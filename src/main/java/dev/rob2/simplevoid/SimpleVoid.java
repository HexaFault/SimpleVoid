package dev.rob2.simplevoid;

import dev.rob2.simplevoid.commands.SimpleVoidCommand;
import dev.rob2.simplevoid.listeners.JoinListener;
import dev.rob2.simplevoid.listeners.PortalListener;
import dev.rob2.simplevoid.listeners.RespawnListener;
import dev.rob2.simplevoid.portal.PortalManager;
import dev.rob2.simplevoid.world.VoidChunkGenerator;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SimpleVoid extends JavaPlugin {

    private static SimpleVoid instance;
    private PortalManager portalManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        // ============================================================
        // CREATE ANY QUEUED WORLDS (Paper 1.20+ requires startup creation)
        // ============================================================
        String pending = getConfig().getString("pending-world");
        if (pending != null && !pending.isEmpty()) {
            getLogger().info("Creating queued void world: " + pending);
            createVoidWorld(pending);

            // Clear queue
            getConfig().set("pending-world", null);
            saveConfig();
        }

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

    // ============================================================
    // ACTUAL WORLD CREATION (RUNS DURING STARTUP ONLY)
    // ============================================================
    public void createVoidWorld(String worldName) {

        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        if (worldFolder.exists()) {
            getLogger().warning("World folder already exists: " + worldName);
            return;
        }

        getLogger().info("Generating void world: " + worldName);

        WorldCreator wc = new WorldCreator(worldName);
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);
        wc.generateStructures(false);
        wc.generator(new VoidChunkGenerator());

        World world = Bukkit.createWorld(wc);

        if (world == null) {
            getLogger().severe("Failed to create world: " + worldName);
            return;
        }

        // Create spawn platform
        int y = 64;
        Location spawn = new Location(world, 0, y, 0);
        world.setSpawnLocation(spawn);

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                world.getBlockAt(x, y, z).setType(Material.BEDROCK);
            }
        }

        getLogger().info("Void world '" + worldName + "' created successfully.");
    }
}
