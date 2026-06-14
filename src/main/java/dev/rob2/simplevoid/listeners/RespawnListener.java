package dev.rob2.simplevoid.listeners;

import dev.rob2.simplevoid.ConfigManager;
import dev.rob2.simplevoid.SimpleVoid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {

        // 1. Respect valid bed spawns
        if (event.isBedSpawn()) {
            return;
        }

        // 2. Otherwise, send them to the hub world
        String hubName = ConfigManager.getHubWorld();
        World hub = Bukkit.getWorld(hubName);

        if (hub == null) {
            SimpleVoid.getInstance().getLogger().warning(
                    "Hub world '" + hubName + "' does not exist! Check config.yml."
            );
            return;
        }

        Location hubSpawn = hub.getSpawnLocation();
        event.setRespawnLocation(hubSpawn);
    }
}
