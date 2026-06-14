package dev.rob2.simplevoid.commands;

import dev.rob2.simplevoid.ConfigManager;
import dev.rob2.simplevoid.SimpleVoid;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
            sender.sendMessage(ChatColor.GRAY + "/" + label + " portal <create|delete|list>");
            sender.sendMessage(ChatColor.GRAY + "/" + label + " hub");
            sender.sendMessage(ChatColor.GRAY + "/" + label + " sethub");
            sender.sendMessage(ChatColor.GRAY + "/" + label + " reload");
            return true;
        }

        // ============================================================
        // HUB COMMAND
        // ============================================================
        if (args[0].equalsIgnoreCase("hub")) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage("Players only.");
                return true;
            }

            String hubName = ConfigManager.getHubWorld();
            World hub = Bukkit.getWorld(hubName);

            if (hub == null) {
                player.sendMessage(ChatColor.RED + "Hub world '" + hubName + "' does not exist!");
                return true;
            }

            player.teleport(hub.getSpawnLocation());
            player.sendMessage(ChatColor.GREEN + "Teleported to hub world: " + hubName);
            return true;
        }

        // ============================================================
        // SETHUB COMMAND
        // ============================================================
        if (args[0].equalsIgnoreCase("sethub")) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage("Players only.");
                return true;
            }

            String worldName = player.getWorld().getName();

            plugin.getConfig().set("hub-world", worldName);
            plugin.saveConfig();

            player.sendMessage(ChatColor.GREEN + "Hub world set to: " + worldName);
            return true;
        }

        // ============================================================
        // RELOAD COMMAND
        // ============================================================
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "SimpleVoid config reloaded.");
            return true;
        }

        // ============================================================
        // CREATE WORLD
        // ============================================================
        if (args[0].equalsIgnoreCase("createworld")) {
            return handleCreateWorld(sender, label, args);
        }

        // ============================================================
        // PORTAL COMMANDS
        // ============================================================
        if (args[0].equalsIgnoreCase("portal")) {
            return handlePortal(sender, label, args);
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /" + label);
        return true;
    }

    // ============================================================
    // CREATE WORLD (DATAPACK-BASED VOID DIMENSION)
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

        // --- SAFETY CHECK: Prevent dimension creation conflicts ---
        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        File dimensionFolder = new File(Bukkit.getWorldContainer(),
                "world/dimensions/minecraft/" + worldName);

        if (dimensionFolder.exists()) {
            player.sendMessage(ChatColor.RED + "A DIMENSION named '" + worldName + "' already exists.");
            player.sendMessage(ChatColor.GRAY + dimensionFolder.getPath());
            return true;
        }

        if (worldFolder.exists()) {
            player.sendMessage(ChatColor.RED + "A WORLD folder named '" + worldName + "' already exists.");
            player.sendMessage(ChatColor.GRAY + worldFolder.getPath());
            return true;
        }

        // --- STEP 1: Create datapack structure ---
        try {
            createDatapackStructure(worldName);
            writeDimensionTypeJson(worldName);
            writeDimensionJson(worldName);

            player.sendMessage(ChatColor.GREEN + "Datapack created for '" + worldName + "'.");
            player.sendMessage(ChatColor.YELLOW + "Restart server or run /reload to activate.");
        } catch (IOException e) {
            player.sendMessage(ChatColor.RED + "Failed to create datapack files. Check console.");
            e.printStackTrace();
        }

        return true;
    }

    // ============================================================
    // STEP 1 — CREATE DATAPACK FOLDERS + pack.mcmeta
    // ============================================================
    private void createDatapackStructure(String worldName) throws IOException {

        Path worldFolder = Bukkit.getWorldContainer().toPath().resolve("world");

        Path datapackRoot = worldFolder
                .resolve("datapacks")
                .resolve("simplevoid_" + worldName);

        Files.createDirectories(datapackRoot);

        Path packMeta = datapackRoot.resolve("pack.mcmeta");
        String packMetaContent = """
        {
          "pack": {
            "pack_format": 48,
            "description": "SimpleVoid dimension datapack"
          }
        }
        """;
        Files.writeString(packMeta, packMetaContent);

        Files.createDirectories(datapackRoot.resolve("data").resolve(worldName).resolve("dimension_type"));
        Files.createDirectories(datapackRoot.resolve("data").resolve(worldName).resolve("dimension"));
    }

    // ============================================================
    // STEP 2A — WRITE dimension_type JSON
    // ============================================================
    private void writeDimensionTypeJson(String worldName) throws IOException {
        Path datapackRoot = Bukkit.getWorldContainer().toPath()
                .resolve("world/datapacks/simplevoid_" + worldName);

        Path file = datapackRoot
                .resolve("data").resolve(worldName)
                .resolve("dimension_type")
                .resolve(worldName + ".json");

        String json = """
        {
          "ultrawarm": false,
          "natural": false,
          "piglin_safe": false,
          "respawn_anchor_works": false,
          "bed_works": true,
          "has_raids": false,
          "min_y": 0,
          "height": 384,
          "logical_height": 384,
          "coordinate_scale": 1.0,
          "ambient_light": 1.0
        }
        """;

        Files.writeString(file, json);
    }

    // ============================================================
    // STEP 2B — WRITE dimension JSON
    // ============================================================
    private void writeDimensionJson(String worldName) throws IOException {
        Path datapackRoot = Bukkit.getWorldContainer().toPath()
                .resolve("world/datapacks/simplevoid_" + worldName);

        Path file = datapackRoot
                .resolve("data").resolve(worldName)
                .resolve("dimension")
                .resolve(worldName + ".json");

        String json = """
        {
          "type": "%s:%s",
          "generator": {
            "type": "minecraft:flat",
            "settings": {
              "layers": [],
              "biome": "minecraft:the_void",
              "structure_overrides": []
            }
          },
          "spawn": {
            "x": 0,
            "y": 64,
            "z": 0,
            "angle": 0
          }
        }
        """.formatted(worldName, worldName);

        Files.writeString(file, json);
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
