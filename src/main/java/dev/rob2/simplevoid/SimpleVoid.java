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

        // Load config.yml (hub-world, respawn-mode, portals)
        saveDefaultConfig();

        // ============================================================
        // LOAD PORTALS
        // ============================================================
        this.portalManager = new PortalManager(this);
        this.portalManager.loadPortals();

        // ============================================================
        // REGISTER COMMANDS & LISTENERS
        // ============================================================
        getCommand("simplevoid").setExecutor(new SimpleVoidCommand(this));

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getServer().getPluginManager().registerEvents(new PortalListener(this.portalManager), this);

        getLogger().info("SimpleVoid enabled successfully.");
    }

    @Override
    public void onDisable() {
        if (portalManager != null) {
            portalManager.savePortals();
        }
        getLogger().info("SimpleVoid disabled.");
    }

    public static SimpleVoid getInstance() {
        return instance;
    }

    public PortalManager getPortalManager() {
        return portalManager;
    }
}
