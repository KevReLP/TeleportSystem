package de.kevrecraft.teleportsystem.commands;

import de.kevrecraft.teleportsystem.TeleportSystem;
import de.kevrecraft.teleportsystem.managers.HomeManager;
import de.kevrecraft.teleportsystem.TeleportPoints.HomePoint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Das kann nur ein Spieler tun!");
            return true;
        }


        if(help(sender, args))
            return true;
        else if (set(sender, args))
            return true;
        if(teleport(sender, args))
            return true;
        if(teleportCustom(sender, args))
            return true;
        if(remove(sender, args))
            return true;
        if(teleportCustomAdmin(sender, args))
            return true;
        if (setAdmin(sender, args))
            return true;
        if(removeAdmin(sender, args))

        sender.sendMessage(ChatColor.RED + "Fehler: Benutzte /home help für eine hilfestellung!");
        return true;
    }

    public boolean teleportCustomAdmin(CommandSender sender, String[] args) {
        if(!sender.hasPermission("home.admin"))
            return false;
        if(args.length != 3)
            return false;
        if(!args[0].equalsIgnoreCase("tp"))
            return false;
        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) {
            sender.sendMessage(ChatColor.RED + "Der eingegebene Spieler " + args[1] + " konnten nicht gefunden werden!");
            sender.sendMessage(ChatColor.YELLOW + "Ist der Spieler " + args[1] + " Online?");
            return true;
        }
        HomePoint homePoint = HomeManager.get(target);
        if(!homePoint.exist(args[2])) {
            sender.sendMessage(ChatColor.RED + "Das home " + args[2] + " von " + args[1] + " wurde noch nicht gesetzt!");
            return true;
        }

        homePoint.get(args[2]).teleport((Player) sender);
        sender.sendMessage(ChatColor.GREEN + "Du wurdest zum Home " + args[2] + " von " + args[1] + " teleportiert!");
        return true;
    }

    public boolean teleportCustom(CommandSender sender, String[] args) {
        if(args.length != 2)
            return false;
        if(!args[0].equalsIgnoreCase("tp"))
            return false;
        Player player = (Player) sender;
        if(player == null)
            return false;
        HomePoint homePoint = HomeManager.get(player);
        if(!homePoint.exist(args[1])) {
            sender.sendMessage(ChatColor.RED + "Dein home " + args[1] + " wurde noch nicht gesetzt!");
            return true;
        }

        homePoint.get(args[1]).teleport(player);
        player.sendMessage(ChatColor.GREEN + "Du wurdest nach Hause " + args[1] + " teleportiert!");
        return true;
    }

    public boolean teleport(CommandSender sender, String[] args) {
        if(args.length != 0)
            return false;
        Player player = (Player) sender;
        if(player == null)
            return false;
        HomePoint homePoint = HomeManager.get(player);
        if(!homePoint.exist("home")) {
            sender.sendMessage(ChatColor.RED + "Dein home wurde noch nicht gesetzt!");
            return true;
        }


        homePoint.get("home").teleport(player);
        player.sendMessage(ChatColor.GREEN + "Du wurdest nach Hause teleportiert!");
        return true;
    }

    public boolean setAdmin(CommandSender sender, String[] args) {
        if(!sender.hasPermission("home.admin"))
            return false;
        if(args.length != 3)
            return false;
        if(!args[0].equalsIgnoreCase("set"))
            return false;
        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) {
            sender.sendMessage(ChatColor.RED + "Der eingegebene Spieler " + args[1] + " konnten nicht gefunden werden!");
            sender.sendMessage(ChatColor.YELLOW + "Ist der Spieler " + args[1] + " Online?");
            return true;
        }


        if(HomeManager.get(target).getHomes().size() >= TeleportSystem.getConfiguration().getInt("home.max")) {
            if(!HomeManager.get(target).exist(args[2])) {
                sender.sendMessage(ChatColor.RED + "Die maximale anzahl an Homes von " + args[1] + " ist bereits erreicht!");
                return true;
            }
        }


        HomeManager.get(target).set(args[2], ((Player) sender).getLocation());
        sender.sendMessage(ChatColor.GREEN + "Das Home " + args[2] + " von " + args[1] +" wurde gesetzt!");
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

        String homeName;
        if(args.length == 1) {
            homeName = "home";
        } else {
            homeName = args[1];
        }

        if(HomeManager.get(player).getHomes().size() >= TeleportSystem.getConfiguration().getInt("home.max")) {
            if(!HomeManager.get(player).exist(homeName)) {
                sender.sendMessage(ChatColor.RED + "Deine maximale anzahl an Homes ist bereits erreicht!");
                return true;
            }
        }


        HomeManager.get(player).set(homeName, player.getLocation());
        player.sendMessage(ChatColor.GREEN + "Dein Home point wurde gesetzt!");
        return true;
    }

    public boolean removeAdmin(CommandSender sender, String[] args) {
        if(!sender.hasPermission("home.admin"))
            return false;
        if(args.length != 3)
            return false;
        if(!args[0].equalsIgnoreCase("remove"))
            return false;
        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) {
            sender.sendMessage(ChatColor.RED + "Der eingegebene Spieler " + args[1] + " konnten nicht gefunden werden!");
            sender.sendMessage(ChatColor.YELLOW + "Ist der Spieler " + args[1] + " Online?");
            return true;
        }

        if(!HomeManager.get(target).exist(args[2])) {
            sender.sendMessage(ChatColor.YELLOW + "Das Home " + args[2] + " von " + args[1] +" kann nicht gelöscht werden, da es nicht existiert!");
            return true;
        }

        HomeManager.get(target).remove(args[2]);
        sender.sendMessage(ChatColor.GREEN + "Das Home " + args[2] + " von " + args[1] + " wurde gelöscht!");
        return true;
    }

    public boolean remove(CommandSender sender, String[] args) {
        if(args.length != 2)
            return false;
        if(!args[0].equalsIgnoreCase("remove"))
            return false;
        if(!(sender instanceof Player))
            return false;
        Player player = (Player) sender;

        if(!HomeManager.get(player).exist(args[1])) {
            sender.sendMessage(ChatColor.YELLOW + "Das Home " + args[1] + " kann nicht gelöscht werden, da es nicht existiert!");
            return true;
        }

        HomeManager.get(player).remove(args[1]);
        sender.sendMessage(ChatColor.GREEN + "Das Home " + args[1] + " wurde gelöscht!");
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
        sender.sendMessage(commandColor + "/home remove <name>" + arrow + color + " Löscht ein Homepunkt mit dem Angegebenen Namen.");
        sender.sendMessage(commandColor + "/home tp <name>" + arrow + color + " Setzt ein Homepunkt mit dem Angegebenen Namen.");

        if(sender.hasPermission("home.admin")) {
            sender.sendMessage(commandColor + "/home tp <player> <name>" + arrow + color + " Setzt ein Homepunkt mit dem Angegebenen Namen.");
            sender.sendMessage(commandColor + "/home set <player> <name>" + arrow + color + " Setzt ein Homepunkt mit dem Angegebenen Namen.");
            sender.sendMessage(commandColor + "/home remove <player> <name>" + arrow + color + " Setzt ein Homepunkt mit dem Angegebenen Namen.");
        }

        return true;
    }

}
