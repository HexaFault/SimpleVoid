package dev.rob2.simplevoid.commands;

import dev.rob2.simplevoid.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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
}
