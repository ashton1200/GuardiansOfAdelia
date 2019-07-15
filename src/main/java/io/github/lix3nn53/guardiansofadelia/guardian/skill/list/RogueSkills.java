package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.DirectionCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SingleTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.MeleeAttackTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class RogueSkills {

    public static List<Skill> getSet() {
        List<Skill> skills = new ArrayList<>();

        skills.add(getOne());
        skills.add(getTwo());
        skills.add(getThree());
        skills.add(getPassive());
        skills.add(getUltimate());

        return skills;
    }

    private static Skill getOne() {
        List<String> description = new ArrayList<>();
        description.add("Jump towards your target and deal damage.");
        description.add("If you are behind your target also apply slow.");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Claw Swipe", Material.IRON_HOE, 32, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> ranges = new ArrayList<>();
        ranges.add(6D);
        ranges.add(6D);
        ranges.add(6D);
        ranges.add(6D);
        ranges.add(6D);
        ranges.add(6D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 1, ranges, 4);

        DirectionCondition directionCondition = new DirectionCondition(false);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(3);
        amplifiers.add(4);
        amplifiers.add(5);
        amplifiers.add(6);
        PotionEffectMechanic slow = new PotionEffectMechanic(PotionEffectType.SLOW, ticks, amplifiers);

        WarpTargetMechanic warpTargetMechanic = new WarpTargetMechanic(false);

        List<Double> damages = new ArrayList<>();
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(singleTarget);
        singleTarget.addChildren(directionCondition);
        directionCondition.addChildren(slow);

        singleTarget.addChildren(warpTargetMechanic);
        singleTarget.addChildren(damageMechanic);
        singleTarget.addChildren(new SoundMechanic(GoaSound.SKILL_POISON_SLASH));

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add("Dash forward and gain shields and");
        description.add("resistance to fall damage");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Void Dash", Material.IRON_HOE, 10, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> forwards = new ArrayList<>();
        forwards.add(1.5);
        List<Double> upwards = new ArrayList<>();
        upwards.add(1D);
        upwards.add(1.8);
        upwards.add(1.85);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        WarpMechanic warpMechanic = new WarpMechanic(false, forwards, upwards, right);

        LandTrigger landTrigger = new LandTrigger();

        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, new ArrayList<>());//0 for infinite
        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL);

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(warpMechanic);
        selfTarget.addChildren(immunityMechanic);
        selfTarget.addChildren(landTrigger);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_PROJECTILE_VOID));

        landTrigger.addChildren(immunityRemoveMechanic);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add("Throw shurikens one by one that");
        description.add("deals damage");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Shurikens", Material.IRON_HOE, 56, description, reqLevels, reqPoints, manaCosts, cooldowns);

        List<Integer> repetitions = new ArrayList<>();
        repetitions.add(3);
        repetitions.add(4);
        repetitions.add(5);
        repetitions.add(6);
        RepeatMechanic repeatMechanic = new RepeatMechanic(10L, repetitions);

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, Egg.class);

        SelfTarget selfTarget = new SelfTarget();
        skill.addTrigger(selfTarget);
        selfTarget.addChildren(repeatMechanic);
        repeatMechanic.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_THROW_SMALL_OBJECT));

        List<Double> damages = new ArrayList<>();
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.RANGED);

        projectileMechanic.addChildren(damageMechanic);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add("Deal bonus damage when you hit");
        description.add("targets from behind with melee attacks");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Backstab", Material.IRON_HOE, 17, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        MeleeAttackTrigger meleeAttackTrigger = new MeleeAttackTrigger(cooldowns);

        DirectionCondition directionCondition = new DirectionCondition(false);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(meleeAttackTrigger);
        meleeAttackTrigger.addChildren(directionCondition);

        List<Double> damages = new ArrayList<>();
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        meleeAttackTrigger.addChildren(damageMechanic);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        //TODO skill including critic damage bonus
        description.add("Prepare for 3 seconds then");
        description.add("jump behind your target and");
        description.add("apply wither effect");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Phantom Strike", Material.IRON_HOE, 45, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> ranges = new ArrayList<>();
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 1, ranges, 4);

        WarpTargetMechanic warpTargetMechanic = new WarpTargetMechanic(false);


        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Integer> amplifiers = new ArrayList<>();
        ticks.add(2);
        ticks.add(2);
        ticks.add(2);
        ticks.add(2);
        List<Double> multipliers = new ArrayList<>();
        multipliers.add(0.1);
        BuffMechanic physicalDamageBuff = new BuffMechanic(BuffType.PHYSICAL_DAMAGE, multipliers, ticks);
        BuffMechanic magicalDamageBuff = new BuffMechanic(BuffType.MAGIC_DAMAGE, multipliers, ticks);
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.INCREASE_DAMAGE, ticks, amplifiers);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(singleTarget);
        singleTarget.addChildren(warpTargetMechanic);

        return skill;
    }
}
