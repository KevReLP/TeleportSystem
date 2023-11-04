package de.kevrecraft.teleportsystem.commands;

import de.kevrecraft.teleportsystem.TeleportSystem;
import de.kevrecraft.teleportsystem.managers.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    TeleportSystem plugin;

    public SpawnCommand(TeleportSystem pl) {
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Das darf nur ein Spieler tun!");
                    return;
                }

                if (args.length == 0) {
                    SpawnManager.getSpawn(((Player) sender)).teleport(((Player) sender), plugin);
                    sender.sendMessage(ChatColor.GREEN + "Du wurdest zum Spawn teleportiert!");
                    return;
                }


                sender.sendMessage(ChatColor.RED + "Fehler! Benutze /spawn help für eine hilfe!");
            }
        });
        return true;
    }
}
