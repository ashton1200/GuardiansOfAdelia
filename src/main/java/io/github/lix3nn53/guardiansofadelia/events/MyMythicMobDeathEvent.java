package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.killProtection.KillProtectionManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MyMythicMobDeathEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(MythicMobDeathEvent e) {
        Entity entity = e.getEntity();
        if (!(entity instanceof LivingEntity)) return;

        KillProtectionManager.onLivingEntityDeath((LivingEntity) entity, e);
        MyChunkEvents.DO_NOT_DELETE.remove(entity);
    }

}
