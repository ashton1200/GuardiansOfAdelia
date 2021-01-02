package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SelfTarget extends TargetComponent {

    public SelfTarget(ConfigurationSection configurationSection) {
        super(false, false, true,
                configurationSection.contains("max") ? configurationSection.getInt("max") : 1,
                false,
                configurationSection.contains("keepCurrent") && configurationSection.getBoolean("keepCurrent"),
                configurationSection.contains("addToBeginning") && configurationSection.getBoolean("addToBeginning"));
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (super.isKeepCurrent()) {
            if (super.isaddToBeginning()) {
                targets.add(0, caster);
            } else {
                targets.add(caster);
            }
        } else {
            targets = new ArrayList<>();
            targets.add(caster);
        }

        return executeChildren(caster, skillLevel, targets, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
