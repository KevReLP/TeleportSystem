package de.kevrecraft.teleportsystem.commands;

import de.kevrecraft.teleportsystem.TeleportSystem;
import de.kevrecraft.teleportsystem.managers.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    public static final String adminPermission = "warp.admin";

    private TeleportSystem plugin;

    public WarpCommand(TeleportSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Fehler! Das kann nur ein Spieler tun!");
                    return;
                }

                if(args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        sendHelp(sender);
                        return;
                    }
                    if(WarpManager.exits(args[0])) {
                        warp(sender, args[0]);
                        return;
                    } else {
                        sender.sendMessage(ChatColor.RED + "Der eingegebene Warppunkt " + args[0] + " existiert nicht!");
                        return;
                    }

                }

                if (args.length == 2) {
                    if(sender.hasPermission(adminPermission)) {
                        if(args[0].equalsIgnoreCase("set")) {
                            set(sender, args[1]);
                            return;
                        }
                        if (args[0].equalsIgnoreCase("remove")) {
                            if(WarpManager.exits(args[1])) {
                                remove(sender, args[1]);
                                return;
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "Der Warppunkt " + args[1] + " existiert nicht!");
                                return;
                            }
                        }
                    }
                }

                sender.sendMessage(ChatColor.RED + "Fehler: Benutze /warp help um eine Hilfe zu erhalten!");

            }
        });
        return true;
    }

    private void set(CommandSender sender, String name) {
        WarpManager.set(name, ((Player) sender).getLocation());
        sender.sendMessage(ChatColor.GREEN + "Du hast den Warppunkt " + name + " erfolgreich gesetzt!");
    }

    private void remove(CommandSender sender, String name) {
        WarpManager.set(name, null);
        sender.sendMessage(ChatColor.GREEN + "Du hast den Warppunkt " + name + " gelöscht!");
    }

    private void warp(CommandSender sender, String name) {
        Entity entity = (Entity) sender;
        WarpManager.teleport(name, entity);
        sender.sendMessage(ChatColor.GREEN + "Du wurdest zu " + name + " teleportiert!");
    }

    private void sendHelp(CommandSender sender) {
        String color = ChatColor.BLUE.toString();
        String commandColor = ChatColor.GRAY.toString();
        String arrow = ChatColor.WHITE.toString() + "→";

        sender.sendMessage(color + ChatColor.BOLD + "------- Help: Warp -------");
        sender.sendMessage(commandColor + "/warp <name>" +  arrow + color +" Teleportiert dich zu dem eingegebenen Warppunkt.");

        if(sender.hasPermission(adminPermission)) {
            sender.sendMessage(commandColor + "/warp set <name>" +  arrow + color +" Setzt einen Warppunkt mit dem eingegebenen Name.");
            sender.sendMessage(commandColor + "/warp remove <name>" +  arrow + color +" Löscht einen Warppunkt mit dem eingegebenen Name.");
        }
    }
}
