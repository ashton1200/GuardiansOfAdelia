package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MMCustomMechanicsRegister implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(MythicMechanicLoadEvent event) {
        if (event.getMechanicName().equalsIgnoreCase("GoaDisarm")) {
            event.register(new MMMechanicDisarm(event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaRoot")) {
            event.register(new MMMechanicRoot(event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaSilence")) {
            event.register(new MMMechanicSilence(event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaStun")) {
            event.register(new MMMechanicStun(event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaHologram")) {
            event.register(new MMMechanicHologram(event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaGlow")) {
            event.register(new MMMechanicGlow(event.getConfig()));
        }
    }
}
