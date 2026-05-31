package dev.rob2.simplevoid;

public class ConfigManager {

    public static String getHubWorld() {
        return SimpleVoid.getInstance().getConfig().getString("hub-world", "hub");
    }

    public static String getRespawnMode() {
        return SimpleVoid.getInstance().getConfig().getString("respawn-mode", "same-world");
    }
}
