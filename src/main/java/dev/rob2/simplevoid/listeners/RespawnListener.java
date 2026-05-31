package dev.rob2.simplevoid.listeners;

import dev.rob2.simplevoid.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        String mode = ConfigManager.getRespawnMode();

        if (mode.equalsIgnoreCase("hub")) {
            String hubName = ConfigManager.getHubWorld();
            World hub = Bukkit.getWorld(hubName);
            if (hub != null) {
                event.setRespawnLocation(hub.getSpawnLocation());
            }
        }
        // "same-world" -> do nothing (vanilla)
    }
}
