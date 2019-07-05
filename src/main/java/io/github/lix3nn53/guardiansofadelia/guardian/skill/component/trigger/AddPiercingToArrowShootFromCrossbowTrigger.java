package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AddPiercingToArrowShootFromCrossbowTrigger extends TriggerComponent {

    private final int piercingLevel;
    private final int piercingLevelPerSkillLevel;
    LivingEntity caster;
    int skillLevel;
    String castKey;

    public AddPiercingToArrowShootFromCrossbowTrigger(int piercingLevel, int piercingLevelPerSkillLevel) {
        this.piercingLevel = piercingLevel;
        this.piercingLevelPerSkillLevel = piercingLevelPerSkillLevel;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        this.caster = caster;
        this.skillLevel = skillLevel;
        this.castKey = castKey;

        AddPiercingToArrowShootFromCrossbowTrigger rangedAttackTrigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target instanceof Player) {
                        TriggerListener.startListeningAddPiercingToArrowShootFromCrossbowTrigger((Player) target, rangedAttackTrigger);
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 10L);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }

    /**
     * The callback when player lands that applies child components
     */
    public void callback(Arrow arrow) {
        arrow.setPierceLevel(piercingLevel + ((skillLevel - 1) + piercingLevelPerSkillLevel));
    }
}
