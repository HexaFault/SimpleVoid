package dev.rob2.simplevoid.listeners;

import dev.rob2.simplevoid.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        // Only teleport if this is a first join detected earlier
        if (!FirstJoinTracker.isFirstJoin(event.getPlayer().getUniqueId())) {
            return;
        }

        String hubName = ConfigManager.getHubWorld();
        World hub = Bukkit.getWorld(hubName);

        if (hub != null) {
            Location spawn = hub.getSpawnLocation();
            event.getPlayer().teleport(spawn);
        }
    }
}
