package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.BuffType;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.PotionType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Armors;
import io.github.lix3nn53.guardiansofadelia.Items.list.consumables.BuffScrolls;
import io.github.lix3nn53.guardiansofadelia.Items.list.consumables.Potions;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveItemList;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPoolGenerator {

    public static List<ItemStack> generateWeapons(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = tier.getMinNumberOfStats();
        int minStatValue = gearLevel.getMinStatValue();
        int maxStatValue = gearLevel.getMaxStatValue();

        //7*3 weapon
        List<ItemStack> temp = new ArrayList<>();

        temp.add(Weapons.getWeapon(RPGClass.WARRIOR, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.ARCHER, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.NINJA, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.PALADIN, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MAGE, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.KNIGHT, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MONK, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        return temp;
    }

    public static List<ItemStack> generatePassives(ItemTier tier, String itemTag, GearLevel gearLevel) {
        double bonusPercent = tier.getBonusPercent();
        int minNumberofStats = tier.getMinNumberOfStats();
        int minStatValue = gearLevel.getMinStatValue();
        int maxStatValue = gearLevel.getMaxStatValue();

        List<ItemStack> temp = new ArrayList<>();

        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.RING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats, bonusPercent));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.GLOVE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats, bonusPercent));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.NECKLACE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats, bonusPercent));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.EARRING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats, bonusPercent));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.PARROT, tier, itemTag, minStatValue, maxStatValue, minNumberofStats, bonusPercent));

        return temp;
    }

    public static List<ItemStack> generateArmors(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = tier.getMinNumberOfStats();
        int minStatValue = gearLevel.getMinStatValue();
        int maxStatValue = gearLevel.getMaxStatValue();

        List<ItemStack> temp = new ArrayList<>();

        if (gearLevel.equals(GearLevel.ZERO) || gearLevel.equals(GearLevel.ONE)) {
            temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        } else {
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                temp.add(Armors.getArmor(ArmorType.HELMET, rpgClass, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.CHESTPLATE, rpgClass, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.LEGGINGS, rpgClass, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.BOOTS, rpgClass, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            }
        }

        return temp;
    }

    public static List<ItemStack> generatePotions(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int itemTier = gearLevel.getMinLevel();

        List<ItemStack> temp = new ArrayList<>();

        temp.add(Potions.getItemStack(PotionType.HEALTH, itemTier));
        temp.add(Potions.getItemStack(PotionType.MANA, itemTier));
        temp.add(Potions.getItemStack(PotionType.HYBRID, itemTier));
        temp.add(Potions.getItemStack(PotionType.REGENERATION, itemTier));

        return temp;
    }

    public static List<ItemStack> generateBuffScrolls(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int itemTier = gearLevel.getMinLevel();

        List<ItemStack> temp = new ArrayList<>();

        temp.add(BuffScrolls.getItemStack(BuffType.PHYSICAL_DAMAGE, itemTier));
        temp.add(BuffScrolls.getItemStack(BuffType.PHYSICAL_DEFENSE, itemTier));
        temp.add(BuffScrolls.getItemStack(BuffType.MAGICAL_DAMAGE, itemTier));
        temp.add(BuffScrolls.getItemStack(BuffType.MAGICAL_DEFENSE, itemTier));

        return temp;
    }
}
