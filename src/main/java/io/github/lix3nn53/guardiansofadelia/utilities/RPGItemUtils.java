package io.github.lix3nn53.guardiansofadelia.utilities;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class RPGItemUtils {

    public static ItemStack setDamage(ItemStack itemStack, int amount) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        AttributeModifier modifierTwo = new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", amount * 0.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifierTwo);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack resetArmor(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 0, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

        AttributeModifier modifierTwo = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 0, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifierTwo);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack setAttackSpeed(ItemStack itemStack, double amount) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", amount, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
