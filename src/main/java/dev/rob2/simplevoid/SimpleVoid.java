package dev.rob2.simplevoid;

import dev.rob2.simplevoid.commands.HubCommand;
import dev.rob2.simplevoid.commands.SimpleVoidCommand;
import dev.rob2.simplevoid.listeners.JoinListener;
import dev.rob2.simplevoid.listeners.PortalListener;
import dev.rob2.simplevoid.listeners.RespawnListener;
import dev.rob2.simplevoid.portal.PortalManager;
import dev.rob2.simplevoid.listeners.FirstJoinTracker;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleVoid extends JavaPlugin {

    private static SimpleVoid instance;
    private PortalManager portalManager;

    @Override
    public void onEnable() {
        instance = this;

        // Load config.yml (hub-world, portals)
        saveDefaultConfig();

        // ============================================================
        // LOAD PORTALS
        // ============================================================
        this.portalManager = new PortalManager(this);
        this.portalManager.loadPortals();

        // ============================================================
        // REGISTER COMMANDS
        // ============================================================
        getCommand("simplevoid").setExecutor(new SimpleVoidCommand(this));
        getCommand("hub").setExecutor(new HubCommand());   // <-- NEW

        // ============================================================
        // REGISTER LISTENERS
        // ============================================================
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getServer().getPluginManager().registerEvents(new PortalListener(this.portalManager), this);
        getServer().getPluginManager().registerEvents(new FirstJoinTracker(), this);

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
