package de.kevrecraft.teleportsystem.commands;

import de.kevrecraft.teleportsystem.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(help(sender, args))
            return true;
        else if (set(sender, args))
            return true;

        sender.sendMessage(ChatColor.RED + "Fehler: Benutzte /home help für eine hilfestellung!");
        return true;
    }

    public boolean set(CommandSender sender, String[] args) {
        if(args.length != 1 && args.length != 2)
            return false;
        if(!args[0].equalsIgnoreCase("set"))
            return false;
        if(!(sender instanceof Player))
            return false;
        Player player = (Player) sender;

        if(args.length == 1) {
            HomeManager.get(player).set("home", player.getLocation());
            player.sendMessage(ChatColor.GREEN + "Dein Home point wurde gesetzt!");
            return true;
        }
        HomeManager.get(player).set(args[1], player.getLocation());
        player.sendMessage(ChatColor.GREEN + "Dein Home point wurde gesetzt!");


        return true;
    }

    public boolean help(CommandSender sender, String[] args) {
        if(args.length != 1)
            return false;
        if(!args[0].equalsIgnoreCase("help"))
            return false;

        String color = ChatColor.BLUE.toString();
        String commandColor = ChatColor.GRAY.toString();
        String arrow = ChatColor.WHITE.toString() + "→";

        sender.sendMessage(color + ChatColor.BOLD + "------- Help: Home -------");
        sender.sendMessage(commandColor + "/home" +  arrow + color +" Teleportiert dich zu deinem home.");
        sender.sendMessage(commandColor + "/home help " + arrow + color + " Gibt dir eine Hilfestellung zu dem Befehl.");
        sender.sendMessage(commandColor + "/home set" + arrow + color + " Setzt ein Homepunkt mit dem Namen home.");
        sender.sendMessage(commandColor + "/home set <name>" + arrow + color + " Setzt ein Homepunkt mit dem Angegebenen Namen.");

        return true;
    }

}
