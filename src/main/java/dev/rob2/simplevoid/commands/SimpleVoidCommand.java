package dev.rob2.simplevoid.commands;

import dev.rob2.simplevoid.SimpleVoid;
import dev.rob2.simplevoid.world.VoidChunkGenerator;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.io.File;

public class SimpleVoidCommand implements CommandExecutor {

    private final SimpleVoid plugin;

    public SimpleVoidCommand(SimpleVoid plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "SimpleVoid commands:");
            sender.sendMessage(ChatColor.GRAY + "/" + label + " createworld <name>");
            sender.sendMessage(ChatColor.GRAY + "/" + label + " portal create <name> [height] [width]");
            sender.sendMessage(ChatColor.GRAY + "/" + label + " portal delete <name>");
            sender.sendMessage(ChatColor.GRAY + "/" + label + " portal list");
            return true;
        }

        if (args[0].equalsIgnoreCase("createworld")) {
            return handleCreateWorld(sender, label, args);
        }

        if (args[0].equalsIgnoreCase("portal")) {
            return handlePortal(sender, label, args);
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /" + label);
        return true;
    }

    // ============================================================
    // CREATE WORLD (FIXED FOR PAPER 1.20+)
    // ============================================================
    private boolean handleCreateWorld(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return true;
        }

        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "Usage: /" + label + " createworld <worldname>");
            return true;
        }

        String worldName = args[1];

        // --- SAFETY CHECK: Prevent dimension creation ---
        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        File dimensionFolder = new File(Bukkit.getWorldContainer(),
                "world/dimensions/minecraft/" + worldName);

        if (dimensionFolder.exists()) {
            player.sendMessage(ChatColor.RED + "A DIMENSION named '" + worldName + "' already exists.");
            player.sendMessage(ChatColor.RED + "Delete this folder first:");
            player.sendMessage(ChatColor.GRAY + dimensionFolder.getPath());
            return true;
        }

        if (worldFolder.exists()) {
            player.sendMessage(ChatColor.RED + "A WORLD folder named '" + worldName + "' already exists.");
            player.sendMessage(ChatColor.RED + "Delete it first if you want a fresh void world:");
            player.sendMessage(ChatColor.GRAY + worldFolder.getPath());
            return true;
        }

        player.sendMessage(ChatColor.YELLOW + "Creating void world '" + worldName + "'...");

        // --- CORRECT WORLD CREATION FOR PAPER 1.20+ ---
        WorldCreator wc = new WorldCreator(worldName);
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);
        wc.generateStructures(false);
        wc.generator(new VoidChunkGenerator());

        World world = Bukkit.createWorld(wc);

        if (world == null) {
            player.sendMessage(ChatColor.RED + "Failed to create world.");
            return true;
        }

        // Spawn platform
        int y = 64;
        Location spawn = new Location(world, 0, y, 0);
        world.setSpawnLocation(spawn);

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                world.getBlockAt(x, y, z).setType(Material.BEDROCK);
            }
        }

        player.teleport(spawn.add(0.5, 1, 0.5));
        player.sendMessage(ChatColor.GREEN + "Void world created and loaded.");
        return true;
    }

    // ============================================================
    // PORTAL COMMANDS
    // ============================================================
    private boolean handlePortal(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run portal commands.");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /" + label + " portal <create|delete|list>");
            return true;
        }

        String sub = args[1];

        if (sub.equalsIgnoreCase("create")) {

            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "Usage: /" + label + " portal create <name> [height] [width]");
                return true;
            }

            String name = args[2];

            int height = 1;
            int width = 1;

            if (args.length >= 4) {
                try { height = Integer.parseInt(args[3]); }
                catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Height must be a number.");
                    return true;
                }
            }

            if (args.length >= 5) {
                try { width = Integer.parseInt(args[4]); }
                catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Width must be a number.");
                    return true;
                }
            }

            plugin.getPortalManager().createPortal(player, name, height, width);
            return true;
        }

        if (sub.equalsIgnoreCase("delete")) {

            if (args.length != 3) {
                player.sendMessage(ChatColor.RED + "Usage: /" + label + " portal delete <name>");
                return true;
            }

            plugin.getPortalManager().deletePortal(player, args[2]);
            return true;
        }

        if (sub.equalsIgnoreCase("list")) {
            plugin.getPortalManager().listPortals(player);
            return true;
        }

        player.sendMessage(ChatColor.RED + "Unknown portal subcommand.");
        return true;
    }
}
