package dev.rob2.simplevoid.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FirstJoinTracker implements Listener {

    private static final Set<UUID> firstJoin = new HashSet<>();

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            firstJoin.add(event.getPlayer().getUniqueId());
        }
    }

    public static boolean isFirstJoin(UUID uuid) {
        return firstJoin.remove(uuid);
    }
}
