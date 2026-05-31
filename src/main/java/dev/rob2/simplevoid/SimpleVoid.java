package dev.rob2.simplevoid;

import dev.rob2.simplevoid.commands.SimpleVoidCommand;
import dev.rob2.simplevoid.listeners.JoinListener;
import dev.rob2.simplevoid.listeners.PortalListener;
import dev.rob2.simplevoid.listeners.RespawnListener;
import dev.rob2.simplevoid.portal.PortalManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleVoid extends JavaPlugin {

    private static SimpleVoid instance;
    private PortalManager portalManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.portalManager = new PortalManager(this);
        this.portalManager.loadPortals();

        getCommand("simplevoid").setExecutor(new SimpleVoidCommand(this));

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getServer().getPluginManager().registerEvents(new PortalListener(this.portalManager), this);
    }

    @Override
    public void onDisable() {
        if (portalManager != null) {
            portalManager.savePortals();
        }
    }

    public static SimpleVoid getInstance() {
        return instance;
    }

    public PortalManager getPortalManager() {
        return portalManager;
    }
}
