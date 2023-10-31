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

    private static final String adminPermission = "warp.admin";

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
                        warp(sender, args);
                        return;
                    }

                }

                sender.sendMessage(ChatColor.RED + "Fehler: Benutze /warp help um eine Hilfe zu erhalten!");

            }
        });
        return true;
    }

    private void warp(CommandSender sender, String[] args) {
        Entity entity = (Entity) sender;
        WarpManager.teleport(args[0], entity);
        sender.sendMessage(ChatColor.GREEN + "Du wurdest zu " + args[0] + " teleportiert!");
    }

    private void sendHelp(CommandSender sender) {
        String color = ChatColor.BLUE.toString();
        String commandColor = ChatColor.GRAY.toString();
        String arrow = ChatColor.WHITE.toString() + "→";

        sender.sendMessage(color + ChatColor.BOLD + "------- Help: Warp -------");
        sender.sendMessage(commandColor + "/warp <name>" +  arrow + color +" Teleportiert dich zu dem eingegebenen Warppunkt.");

        if(sender.hasPermission(adminPermission)) {
            sender.sendMessage(commandColor + "/warp set <name>" +  arrow + color +" Setzt einen Warppunkt mit dem eingegebenen Name.");
            sender.sendMessage(commandColor + "/warp remove <name>" +  arrow + color +" Setzt einen Warppunkt mit dem eingegebenen Name.");
        }
    }

}
