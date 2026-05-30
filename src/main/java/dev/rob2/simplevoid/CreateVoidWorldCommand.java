package dev.rob2.simplevoid;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.generator.WorldCreator;

public class CreateVoidWorldCommand implements CommandExecutor {

    private final SimpleVoid plugin;

    public CreateVoidWorldCommand(SimpleVoid plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /simplevoid <worldname>");
            return true;
        }

        String worldName = args[0];

        if (Bukkit.getWorld(worldName) != null) {
            player.sendMessage(ChatColor.RED + "World already exists.");
            return true;
        }

        player.sendMessage(ChatColor.YELLOW + "Creating void world '" + worldName + "'...");

        WorldCreator wc = new WorldCreator(worldName);
        wc.generator(new VoidChunkGenerator());
        World world = wc.createWorld();

        if (world == null) {
            player.sendMessage(ChatColor.RED + "Failed to create world.");
            return true;
        }

        // Spawn location
        int y = 64;
        Location spawn = new Location(world, 0, y, 0);
        world.setSpawnLocation(spawn);

        // Build 3x3 platform
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                world.getBlockAt(x, y, z).setType(Material.BEDROCK);
            }
        }

        // Teleport player
        player.teleport(spawn.add(0.5, 1, 0.5));

        player.sendMessage(ChatColor.GREEN + "Void world created and loaded.");

        return true;
    }
}

