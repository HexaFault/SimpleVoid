package dev.rob2.simplevoid.listeners;

import dev.rob2.simplevoid.portal.PortalData;
import dev.rob2.simplevoid.portal.PortalManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PortalListener implements Listener {

    private final PortalManager portalManager;

    public PortalListener(PortalManager portalManager) {
        this.portalManager = portalManager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();
        if (to == null) return;

        // Only trigger when entering a new block
        if (to.getBlockX() == from.getBlockX()
                && to.getBlockY() == from.getBlockY()
                && to.getBlockZ() == from.getBlockZ()) {
            return;
        }

        PortalData portal = portalManager.getPortalAt(to);
        if (portal == null) return;

        Location target = portal.getTargetLocation();
        if (target != null) {
            event.getPlayer().teleport(target);
        }
    }
}
