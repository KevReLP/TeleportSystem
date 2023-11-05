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

    public static final String adminPermission = "spawn.admin";

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
                    spawn(sender);
                    return;
                }

                if(args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        sendHelp(sender);
                        return;
                    }
                }

                if (sender.hasPermission(adminPermission))
                    if (args.length == 2) {
                        if(args[0].equalsIgnoreCase("tp")) {
                            spawnAdmin(sender, args[1]);
                            return;
                        }

                        if(args[0].equalsIgnoreCase("set")) {
                            setAdmin(sender, args[1]);
                            return;
                        }

                        if(args[0].equalsIgnoreCase("remove")) {
                            removeAdmin(sender, args[1]);
                            return;
                        }
                    }


                sender.sendMessage(ChatColor.RED + "Fehler! Benutze /spawn help für eine hilfe!");
            }
        });
        return true;
    }
    private void removeAdmin(CommandSender sender, String name) {
        if(SpawnManager.exists(name)) {
            SpawnManager.setSpawn(name, null);
            sender.sendMessage(ChatColor.GREEN + "Der Spawn " + name + " wurde gelöscht!");
        } else {
            sender.sendMessage(ChatColor.YELLOW + "Der Spawn " + name + " existiert nicht!");
        }
    }

    private void setAdmin(CommandSender sender, String name) {
        SpawnManager.setSpawn(name, ((Player) sender).getLocation());
        sender.sendMessage(ChatColor.GREEN + "Der Spawn " + name + " wurde gesetzt!");
    }

    private void spawnAdmin(CommandSender sender, String name) {
        if(SpawnManager.exists(name)) {
            SpawnManager.getSpawn(name).teleport(((Player) sender), plugin);
            sender.sendMessage(ChatColor.GREEN + "Du wurdest zum Spawn teleportiert!");
        } else {
            sender.sendMessage(ChatColor.RED + "Der eingegebene Spawn " + name + " existiert nicht!");
        }
    }

    private void spawn(CommandSender sender) {
        SpawnManager.getSpawn(((Player) sender)).teleport(((Player) sender), plugin);
        sender.sendMessage(ChatColor.GREEN + "Du wurdest zum Spawn teleportiert!");
    }

    private void sendHelp(CommandSender sender) {
        String color = ChatColor.BLUE.toString();
        String commandColor = ChatColor.GRAY.toString();
        String arrow = ChatColor.WHITE.toString() + "→";

        sender.sendMessage(color + ChatColor.BOLD + "------- Help: Spawn -------");
        sender.sendMessage(commandColor + "/spawn " +  arrow + color +" Teleportiert dich zum Spawn.");
        sender.sendMessage(commandColor + "/spawn help " +  arrow + color +" Sendet dir eine Hilfestellung.");

        if (sender.hasPermission(adminPermission)) {
            sender.sendMessage(commandColor + "/spawn set <name> " +  arrow + color +" Setzt einen Spawn mit dem Namen.");
            sender.sendMessage(commandColor + "/spawn remove <name> " +  arrow + color +" Löscht einen Spawn mit den Namen.");
            sender.sendMessage(commandColor + "/spawn tp <name> " +  arrow + color +" Teleportiert dich zum Spawn mit dem eingegebenen Namen.");
        }
    }

}
