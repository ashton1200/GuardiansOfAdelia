package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class RPGSlotEarring extends RPGSlotPassive implements RPGSlot {

    private final int passiveTypeNum = 4;

    public boolean doesFit(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "passive")) {
            Integer typeNum = PersistentDataContainerUtil.getInteger(itemStack, "passive");
            return typeNum == this.passiveTypeNum;
        }
        return false;
    }

    public ItemStack getFillItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Earrings Slot");
        itemMeta.setLore(new ArrayList() {{
            add("");
        }});
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(9);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
