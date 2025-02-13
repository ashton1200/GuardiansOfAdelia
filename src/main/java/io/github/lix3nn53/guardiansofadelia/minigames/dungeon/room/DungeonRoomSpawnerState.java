package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class DungeonRoomSpawnerState {
    // State
    BukkitTask secureSpawnRunner;
    private int mobsLeftToKill;

    public DungeonRoomSpawnerState(int mobsLeftToKill) {
        this.mobsLeftToKill = mobsLeftToKill;
    }

    public void onMobKill(int count) {
        mobsLeftToKill -= count;

        if (mobsLeftToKill <= 0) {
            if (secureSpawnRunner != null) secureSpawnRunner.cancel();
        }
    }

    public boolean isClear() {
        return mobsLeftToKill == 0;
    }

    public void startSecureSpawnerRunner(String mobCode, int mobLevel, Location dungeonStart, DungeonRoomSpawner spawner, int roomNo, int spawnerIndex) {
        secureSpawnRunner = new BukkitRunnable() {
            @Override
            public void run() {
                if (mobsLeftToKill <= 0) {
                    cancel();
                    return;
                }

                spawner.secureSpawn(mobCode, mobLevel, dungeonStart, roomNo, spawnerIndex, mobsLeftToKill);
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 20 * 15L, 20 * 15L);
    }

    public void stopSecureSpawnerRunner() {
        if (secureSpawnRunner != null) secureSpawnRunner.cancel();
    }
}
