package io.github.lix3nn53.guardiansofadelia.revive;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TombManager {

    private static final HashMap<Player, Tomb> playerToTomb = new HashMap<>();
    private static final HashMap<String, List<Tomb>> chunkKeyToTomb = new HashMap<>();
    private static final List<Player> playersInTombCooldown = new ArrayList<>();

    private static final HashMap<Player, Location> playerToDeathLocation = new HashMap<>();
    private static final HashMap<Player, Town> playerToSpawnTown = new HashMap<>();

    public static boolean hasTomb(Player player) {
        return playerToTomb.containsKey(player);
    }

    public static void onDeath(Player player, Location deathLocation) {
        Town town = TownManager.getNearestTown(deathLocation);
        player.teleport(town.getLocation());

        if (playersInTombCooldown.contains(player)) {
            player.sendMessage(ChatPalette.RED + "You can't search for your tomb again. Your soul needs to rest for 5 minutes.");
            return;
        }

        playerToDeathLocation.put(player, deathLocation);
        playerToSpawnTown.put(player, town);
        openDeathGui(player);
    }

    public static void onChunkLoad(String chunkKey) {
        if (chunkKeyToTomb.containsKey(chunkKey)) {
            List<Tomb> tombs = chunkKeyToTomb.get(chunkKey);
            for (Tomb tomb : tombs) {
                tomb.createModel();
            }
        }
    }

    public static void onTombRemove(Tomb tomb) {
        String chunkKey = LocationUtils.getChunkKey(tomb.getBaseLocation());
        if (chunkKeyToTomb.containsKey(chunkKey)) {
            List<Tomb> tombs = chunkKeyToTomb.get(chunkKey);
            tombs.remove(tomb);
            if (tombs.isEmpty()) {
                chunkKeyToTomb.remove(chunkKey);
            } else {
                chunkKeyToTomb.put(chunkKey, tombs);
            }
        }
        playerToTomb.remove(tomb.getOwner());
    }

    private static void openDeathGui(Player player) {
        GuiGeneric reviveGui = new GuiGeneric(9, ChatPalette.BLUE_LIGHT + "Revive Gui", 0);

        ItemStack respawn = new ItemStack(Material.IRON_HOE);
        ItemMeta itemMeta = respawn.getItemMeta();
        itemMeta.setCustomModelData(18);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Respawn");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "You have respawn in nearest town");
        lore.add(ChatPalette.GRAY + "to your death location.");
        lore.add(ChatPalette.GRAY + "Close your inventory to continue.");
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        respawn.setItemMeta(itemMeta);

        reviveGui.setItem(3, respawn);

        ItemStack soul = new ItemStack(Material.IRON_HOE);
        itemMeta = soul.getItemMeta();
        itemMeta.setCustomModelData(10);
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Search your tomb in soul mode");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Find your tomb and left");
        lore.add(ChatPalette.GRAY + "click near it to respawn");
        lore.add(ChatPalette.RED + "Time limit is 2 minutes after your death");
        lore.add(ChatPalette.GRAY + "You will respawn here if you cant");
        lore.add(ChatPalette.GRAY + "find your tomb in time");
        itemMeta.setLore(lore);
        soul.setItemMeta(itemMeta);

        reviveGui.setItem(5, soul);

        reviveGui.openInventory(player);
    }

    public static void onReachToTomb(Player player) {
        if (playerToTomb.containsKey(player)) {
            Tomb tomb = playerToTomb.get(player);
            if (tomb.isNear()) {
                tomb.remove();
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatPalette.GREEN_DARK + "You have reached your tomb in time!");
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 240, 20));
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1));
                PetManager.onEggEquip(player);
            }
        }
    }

    public static void closeTombGui(Player player) {
        if (!playerToTomb.containsKey(player)) {
            PetManager.onEggEquip(player);

            playerToDeathLocation.remove(player);
            playerToSpawnTown.remove(player);
        }
    }

    public static void startSearch(Player player) {
        Location deathLocation = playerToDeathLocation.get(player);
        Town town = playerToSpawnTown.get(player);

        Tomb tomb = new Tomb(player, deathLocation);
        String chunkKey = LocationUtils.getChunkKey(deathLocation);
        List<Tomb> tombs;
        if (chunkKeyToTomb.containsKey(chunkKey)) {
            tombs = chunkKeyToTomb.get(chunkKey);
        } else {
            tombs = new ArrayList<>();
        }
        tombs.add(tomb);
        chunkKeyToTomb.put(chunkKey, tombs);
        playerToTomb.put(player, tomb);
        playersInTombCooldown.add(player);

        new BukkitRunnable() {

            @Override
            public void run() {
                player.setGameMode(GameMode.SPECTATOR);
                tomb.createModel();
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 1L);

        new BukkitRunnable() {

            final int timeLimitIn10Seconds = 12;
            int count = 0;

            @Override
            public void run() {
                if (playerToTomb.containsKey(player)) {
                    if (count == timeLimitIn10Seconds) {
                        tomb.remove();
                        player.setGameMode(GameMode.ADVENTURE);
                        player.teleport(town.getLocation());
                        player.sendMessage(ChatPalette.RED + "Tomb search timeout");
                        PetManager.onEggEquip(player);
                        cancel();
                    } else {
                        player.sendMessage(ChatPalette.BLUE_LIGHT.toString() + ((timeLimitIn10Seconds * 10) - (10 * count)) + " seconds left for your soul to give up");
                        count++;
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 20L, 20 * 10L);

        //remove from playersInTombCooldown after 5 minutes
        new BukkitRunnable() {

            @Override
            public void run() {
                playersInTombCooldown.remove(player);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 300L);
    }
}
