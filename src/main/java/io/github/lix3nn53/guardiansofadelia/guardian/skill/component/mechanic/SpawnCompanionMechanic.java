package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.element.Element;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpawnCompanionMechanic extends MechanicComponent {

    private final String mobCode;
    private final List<Integer> amounts;
    private final List<Integer> DURATION;

    private final ElementType element;
    private final List<Double> elementPercents;

    private boolean SAVE = false;
    private final Optional<String> maxAmountVar;
    private final int maxAmountIfVarEmpty;

    public SpawnCompanionMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("mobCode")) {
            configLoadError("mobCode");
        }

        if (!configurationSection.contains("amounts")) {
            configLoadError("amounts");
        }

        if (!configurationSection.contains("element")) {
            configLoadError("element");
        }

        if (!configurationSection.contains("elementPercents")) {
            configLoadError("elementPercents");
        }

        this.mobCode = configurationSection.getString("mobCode");
        this.amounts = configurationSection.getIntegerList("amounts");
        this.element = ElementType.valueOf(configurationSection.getString("element"));
        this.elementPercents = configurationSection.getDoubleList("elementPercents");

        if (configurationSection.contains("durations")) {
            this.DURATION = configurationSection.getIntegerList("durations");
        } else {
            this.DURATION = new ArrayList<>();
        }

        if (configurationSection.contains("save")) {
            this.SAVE = configurationSection.getBoolean("save");
        }

        if (configurationSection.contains("maxAmountVar")) {
            this.maxAmountVar = Optional.of(configurationSection.getString("maxAmountVar"));
            this.maxAmountIfVarEmpty = configurationSection.getInt("maxAmountIfVarEmpty");
        } else {
            this.maxAmountVar = Optional.empty();
            this.maxAmountIfVarEmpty = 0;
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        int amount = amounts.get(skillLevel - 1);
        double elementPercent = elementPercents.get(skillLevel - 1);

        int level = 0;
        if (caster instanceof Player) {
            Player player = (Player) caster;
            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                    Element element = rpgCharacterStats.getElement(this.element);
                    int elementPoints = element.getTotal();

                    String rpgClassStr = activeCharacter.getRpgClassStr();
                    double playerDamage = rpgCharacterStats.getTotalElementDamage(player, rpgClassStr);

                    double total = playerDamage + elementPoints;

                    level = (int) (total * elementPercent + 0.5);
                }
            }
        }

        List<LivingEntity> spawned = new ArrayList<>();
        for (LivingEntity target : targets) {
            if (!(target instanceof Player)) continue;
            Player owner = (Player) target;

            if (this.maxAmountVar.isPresent()) {
                int max = SkillDataManager.getValue(owner, this.maxAmountVar.get());
                if (max == 0) {
                    max = this.maxAmountIfVarEmpty;
                }
                List<LivingEntity> companions = PetManager.getCompanions(owner);
                int current = 0;
                if (companions != null) {
                    current = companions.size();
                }

                int available = max - current;

                if (available <= 0) continue;

                if (amount > available) {
                    amount = available;
                }
            } else {
                if (CommandAdmin.DEBUG_MODE) owner.sendMessage("No value");
            }

            for (int i = 0; i < amount; i++) {

                LivingEntity livingEntity = PetManager.spawnCompanion(owner, mobCode, level);

                if (livingEntity == null) {
                    GuardiansOfAdelia.getInstance().getLogger().info("SpawnPetMechanic error: " + mobCode);
                    continue;
                }

                spawned.add(livingEntity);

                if (SAVE) {
                    SkillDataManager.onSkillEntityCreateWithSaveOption(caster, livingEntity, castCounter);
                }

                if (!this.DURATION.isEmpty()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (SAVE) {
                                SkillDataManager.removeSavedEntity(caster, castCounter, livingEntity);
                            } else {
                                livingEntity.remove();
                            }
                        }
                    }.runTaskLater(GuardiansOfAdelia.getInstance(), DURATION.get(skillLevel - 1));
                }
            }
        }

        if (!spawned.isEmpty()) {
            executeChildren(caster, skillLevel, spawned, castCounter, skillIndex);

            return true;
        }

        return false;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        String codeStr = mobCode.replaceAll("Companion", "");
        if (skillLevel == 0) {
            String lore = "Spawn " + amounts.get(skillLevel) + "x" + codeStr + " " + ((int) (elementPercents.get(skillLevel) * 100))
                    + "% " + element.getFullName();

            if (!DURATION.isEmpty()) {
                lore = lore + " for " + DURATION.get(skillLevel) / 20;
            }

            additions.add(lore);
        } else if (skillLevel == amounts.size()) {
            String lore = "Spawn " + amounts.get(skillLevel - 1) + "x" + codeStr + " " + ((int) (elementPercents.get(skillLevel - 1) * 100))
                    + "% " + element.getFullName();

            if (!DURATION.isEmpty()) {
                lore = lore + " for " + DURATION.get(skillLevel - 1) / 20;
            }

            additions.add(lore);
        } else {
            String lore1 = "Spawn " + amounts.get(skillLevel - 1) + "x" + codeStr + " " + ((int) (elementPercents.get(skillLevel - 1) * 100))
                    + "% of " + element.getFullName();
            String lore2 = amounts.get(skillLevel) + "x " + ((int) (elementPercents.get(skillLevel) * 100)) + "% " + element.getFullName();

            if (!DURATION.isEmpty()) {
                lore1 = lore1 + " for " + DURATION.get(skillLevel - 1) / 20;
                lore2 = lore2 + " for " + DURATION.get(skillLevel) / 20;
            }

            additions.add(lore1 + " -> " + lore2);
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
