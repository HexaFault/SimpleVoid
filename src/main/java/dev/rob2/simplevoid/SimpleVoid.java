package dev.rob2.simplevoid;

import org.bukkit.plugin.java.JavaPlugin;

public class SimpleVoid extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("simplevoid").setExecutor(new CreateVoidWorldCommand(this));
    }
}

