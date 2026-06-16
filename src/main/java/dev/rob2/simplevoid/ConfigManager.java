package dev.rob2.simplevoid;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static SimpleVoid plugin;

    public static void init(SimpleVoid pl) {
        plugin = pl;
        plugin.saveDefaultConfig(); // ensures config.yml exists
    }

    public static String getHubWorld() {
        FileConfiguration config = plugin.getConfig();
        return config.getString("hub-world", "hub");
    }

    public static String getRespawnMode() {
        FileConfiguration config = plugin.getConfig();
        return config.getString("respawn-mode", "same-world");
    }
}
