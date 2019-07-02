package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class TriggerListener {

    private static HashMap<Player, LandTrigger> playerToLandTrigger = new HashMap<>();
    private static HashMap<Player, InitializeTrigger> playerToInitializeTrigger = new HashMap<>();

    public static void onPlayerQuit(Player player) {
        playerToLandTrigger.remove(player);
    }

    public static void startListeningLandTrigger(Player player, LandTrigger landTrigger) {
        playerToLandTrigger.put(player, landTrigger);
    }

    public static void onPlayerLandGround(Player player) {
        if (playerToLandTrigger.containsKey(player)) {
            playerToLandTrigger.get(player).callback(player);
            playerToLandTrigger.remove(player);
        }
    }

    public static void onSkillLearn(Player player, InitializeTrigger initializeTrigger) {
        initializeTrigger.startEffects();
        playerToInitializeTrigger.put(player, initializeTrigger);
    }

    public static void onSkillUnlearn(Player player) {
        if (playerToInitializeTrigger.containsKey(player)) {
            playerToInitializeTrigger.get(player).stopEffects();
            playerToInitializeTrigger.remove(player);
        }
    }
}
