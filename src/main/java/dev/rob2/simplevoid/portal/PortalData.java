package dev.rob2.simplevoid.portal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class PortalData {

    private final String name;
    private final String worldName;
    private final int baseX, baseY, baseZ;
    private final int height, width;

    private final String targetWorldName;
    private final double targetX, targetY, targetZ;

    public PortalData(String name,
                      String worldName,
                      int baseX, int baseY, int baseZ,
                      int height, int width,
                      String targetWorldName,
                      double targetX, double targetY, double targetZ) {

        this.name = name;
        this.worldName = worldName;
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;
        this.height = height;
        this.width = width;
        this.targetWorldName = targetWorldName;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    public String getName() { return name; }
    public String getWorldName() { return worldName; }
    public int getBaseX() { return baseX; }
    public int getBaseY() { return baseY; }
    public int getBaseZ() { return baseZ; }
    public int getHeight() { return height; }
    public int getWidth() { return width; }

    public Location getTargetLocation() {
        World w = Bukkit.getWorld(targetWorldName);
        return w == null ? null : new Location(w, targetX, targetY, targetZ);
    }

    public boolean isInside(Location loc) {
        if (!loc.getWorld().getName().equals(worldName)) return false;

        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        // Portal is a vertical rectangle on a single Z plane
        if (z != baseZ) return false;

        boolean withinX = x >= baseX && x < baseX + width;
        boolean withinY = y >= baseY && y < baseY + height;

        return withinX && withinY;
    }
}
