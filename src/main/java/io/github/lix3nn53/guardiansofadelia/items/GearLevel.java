package io.github.lix3nn53.guardiansofadelia.items;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.inventory.ItemStack;

/**
 * Every 10 level is a GearLevel
 * level 1-9 is gearLevel 0
 * level 10-19 is gearLevel 1
 * level 20-29 is gearLevel 2 and so on
 */
public enum GearLevel {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    private static final double MIN_STAT_MULTIPLIER = 0.25;
    private static final double MAX_STAT_MULTIPLIER = 1.2;

    private static final double NORMAL_ITEM_NERF = 0.6; // passive stats on normal item
    private static final double ATTRIBUTE_NERF = 0.2;

    public static GearLevel getGearLevel(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");

            int i = (reqLevel / 10);
            return GearLevel.values()[i];
        }

        return ZERO;
    }

    public static GearLevel getGearLevel(int reqLevel) {
        int i = (reqLevel / 10);

        return GearLevel.values()[i];
    }

    public int getMinLevel() {
        return ordinal() * 10;
    }

    public int getMaxLevel() {
        return (ordinal() * 10) + 9;
    }

    public int getMinStatValue(boolean isPassive, boolean isElement) {
        if (!isPassive && !isElement) { // Normal items does not have attribute bonus
            return 0;
        }

        double v = Math.pow((ordinal() + 2), 2) * MIN_STAT_MULTIPLIER;

        if (!isPassive) {
            v = v * NORMAL_ITEM_NERF;
        }

        if (!isElement) {
            v = v * ATTRIBUTE_NERF;
        }

        return Math.max((int) (v + 0.5), 1);
    }

    public int getMaxStatValue(boolean isPassive, boolean isElement) {
        if (!isPassive && !isElement) { // Normal items does not have attribute bonus
            return 0;
        }

        double v = Math.pow((ordinal() + 2), 2) * MAX_STAT_MULTIPLIER;

        if (!isPassive) {
            v = v * NORMAL_ITEM_NERF;
        }

        if (!isElement) {
            v = v * ATTRIBUTE_NERF;
        }

        return Math.max((int) (v + 0.5), 1);
    }
}
