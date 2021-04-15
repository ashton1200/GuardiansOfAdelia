package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class SingleTarget extends TargetComponent {

    private final List<Double> range;
    private final double tolerance;
    // PARTICLE
    private final double gap;
    private final Particle particle;
    private final Particle.DustOptions dustOptions;

    public SingleTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("ranges")) {
            configLoadError("ranges");
        }

        if (!configurationSection.contains("tolerance")) {
            configLoadError("tolerance");
        }

        this.range = configurationSection.getDoubleList("ranges");
        this.tolerance = configurationSection.getDouble("tolerance");

        // PARTICLE
        if (!configurationSection.contains("particle")) {
            configLoadError("particle");
        }

        ConfigurationSection particleSection = configurationSection.getConfigurationSection("particle");

        this.particle = Particle.valueOf(particleSection.getString("particleType"));
        this.gap = particleSection.contains("gap") ? particleSection.getDouble("gap") : 0;

        if (particleSection.contains("dustColor")) {
            if (!this.particle.getDataType().equals(Particle.DustOptions.class)) {
                configLoadError("WRONG DUST OPTIONS");
            }

            int dustColor = particleSection.getInt("dustColor");
            int dustSize = particleSection.getInt("dustSize");

            dustOptions = new Particle.DustOptions(Color.fromRGB(dustColor), dustSize);
        } else {
            dustOptions = null;
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> singles = new ArrayList<>();

        for (LivingEntity target : targets) {
            LivingEntity singleTarget = TargetHelper.getLookingTarget(target, range.get(skillLevel - 1), tolerance);

            if (singleTarget != null) {
                singles.add(singleTarget);

                Location eyeLocation = target.getEyeLocation();

                double v = singleTarget.getHeight() / 2;
                Location targetLocation = singleTarget.getLocation().add(0, v, 0);

                ParticleShapes.drawLineBetween(eyeLocation.getWorld(), eyeLocation.toVector(), particle, dustOptions, targetLocation.toVector(), gap);
            }
        }

        if (singles.isEmpty()) return false;

        singles = determineTargets(caster, singles);

        if (singles.isEmpty()) return false;

        List<LivingEntity> targetsNew = new ArrayList<>();
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                singles.addAll(targets);
                targetsNew = singles;
            } else {
                targetsNew.addAll(targets);
                targetsNew.addAll(singles);
            }
        } else {
            targetsNew = singles;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatColor.YELLOW + "Range: " + range.get(skillLevel));
        } else if (skillLevel == range.size()) {
            additions.add(ChatColor.YELLOW + "Range: " + range.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.YELLOW + "Range: " + range.get(skillLevel - 1) + " -> " + range.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
