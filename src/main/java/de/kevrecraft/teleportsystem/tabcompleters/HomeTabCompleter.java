package de.kevrecraft.teleportsystem.tabcompleters;

import de.kevrecraft.teleportsystem.managers.HomeManager;
import org.bukkit.ChatColor;
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
            sender.sendMessage(ChatColor.RED + "Das kann nur ein Spieler tun!");
            return new ArrayList<>();
        }

        if(args.length == 1) {
            return getFirstList();
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("set")) {
                return getHomes((Player) sender);
            } else if(args[0].equalsIgnoreCase("tp")) {
                return getHomes((Player) sender);
            } else if(args[0].equalsIgnoreCase("remove")) {
                return getHomes((Player) sender);
            }
        }

        return new ArrayList<>();
    }

    public ArrayList<String> getHomes(Player player) {
        return new ArrayList<>(HomeManager.get(player).getHomes());
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
