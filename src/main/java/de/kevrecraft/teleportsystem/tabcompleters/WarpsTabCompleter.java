package de.kevrecraft.teleportsystem.tabcompleters;

import de.kevrecraft.teleportsystem.commands.WarpCommand;
import de.kevrecraft.teleportsystem.managers.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpsTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        if(args.length == 1) {
            if(sender.hasPermission(WarpCommand.adminPermission)) {
                ArrayList<String> list = getWarps();
                list.add("set");
                list.add("remove");
                return list;
            }
            return getWarps();
        }

        if(args.length == 2) {
            if(sender.hasPermission(WarpCommand.adminPermission)) {
                if (args[0].equalsIgnoreCase("set"))
                    return getWarps();
                if (args[0].equalsIgnoreCase("remove"))
                    return getWarps();
            }

        }

        return new ArrayList<>();
    }

    private ArrayList<String> getWarps() {
        ArrayList<String> list = new ArrayList<>();
        for(String name : WarpManager.getWarpsList()) {
            list.add(name);
        }
        return list;
    }
}
