package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VanillaSlotLeggings implements VanillaSlot {

    @Override
    public boolean doesFit(ItemStack itemStack) {
        Material mat = itemStack.getType();
        if (!(mat.equals(Material.CHAINMAIL_LEGGINGS) || mat.equals(Material.DIAMOND_LEGGINGS) || mat.equals(Material.GOLDEN_LEGGINGS)
                || mat.equals(Material.IRON_LEGGINGS) || mat.equals(Material.LEATHER_LEGGINGS))) {
            return false;
        }
        return false;
    }

    @Override
    public void setItemOnSlot(Player player, ItemStack itemOnSlot) {
        player.getInventory().setLeggings(itemOnSlot);
    }

    @Override
    public boolean isEmpty(Player player) {
        return player.getInventory().getLeggings() == null;
    }

    @Override
    public void setEmpty(Player player) {
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
    }

    @Override
    public ItemStack getItemOnSlot(Player player) {
        return player.getInventory().getLeggings();
    }

    public ItemStack getFillItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Leggings Slot");
        itemMeta.setLore(new ArrayList() {{
            add("");
        }});
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(6);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
