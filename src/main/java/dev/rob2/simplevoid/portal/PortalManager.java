package dev.rob2.simplevoid.portal;

import dev.rob2.simplevoid.ConfigManager;
import dev.rob2.simplevoid.SimpleVoid;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PortalManager {

    private final SimpleVoid plugin;
    private final Map<String, PortalData> portals = new HashMap<>();

    public PortalManager(SimpleVoid plugin) {
        this.plugin = plugin;
    }

    public void loadPortals() {
        portals.clear();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("portals");
        if (section == null) return;

        for (String name : section.getKeys(false)) {
            ConfigurationSection p = section.getConfigurationSection(name);
            if (p == null) continue;

            String world = p.getString("world");
            int x = p.getInt("x");
            int y = p.getInt("y");
            int z = p.getInt("z");
            int height = p.getInt("height", 1);
            int width = p.getInt("width", 1);

            String targetWorld = p.getString("target-world", ConfigManager.getHubWorld());
            double tx = p.getDouble("target-x", 0.5);
            double ty = p.getDouble("target-y", 80);
            double tz = p.getDouble("target-z", 0.5);

            PortalData data = new PortalData(
                    name, world, x, y, z, height, width,
                    targetWorld, tx, ty, tz
            );

            portals.put(name.toLowerCase(), data);
        }
    }

    public void savePortals() {
        plugin.getConfig().set("portals", null);

        for (PortalData p : portals.values()) {
            String base = "portals." + p.getName();
            plugin.getConfig().set(base + ".world", p.getWorldName());
            plugin.getConfig().set(base + ".x", p.getBaseX());
            plugin.getConfig().set(base + ".y", p.getBaseY());
            plugin.getConfig().set(base + ".z", p.getBaseZ());
            plugin.getConfig().set(base + ".height", p.getHeight());
            plugin.getConfig().set(base + ".width", p.getWidth());

            Location t = p.getTargetLocation();
            if (t != null) {
                plugin.getConfig().set(base + ".target-world", t.getWorld().getName());
                plugin.getConfig().set(base + ".target-x", t.getX());
                plugin.getConfig().set(base + ".target-y", t.getY());
                plugin.getConfig().set(base + ".target-z", t.getZ());
            }
        }

        plugin.saveConfig();
    }

    public void createPortal(Player player, String name, int height, int width) {
        Location base = player.getLocation().getBlock().getLocation();

        String world = base.getWorld().getName();
        String targetWorld = ConfigManager.getHubWorld();
        Location target = player.getServer().getWorld(targetWorld).getSpawnLocation();

        PortalData data = new PortalData(
                name,
                world,
                base.getBlockX(),
                base.getBlockY(),
                base.getBlockZ(),
                height,
                width,
                targetWorld,
                target.getX(),
                target.getY(),
                target.getZ()
        );

        portals.put(name.toLowerCase(), data);
        savePortals();

        player.sendMessage("Created portal '" + name + "' at "
                + world + " (" + base.getBlockX() + "," + base.getBlockY() + "," + base.getBlockZ() + ") "
                + "size " + height + "×" + width);
    }

    public void deletePortal(Player player, String name) {
        if (portals.remove(name.toLowerCase()) != null) {
            savePortals();
            player.sendMessage("Portal '" + name + "' deleted.");
        } else {
            player.sendMessage("No portal named '" + name + "'.");
        }
    }

    public void listPortals(Player player) {
        if (portals.isEmpty()) {
            player.sendMessage("No portals created.");
            return;
        }

        player.sendMessage("Portals:");
        for (PortalData p : portals.values()) {
            player.sendMessage(" - " + p.getName()
                    + " @ " + p.getWorldName()
                    + " (" + p.getBaseX() + "," + p.getBaseY() + "," + p.getBaseZ() + ") "
                    + "size " + p.getHeight() + "×" + p.getWidth());
        }
    }

    public PortalData getPortalAt(Location loc) {
        for (PortalData p : portals.values()) {
            if (p.isInside(loc)) return p;
        }
        return null;
    }
}
