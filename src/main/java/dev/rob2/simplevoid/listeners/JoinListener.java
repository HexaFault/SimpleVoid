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
        if (!event.getPlayer().hasPlayedBefore()) {
            String hubName = ConfigManager.getHubWorld();
            World hub = Bukkit.getWorld(hubName);
            if (hub != null) {
                Location spawn = hub.getSpawnLocation();
                event.getPlayer().teleport(spawn);
            }
        }
    }
}
