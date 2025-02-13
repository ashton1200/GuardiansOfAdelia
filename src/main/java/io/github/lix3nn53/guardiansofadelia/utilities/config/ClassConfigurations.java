package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.ActionBarInfo;
import io.github.lix3nn53.guardiansofadelia.guardian.character.ActionBarInfoType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.config.SkillComponentLoader;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassConfigurations {

    private static final HashMap<String, FileConfiguration> classNameToConfiguration = new HashMap<>();
    private static FileConfiguration fileConfiguration;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "classes";

    public static void createConfigs() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "config.yml");
        createClassConfigs();
    }

    public static void loadConfigs() {
        loadClassConfigs();
    }

    private static void createClassConfigs() {
        List<String> classList = fileConfiguration.getStringList("classList");

        for (String className : classList) {
            YamlConfiguration config = ConfigurationUtils.createConfig(filePath, className + ".yml");
            classNameToConfiguration.put(className, config);
        }
    }

    private static void loadClassConfigs() {
        RPGClassManager.reset();
        for (String className : classNameToConfiguration.keySet()) {
            GuardiansOfAdelia.getInstance().getLogger().info("className: " + className);
            FileConfiguration fileConfiguration = classNameToConfiguration.get(className);

            String colorStr = fileConfiguration.getString("color");
            GuardiansOfAdelia.getInstance().getLogger().info("colorStr: " + colorStr);
            ChatPalette color = ChatPalette.valueOf(colorStr);

            String mainElementStr = fileConfiguration.getString("mainElement");
            ElementType mainElement = ElementType.valueOf(mainElementStr);

            int tier = fileConfiguration.getInt("tier");

            List<String> description = fileConfiguration.getStringList("description");

            int classIconCustomModelData = fileConfiguration.getInt("classIconCustomModelData");

            int attributeElementDamage = fileConfiguration.getInt("attributeElementDamage");
            int attributeElementDefense = fileConfiguration.getInt("attributeElementDefense");
            int attributeMaxHealth = fileConfiguration.getInt("attributeMaxHealth");
            int attributeMaxMana = fileConfiguration.getInt("attributeMaxMana");
            int attributeCriticalChance = fileConfiguration.getInt("attributeCriticalChance");
            HashMap<AttributeType, Integer> attributeTiers = new HashMap<>();
            attributeTiers.put(AttributeType.BONUS_ELEMENT_DAMAGE, attributeElementDamage);
            attributeTiers.put(AttributeType.BONUS_ELEMENT_DEFENSE, attributeElementDefense);
            attributeTiers.put(AttributeType.BONUS_MAX_HEALTH, attributeMaxHealth);
            attributeTiers.put(AttributeType.BONUS_MAX_MANA, attributeMaxMana);
            attributeTiers.put(AttributeType.BONUS_CRITICAL_CHANCE, attributeCriticalChance);

            HashMap<Integer, Skill> skillSet = new HashMap<>();

            if (fileConfiguration.contains("skillOne")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillOne");
                Skill skill = loadSkill(skillSection, 0);
                skillSet.put(0, skill);
            }
            if (fileConfiguration.contains("skillTwo")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillTwo");
                Skill skill = loadSkill(skillSection, 1);
                skillSet.put(1, skill);
            }
            if (fileConfiguration.contains("skillThree")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillThree");
                Skill skill = loadSkill(skillSection, 2);
                skillSet.put(2, skill);
            }
            if (fileConfiguration.contains("skillPassive")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillPassive");
                Skill skill = loadSkill(skillSection, 3);
                skillSet.put(3, skill);
            }
            if (fileConfiguration.contains("skillUltimate")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillUltimate");
                Skill skill = loadSkill(skillSection, 4);
                skillSet.put(4, skill);
            }

            List<ShieldGearType> shieldGearTypes = new ArrayList<>();
            if (fileConfiguration.contains("shieldGearTypes")) {
                List<String> gearTypes = fileConfiguration.getStringList("shieldGearTypes");
                for (String gearType : gearTypes) {
                    ShieldGearType shieldGearType = ShieldGearType.valueOf(gearType);
                    shieldGearTypes.add(shieldGearType);
                }
            }
            List<WeaponGearType> weaponGearTypes = new ArrayList<>();
            if (fileConfiguration.contains("weaponGearTypes")) {
                List<String> gearTypes = fileConfiguration.getStringList("weaponGearTypes");
                for (String gearType : gearTypes) {
                    WeaponGearType weaponGearType = WeaponGearType.valueOf(gearType);
                    weaponGearTypes.add(weaponGearType);
                }
            }
            List<ArmorGearType> armorGearTypes = new ArrayList<>();
            if (fileConfiguration.contains("armorGearTypes")) {
                List<String> gearTypes = fileConfiguration.getStringList("armorGearTypes");
                for (String gearType : gearTypes) {
                    ArmorGearType armorGearType = ArmorGearType.valueOf(gearType);
                    armorGearTypes.add(armorGearType);
                }
            }

            boolean hasDefaultOffhand = fileConfiguration.getBoolean("hasDefaultOffhand");
            boolean isDefaultOffhandWeapon = false;
            if (hasDefaultOffhand) {
                isDefaultOffhandWeapon = fileConfiguration.getBoolean("isDefaultOffhandWeapon");
            }

            ActionBarInfoType actionBarInfoType = fileConfiguration.contains("actionBarInfoType") ? ActionBarInfoType.valueOf(fileConfiguration.getString("actionBarInfoType")) : null;
            String actionBarIcon = fileConfiguration.getString("actionBarIcon");
            String actionBarKey = fileConfiguration.getString("actionBarKey");

            ActionBarInfo actionBarInfo = new ActionBarInfo(actionBarInfoType, actionBarIcon, actionBarKey);

            RPGClass rpgClass = new RPGClass(color, mainElement, className, tier, classIconCustomModelData, attributeTiers, skillSet,
                    actionBarInfo, shieldGearTypes, weaponGearTypes, armorGearTypes, hasDefaultOffhand, isDefaultOffhandWeapon, description);

            RPGClassManager.addClass(className, rpgClass);
        }
    }

    private static Skill loadSkill(ConfigurationSection skillSection, int skillIndex) {
        String name = skillSection.getString("name");
        List<String> description = skillSection.getStringList("description");
        int customModelData = skillSection.getInt("customModelData");
        List<Integer> reqLevels = getDefaultReqLevels(skillIndex);
        List<Integer> reqPoints = getDefaultReqPoints(skillIndex);
        List<Integer> manaCosts = skillSection.getIntegerList("manaCosts");
        List<Integer> cooldowns = skillSection.getIntegerList("cooldowns");

        Skill skill = new Skill(name, 4, Material.IRON_HOE, customModelData, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SkillComponent triggerComponent = SkillComponentLoader.loadSection(skillSection.getConfigurationSection("trigger"));
        skill.addTrigger(triggerComponent);

        int triggerCount = ConfigurationUtils.getChildComponentCount(skillSection, "trigger");
        for (int i = 1; i <= triggerCount; i++) {
            SkillComponent triggerComponentExtra = SkillComponentLoader.loadSection(skillSection.getConfigurationSection("trigger" + i));
            skill.addTrigger(triggerComponentExtra);
        }

        return skill;
    }

    private static List<Integer> getDefaultReqLevels(int skillIndex) {
        List<Integer> reqLevels = new ArrayList<>();
        if (skillIndex == 0) {
            reqLevels.add(1);
            reqLevels.add(10);
            reqLevels.add(20);
            reqLevels.add(30);
        } else if (skillIndex == 1) {
            reqLevels.add(5);
            reqLevels.add(15);
            reqLevels.add(25);
            reqLevels.add(35);
        } else if (skillIndex == 2) {
            reqLevels.add(10);
            reqLevels.add(20);
            reqLevels.add(30);
            reqLevels.add(40);
        } else if (skillIndex == 3) {
            reqLevels.add(20);
            reqLevels.add(30);
            reqLevels.add(40);
            reqLevels.add(50);
        } else if (skillIndex == 4) {
            reqLevels.add(40);
            reqLevels.add(50);
            reqLevels.add(60);
            reqLevels.add(70);
        }

        return reqLevels;
    }

    private static List<Integer> getDefaultReqPoints(int skillIndex) {
        List<Integer> reqPoints = new ArrayList<>();
        if (skillIndex == 0) {
            reqPoints.add(1);
            reqPoints.add(1);
            reqPoints.add(1);
            reqPoints.add(1);
        } else if (skillIndex == 1) {
            reqPoints.add(1);
            reqPoints.add(1);
            reqPoints.add(1);
            reqPoints.add(1);
        } else if (skillIndex == 2) {
            reqPoints.add(1);
            reqPoints.add(1);
            reqPoints.add(1);
            reqPoints.add(1);
        } else if (skillIndex == 3) {
            reqPoints.add(2);
            reqPoints.add(2);
            reqPoints.add(2);
            reqPoints.add(2);
        } else if (skillIndex == 4) {
            reqPoints.add(3);
            reqPoints.add(3);
            reqPoints.add(3);
            reqPoints.add(3);
        }

        return reqPoints;
    }
}