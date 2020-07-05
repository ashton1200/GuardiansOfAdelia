package io.github.lix3nn53.guardiansofadelia.guardian.skill.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.FlagCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.HealthCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.NearbyEntityCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.ValueCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram.HologramMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.DisarmMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.RootMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.SilenceMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class SkillComponentLoader {

    public static SkillComponent loadSection(ConfigurationSection configurationSection) {
        GuardiansOfAdelia.getInstance().getLogger().info(configurationSection.getCurrentPath());
        SkillComponent skillComponent = loadComponent(configurationSection);

        int childComponentCount = getChildComponentCount(configurationSection);

        if (childComponentCount > 0) {
            List<SkillComponent> children = loadChildrenOfSection(configurationSection, childComponentCount);

            for (SkillComponent child : children) {
                skillComponent.addChildren(child);
            }
        }

        return skillComponent;
    }

    private static SkillComponent loadComponent(ConfigurationSection configurationSection) {
        String componentType = configurationSection.getString("componentType");

        if (componentType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NULL COMPONENT TYPE");
            return null;
        }

        if (componentType.equals(ParticleMechanic.class.getSimpleName())) {
            return new ParticleMechanic(configurationSection);
        } else if (componentType.equals(AreaTarget.class.getSimpleName())) {
            return new AreaTarget(configurationSection);
        } else if (componentType.equals(SoundMechanic.class.getSimpleName())) {
            return new SoundMechanic(configurationSection);
        } else if (componentType.equals(HoloMessageMechanic.class.getSimpleName())) {
            return new HoloMessageMechanic(configurationSection);
        } else if (componentType.equals(DelayMechanic.class.getSimpleName())) {
            return new DelayMechanic(configurationSection);
        } else if (componentType.equals(SpawnEntityMechanic.class.getSimpleName())) {
            return new SpawnEntityMechanic(configurationSection);
        } else if (componentType.equals(SelfTarget.class.getSimpleName())) {
            return new SelfTarget();
        } else if (componentType.equals(PushMechanic.class.getSimpleName())) {
            return new PushMechanic(configurationSection);
        } else if (componentType.equals(PotionEffectMechanic.class.getSimpleName())) {
            return new PotionEffectMechanic(configurationSection);
        } else if (componentType.equals(LaunchMechanic.class.getSimpleName())) {
            return new LaunchMechanic(configurationSection);
        } else if (componentType.equals(HealMechanic.class.getSimpleName())) {
            return new HealMechanic(configurationSection);
        } else if (componentType.equals(DamageMechanic.class.getSimpleName())) {
            return new DamageMechanic(configurationSection);
        } else if (componentType.equals(ProjectileMechanic.class.getSimpleName())) {
            try {
                return new ProjectileMechanic(configurationSection);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (componentType.equals(FilterCurrentTargets.class.getSimpleName())) {
            return new FilterCurrentTargets(configurationSection);
        } else if (componentType.equals(HologramMechanic.class.getSimpleName())) {
            return new HologramMechanic(configurationSection);
        } else if (componentType.equals(RepeatMechanic.class.getSimpleName())) {
            return new RepeatMechanic(configurationSection);
        } else if (componentType.equals(BuffMechanic.class.getSimpleName())) {
            return new BuffMechanic(configurationSection);
        } else if (componentType.equals(InitializeTrigger.class.getSimpleName())) {
            return new InitializeTrigger();
        } else if (componentType.equals(TookPhysicalDamageTrigger.class.getSimpleName())) {
            return new TookPhysicalDamageTrigger(configurationSection);
        } else if (componentType.equals(HealthCondition.class.getSimpleName())) {
            return new HealthCondition(configurationSection);
        } else if (componentType.equals(ParticleAnimationMechanic.class.getSimpleName())) {
            return new ParticleAnimationMechanic(configurationSection);
        } else if (componentType.equals(ImmunityMechanic.class.getSimpleName())) {
            return new ImmunityMechanic(configurationSection);
        } else if (componentType.equals(LandTrigger.class.getSimpleName())) {
            return new LandTrigger();
        } else if (componentType.equals(ImmunityRemoveMechanic.class.getSimpleName())) {
            return new ImmunityRemoveMechanic(configurationSection);
        } else if (componentType.equals(ImmunityRemoveMechanic.class.getSimpleName())) {
            return new ImmunityRemoveMechanic(configurationSection);
        } else if (componentType.equals(SilenceMechanic.class.getSimpleName())) {
            return new SilenceMechanic(configurationSection);
        } else if (componentType.equals(TookMeleeDamageTrigger.class.getSimpleName())) {
            return new TookMeleeDamageTrigger(configurationSection);
        } else if (componentType.equals(RootMechanic.class.getSimpleName())) {
            return new RootMechanic(configurationSection);
        } else if (componentType.equals(MessageMechanic.class.getSimpleName())) {
            return new MessageMechanic(configurationSection);
        } else if (componentType.equals(FireMechanic.class.getSimpleName())) {
            return new FireMechanic(configurationSection);
        } else if (componentType.equals(RangedAttackTrigger.class.getSimpleName())) {
            return new RangedAttackTrigger(configurationSection);
        } else if (componentType.equals(ValueCondition.class.getSimpleName())) {
            return new ValueCondition(configurationSection);
        } else if (componentType.equals(ValueAddMechanic.class.getSimpleName())) {
            return new ValueAddMechanic(configurationSection);
        } else if (componentType.equals(LocationTarget.class.getSimpleName())) {
            return new LocationTarget(configurationSection);
        } else if (componentType.equals(AddPiercingToArrowShootFromCrossbowTrigger.class.getSimpleName())) {
            return new AddPiercingToArrowShootFromCrossbowTrigger(configurationSection);
        } else if (componentType.equals(NearbyEntityCondition.class.getSimpleName())) {
            return new NearbyEntityCondition(configurationSection);
        } else if (componentType.equals(FlagCondition.class.getSimpleName())) {
            return new FlagCondition(configurationSection);
        } else if (componentType.equals(RepeatCancelMechanic.class.getSimpleName())) {
            return new RepeatCancelMechanic();
        } else if (componentType.equals(FlagRemoveMechanic.class.getSimpleName())) {
            return new FlagRemoveMechanic(configurationSection);
        } else if (componentType.equals(RemoveSavedEntities.class.getSimpleName())) {
            return new RemoveSavedEntities();
        } else if (componentType.equals(FlagSetMechanic.class.getSimpleName())) {
            return new FlagSetMechanic(configurationSection);
        } else if (componentType.equals(DisarmMechanic.class.getSimpleName())) {
            return new DisarmMechanic(configurationSection);
        } else if (componentType.equals(ValueSetMechanic.class.getSimpleName())) {
            return new ValueSetMechanic(configurationSection);
        } else if (componentType.equals(SingleTarget.class.getSimpleName())) {
            return new SingleTarget(configurationSection);
        }

        GuardiansOfAdelia.getInstance().getLogger().info("NO SUCH COMPONENT IN LOADER: " + componentType);
        GuardiansOfAdelia.getInstance().getLogger().info("NO SUCH COMPONENT IN LOADER: " + componentType);
        GuardiansOfAdelia.getInstance().getLogger().info("NO SUCH COMPONENT IN LOADER: " + componentType);

        return null;
    }

    private static int getChildComponentCount(ConfigurationSection configurationSection) {
        int count = 0;
        while (true) {
            boolean contains = configurationSection.contains("child" + (count + 1));
            if (contains) {
                count++;
            } else {
                break;
            }
        }

        return count;
    }

    private static List<SkillComponent> loadChildrenOfSection(ConfigurationSection configurationSection, int childComponentCount) {
        List<SkillComponent> children = new ArrayList<>();

        for (int i = 1; i <= childComponentCount; i++) {
            GuardiansOfAdelia.getInstance().getLogger().info(configurationSection.getCurrentPath() + ".child" + i);
            ConfigurationSection childSection = configurationSection.getConfigurationSection("child" + i);

            SkillComponent child = loadComponent(childSection);
            children.add(child);

            int childComponentCountOfChild = getChildComponentCount(childSection);

            if (childComponentCountOfChild > 0) {
                List<SkillComponent> childrenOfChild = loadChildrenOfSection(childSection, childComponentCountOfChild);

                for (SkillComponent childOfChild : childrenOfChild) {
                    child.addChildren(childOfChild);
                }
            }
        }

        return children;
    }
}
