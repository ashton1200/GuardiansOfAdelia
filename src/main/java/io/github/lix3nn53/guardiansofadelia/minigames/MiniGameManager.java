package io.github.lix3nn53.guardiansofadelia.minigames;

import io.github.lix3nn53.guardiansofadelia.minigames.arenas.LastOneStanding;
import io.github.lix3nn53.guardiansofadelia.minigames.arenas.WinByMostKills;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonInstance;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.guildwar.GuildWar;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.Portal;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MiniGameManager {

    private static final List<LastOneStanding> lastOneStandingList = new ArrayList<>();
    private static final List<WinByMostKills> winByMostKillsList = new ArrayList<>();
    private static final List<GuildWar> guildWarList = new ArrayList<>();

    private static final HashMap<String, DungeonTheme> dungeonThemes = new HashMap<>();
    private static final HashMap<String, DungeonInstance> codeToDungeon = new HashMap<>();
    private static final HashMap<Portal, String> portalToDungeonTheme = new HashMap<>();

    private static final HashMap<Player, Minigame> playerToMinigame = new HashMap<>();

    public static void initMinigames() {
        //Last One Standing
        List<Location> lastOneStandingStartLocations1 = new ArrayList<>();
        lastOneStandingStartLocations1.add(new Location(Bukkit.getWorld("arena"), -22.5, 68.5, 181.5, -80, -2));
        lastOneStandingStartLocations1.add(new Location(Bukkit.getWorld("arena"), 60.5, 68.5, 196.5, 100, -2));
        LastOneStanding lastOneStandingRoom1 = new LastOneStanding("Royal Duel", 1, 3, 1, lastOneStandingStartLocations1, 1, 2, 1, 2);
        lastOneStandingList.add(lastOneStandingRoom1);

        List<Location> lastOneStandingStartLocations2 = new ArrayList<>();
        lastOneStandingStartLocations2.add(new Location(Bukkit.getWorld("arena"), -22.5, 68.5, 303.5, -80, -2));
        lastOneStandingStartLocations2.add(new Location(Bukkit.getWorld("arena"), 60.5, 68.5, 318.5, 100, -2));
        LastOneStanding lastOneStandingRoom2 = new LastOneStanding("Royal Duel", 1, 3, 2, lastOneStandingStartLocations2, 2, 2, 2, 4);
        lastOneStandingList.add(lastOneStandingRoom2);

        List<Location> lastOneStandingStartLocations3 = new ArrayList<>();
        lastOneStandingStartLocations3.add(new Location(Bukkit.getWorld("arena"), 133.5, 73.5, 528.5, 95, -4));
        lastOneStandingStartLocations3.add(new Location(Bukkit.getWorld("arena"), -41.5, 73.5, 509.5, -84, -1));
        LastOneStanding lastOneStandingRoom3 = new LastOneStanding("Battle of Biomes", 1, 3, 3, lastOneStandingStartLocations3, 2, 2, 2, 4);
        lastOneStandingList.add(lastOneStandingRoom3);

        List<Location> lastOneStandingStartLocations4 = new ArrayList<>();
        lastOneStandingStartLocations4.add(new Location(Bukkit.getWorld("arena"), 140.5, 72.5, 759.5, 95, -4));
        lastOneStandingStartLocations4.add(new Location(Bukkit.getWorld("arena"), -33.5, 72.5, 741.5, -84, -1));
        LastOneStanding lastOneStandingRoom4 = new LastOneStanding("Battle of Biomes", 1, 3, 4, lastOneStandingStartLocations4, 2, 2, 2, 4);
        lastOneStandingList.add(lastOneStandingRoom4);

        //Win By Most Kills
        List<Location> winByMostKillsStartLocations1 = new ArrayList<>();
        winByMostKillsStartLocations1.add(new Location(Bukkit.getWorld("arena"), -71.5, 145.5, -305.5, -135, -2));
        winByMostKillsStartLocations1.add(new Location(Bukkit.getWorld("arena"), 56.5, 145.5, -305.5, 135, -2));
        winByMostKillsStartLocations1.add(new Location(Bukkit.getWorld("arena"), 56.5, 145.5, -433.5, 45, -2));
        WinByMostKills winByMostKillsRoom1 = new WinByMostKills("Space Jump", 1, 1, 1, winByMostKillsStartLocations1, 1, 3, 3);
        winByMostKillsList.add(winByMostKillsRoom1);

        List<Location> winByMostKillsStartLocations2 = new ArrayList<>();
        winByMostKillsStartLocations2.add(new Location(Bukkit.getWorld("arena"), -60.5, 141.5, 22.5, -135, -2));
        winByMostKillsStartLocations2.add(new Location(Bukkit.getWorld("arena"), 67.5, 141.5, 22.5, 135, -2));
        winByMostKillsStartLocations2.add(new Location(Bukkit.getWorld("arena"), 67.5, 141.5, -105.5, 48, -2));
        WinByMostKills winByMostKillsRoom2 = new WinByMostKills("Space Jump", 1, 1, 2, winByMostKillsStartLocations2, 1, 3, 3);
        winByMostKillsList.add(winByMostKillsRoom2);

        //Guild War
        List<Location> guildWarLocations1 = new ArrayList<>();
        guildWarLocations1.add(new Location(Bukkit.getWorld("arena"), 362.5, 40.5, -363.5, -1, -3));
        guildWarLocations1.add(new Location(Bukkit.getWorld("arena"), 480.5, 48.5, -33.5, 179.5f, -2));
        List<Location> guildWarFlagGrounds1 = new ArrayList<>();
        guildWarFlagGrounds1.add(new Location(Bukkit.getWorld("arena"), 280, 44, -134));
        guildWarFlagGrounds1.add(new Location(Bukkit.getWorld("arena"), 407, 31, -187));
        guildWarFlagGrounds1.add(new Location(Bukkit.getWorld("arena"), 529, 46, -271));
        GuildWar GuildWarRoom1 = new GuildWar(500, "Royal Clash", 1, guildWarLocations1, guildWarFlagGrounds1);
        guildWarList.add(GuildWarRoom1);

        List<Location> guildWarLocations2 = new ArrayList<>();
        guildWarLocations2.add(new Location(Bukkit.getWorld("arena"), 434.5, 88.5, 495.5, -108, -3));
        guildWarLocations2.add(new Location(Bukkit.getWorld("arena"), 433.5, 88.5, 278.5, 106.5f, -5));
        List<Location> guildWarFlagGrounds2 = new ArrayList<>();
        guildWarFlagGrounds2.add(new Location(Bukkit.getWorld("arena"), 464, 55, 389));
        GuildWar GuildWarRoom2 = new GuildWar(200, "The Deep Valley", 2, guildWarLocations2, guildWarFlagGrounds2);
        guildWarList.add(GuildWarRoom2);
    }

    public static LastOneStanding getLastOneStanding(int instanceNo) {
        return lastOneStandingList.get(instanceNo - 1);
    }

    public static GuildWar getGuildWar(int instanceNo) {
        return guildWarList.get(instanceNo - 1);
    }

    public static WinByMostKills getWinByMostKills(int instanceNo) {
        return winByMostKillsList.get(instanceNo - 1);
    }

    public static void addDungeonInstance(String dungeonTheme, int instanceNo, DungeonInstance dungeonInstance) {
        codeToDungeon.put(dungeonTheme + instanceNo, dungeonInstance);
    }

    public static DungeonInstance getDungeonInstance(String dungeonTheme, int instanceNo) {
        return codeToDungeon.get(dungeonTheme + instanceNo);
    }

    public static boolean dungeonInstanceExists(String dungeonTheme, int instanceNo) {
        return codeToDungeon.containsKey(dungeonTheme + instanceNo);
    }

    public static DungeonTheme getDungeonFromPortal(Portal portal) {
        if (portalToDungeonTheme.containsKey(portal)) {
            String dungeonThemeCode = portalToDungeonTheme.get(portal);
            return dungeonThemes.get(dungeonThemeCode);
        }
        return null;
    }

    public static void addDungeonPortal(Location location, String dungeonThemeCode) {
        DungeonTheme dungeonTheme = dungeonThemes.get(dungeonThemeCode);
        Portal portal = new Portal(location, dungeonTheme.getPortalColor(), dungeonTheme.getName());
        portalToDungeonTheme.put(portal, dungeonThemeCode);
        PortalManager.addPortal(portal);
    }

    public static Location getPortalLocationOfDungeonTheme(String dungeonTheme) {
        for (Portal portal : portalToDungeonTheme.keySet()) {
            String dungeonThemeLoop = portalToDungeonTheme.get(portal);
            if (dungeonTheme.equals(dungeonThemeLoop)) {
                return portal.getBaseLocation();
            }
        }
        return TownManager.getTown(1).getLocation();
    }

    public static Set<Portal> getDungeonPortals() {
        return portalToDungeonTheme.keySet();
    }

    public static boolean isInMinigame(Player player) {
        return playerToMinigame.containsKey(player);
    }

    public static Minigame playerToMinigame(Player player) {
        return playerToMinigame.get(player);
    }

    public static void addPlayer(Player player, Minigame minigame) {
        playerToMinigame.put(player, minigame);
    }

    public static void removePlayer(Player player) {
        playerToMinigame.remove(player);
    }

    public static void onPlayerDeath(Player player) {
        if (playerToMinigame.containsKey(player)) {
            Minigame minigame = playerToMinigame.get(player);
            minigame.onPlayerDeath(player);
        }
    }

    public static void onPlayerDealDamageToPlayer(Player attacker, Player defender) {
        if (playerToMinigame.containsKey(attacker)) {
            playerToMinigame.get(attacker).onPlayerDealDamageToPlayer(attacker, defender);
        }
    }

    /**
     * @param attacker
     * @param defender
     * @return false to cancel event
     */
    public static boolean onPlayerDealDamageToEntity(Player attacker, LivingEntity defender) {
        if (playerToMinigame.containsKey(attacker)) {
            return playerToMinigame.get(attacker).onPlayerDealDamageToEntity(attacker, defender);
        }

        return true;
    }

    public static void onQuit(Player player) {
        if (playerToMinigame.containsKey(player)) {
            Minigame minigame = playerToMinigame.get(player);
            minigame.leave(player);
        } else {
            if (player.isOnline()) {
                player.sendMessage(ChatPalette.RED + "You are not in a minigame");
            }
        }
    }

    public static boolean onCheckpointSet(Player player, Checkpoint checkpoint) {
        if (playerToMinigame.containsKey(player)) {
            Minigame minigame = playerToMinigame.get(player);
            return minigame.onCheckpointSet(player, checkpoint);
        }

        return false;
    }

    public static void onMobKill(Player player, String internalName, Entity mob) {
        if (playerToMinigame.containsKey(player)) {
            if (playerToMinigame.get(player) instanceof DungeonInstance) {
                DungeonInstance dungeonInstance = (DungeonInstance) playerToMinigame.get(player);
                dungeonInstance.onMobKill(internalName, mob);
            }
        }
    }

    public static GuiGeneric getLastOneStandingJoinGui() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GOLD + "Join Last One Standing", 0);

        int i = 9;
        for (LastOneStanding lastOneStanding : lastOneStandingList) {
            ItemStack room = new ItemStack(Material.LIME_WOOL);
            ItemMeta itemMeta = room.getItemMeta();
            itemMeta.setDisplayName(ChatPalette.GREEN_DARK + lastOneStanding.getMinigameName() + " (" + lastOneStanding.getPlayersInGameSize() + "/" + lastOneStanding.getMaxPlayerSize() + ")");

            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GREEN_DARK + "Map: " + ChatPalette.WHITE + lastOneStanding.getMapName());
            lore.add(ChatPalette.YELLOW + "Level req: " + ChatPalette.WHITE + lastOneStanding.getLevelReq());
            lore.add(ChatPalette.GOLD + "Team amount: " + ChatPalette.WHITE + lastOneStanding.getTeamAmount());
            lore.add(ChatPalette.GOLD + "Team size: " + ChatPalette.WHITE + lastOneStanding.getTeamSize());
            lore.add(ChatPalette.PURPLE_LIGHT + "Game time: " + ChatPalette.WHITE + lastOneStanding.getTimeLimitInMinutes() + " minute(s)");
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to join this room!");
            itemMeta.setLore(lore);

            room.setItemMeta(itemMeta);
            if (lastOneStanding.isInGame()) {
                room.setType(Material.RED_WOOL);
            }
            room.setItemMeta(itemMeta);
            guiGeneric.setItem(i, room);
            i += 2;
        }
        return guiGeneric;
    }

    public static GuiGeneric getWinByMostKillsJoinGui() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GOLD + "Join Win By Most Kills", 0);

        int i = 9;
        for (WinByMostKills winByMostKills : winByMostKillsList) {
            ItemStack room = new ItemStack(Material.LIME_WOOL);
            ItemMeta itemMeta = room.getItemMeta();
            itemMeta.setDisplayName(ChatPalette.GREEN_DARK + winByMostKills.getMinigameName() + " (" + winByMostKills.getPlayersInGameSize() + "/" + winByMostKills.getMaxPlayerSize() + ")");

            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GREEN_DARK + "Map: " + ChatPalette.WHITE + winByMostKills.getMapName());
            lore.add(ChatPalette.YELLOW + "Level req: " + ChatPalette.WHITE + winByMostKills.getLevelReq());
            lore.add(ChatPalette.GOLD + "Team amount: " + ChatPalette.WHITE + winByMostKills.getTeamAmount());
            lore.add(ChatPalette.GOLD + "Team size: " + ChatPalette.WHITE + winByMostKills.getTeamSize());
            lore.add(ChatPalette.PURPLE_LIGHT + "Game time: " + ChatPalette.WHITE + winByMostKills.getTimeLimitInMinutes() + " minute(s)");
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to join this room!");
            itemMeta.setLore(lore);

            room.setItemMeta(itemMeta);
            if (winByMostKills.isInGame()) {
                room.setType(Material.RED_WOOL);
            }
            room.setItemMeta(itemMeta);
            guiGeneric.setItem(i, room);
            i += 2;
        }
        return guiGeneric;
    }

    public static GuiGeneric getGuildWarJoinGui() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Join Guild War", 0);

        int i = 9;
        for (GuildWar guildWar : guildWarList) {
            ItemStack room = new ItemStack(Material.LIME_WOOL);
            ItemMeta itemMeta = room.getItemMeta();
            itemMeta.setDisplayName(ChatPalette.GREEN_DARK + guildWar.getMinigameName() + " (" + guildWar.getPlayersInGameSize() + "/" + guildWar.getMaxPlayerSize() + ")");

            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GREEN_DARK + "Map: " + ChatPalette.WHITE + guildWar.getMapName());
            lore.add(ChatPalette.YELLOW + "Level req: " + ChatPalette.WHITE + guildWar.getLevelReq());
            lore.add(ChatPalette.GOLD + "Team size: " + ChatPalette.WHITE + guildWar.getTeamSize());
            lore.add(ChatPalette.PURPLE_LIGHT + "Game time: " + ChatPalette.WHITE + guildWar.getTimeLimitInMinutes() + " minute(s)");
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to join this room!");
            itemMeta.setLore(lore);

            room.setItemMeta(itemMeta);
            if (guildWar.isInGame()) {
                room.setType(Material.RED_WOOL);
            }
            room.setItemMeta(itemMeta);
            guiGeneric.setItem(i, room);
            i += 2;
        }
        return guiGeneric;
    }

    public static HashMap<String, DungeonTheme> getDungeonThemes() {
        return dungeonThemes;
    }

    public static void addDungeonTheme(String code, DungeonTheme dungeonTheme) {
        dungeonThemes.put(code, dungeonTheme);
    }

    public static void clearDungeonData() {
        for (String key : codeToDungeon.keySet()) {
            DungeonInstance dungeonInstance = codeToDungeon.get(key);

            dungeonInstance.clearDebugHolograms();
        }

        dungeonThemes.clear();
        codeToDungeon.clear();
        portalToDungeonTheme.clear();
    }
}
