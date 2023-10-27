package de.kevrecraft.teleportsystem.tabcompleters;

import de.kevrecraft.teleportsystem.TeleportPoints.HomePoint;
import de.kevrecraft.teleportsystem.managers.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        if(args.length == 1) {
            if(sender.hasPermission("home.admin"))
                return getFirstListAdmin();
            else
                return getFirstList();
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("set")) {
                return getHomes((Player) sender);
            } else if(args[0].equalsIgnoreCase("tp")) {
                return getHomes((Player) sender);
            } else if(args[0].equalsIgnoreCase("remove")) {
                return getHomes((Player) sender);
            } else if(args[0].equalsIgnoreCase("set_other")) {
                return getOflinePlayers();
            } else if(args[0].equalsIgnoreCase("tp_other")) {
                return getOflinePlayers();
            } else if(args[0].equalsIgnoreCase("remove_other")) {
                return getOflinePlayers();
            }
        } else if(args.length == 3) {
            if(args[0].equalsIgnoreCase("set_other")) {
                return getHomes(Bukkit.getOfflinePlayer(args[1]));
            } else if(args[0].equalsIgnoreCase("tp_other")) {
                return getHomes(Bukkit.getOfflinePlayer(args[1]));
            } else if(args[0].equalsIgnoreCase("remove_other")) {
                return getHomes(Bukkit.getOfflinePlayer(args[1]));
            }
        }

        return new ArrayList<>();
    }

    public ArrayList<String> getOflinePlayers() {
        ArrayList<String> list = new ArrayList<>();
        for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            list.add(player.getName());
        }
        return list;
    }

    public ArrayList<String> getHomes(Player player) {
        return new ArrayList<>(HomeManager.get(player).getHomes());
    }

    public ArrayList<String> getHomes(OfflinePlayer target) {
        if(target.hasPlayedBefore()) {
            HomePoint homePoint = HomeManager.get(target);
            return new ArrayList<>(homePoint.getHomes());
        }
        return new ArrayList<>();
    }

    public ArrayList<String> getFirstListAdmin() {
        ArrayList<String> list = getFirstList();
        list.add("set_other");
        list.add("remove_other");
        list.add("tp_other");
        return list;
    }

    public ArrayList<String> getFirstList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("help");
        list.add("set");
        list.add("remove");
        list.add("tp");
        return list;
    }
}
