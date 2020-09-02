package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntityMechanic extends MechanicComponent {

    private final List<String> adeliaEntityList;
    private final int amountPerSpawn;
    private final List<Integer> DURATION;
    private boolean SAVE = false;

    public SpawnEntityMechanic(List<String> adeliaEntityList, int amountPerSpawn, List<Integer> seconds, boolean save) {
        this.adeliaEntityList = adeliaEntityList;
        this.amountPerSpawn = amountPerSpawn;
        this.DURATION = seconds;
        this.SAVE = save;
    }

    public SpawnEntityMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("adeliaEntityList")) {
            configLoadError("adeliaEntityList");
        }

        if (!configurationSection.contains("amountPerSpawn")) {
            configLoadError("amountPerSpawn");
        }

        if (!configurationSection.contains("durations")) {
            configLoadError("durations");
        }

        List<String> adeliaEntityList = configurationSection.getStringList("adeliaEntityList");
        int amountPerSpawn = configurationSection.getInt("amountPerSpawn");

        this.adeliaEntityList = adeliaEntityList;
        this.amountPerSpawn = amountPerSpawn;

        if (configurationSection.contains("durations")) {
            this.DURATION = configurationSection.getIntegerList("durations");
        } else {
            this.DURATION = new ArrayList<>();
        }

        if (configurationSection.contains("save")) {
            this.SAVE = configurationSection.getBoolean("save");
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            for (String key : adeliaEntityList) {
                for (int i = 0; i < amountPerSpawn; i++) {
                    MobManager mobManager = MythicMobs.inst().getMobManager();
                    Location location = target.getLocation();

                    ActiveMob activeMob = mobManager.spawnMob(key, location);
                    AbstractEntity entity = activeMob.getEntity();
                    LivingEntity mob = (LivingEntity) entity.getBukkitEntity();

                    if (SAVE) {
                        SkillDataManager.onSkillEntityCreateWithSaveOption(caster, mob, castCounter);
                    }

                    if (!this.DURATION.isEmpty()) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (SAVE) {
                                    SkillDataManager.removeSavedEntity(caster, castCounter, mob);
                                } else {
                                    mob.remove();
                                }
                            }
                        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20L * DURATION.get(skillLevel - 1));
                    }
                }
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
