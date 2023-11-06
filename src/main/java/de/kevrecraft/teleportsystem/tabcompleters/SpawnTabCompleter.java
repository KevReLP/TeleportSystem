package de.kevrecraft.teleportsystem.tabcompleters;

import de.kevrecraft.teleportsystem.commands.SpawnCommand;
import de.kevrecraft.teleportsystem.managers.SpawnManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SpawnTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1) {
            ArrayList<String> list = new ArrayList<>();
            list.add("help");
            if(sender.hasPermission(SpawnCommand.adminPermission)) {
                list.add("tp");
                list.add("set");
                list.add("remove");
            }
            return list;
        }

        if(sender.hasPermission(SpawnCommand.adminPermission)) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("tp")) {
                    return getSpawnNames();
                }
                if(args[0].equalsIgnoreCase("set")) {
                    return getSpawnNames();
                }
                if(args[0].equalsIgnoreCase("remove")) {
                    return getSpawnNames();
                }
            }
        }

        return new ArrayList<>();
    }

    private ArrayList<String> getSpawnNames() {
        ArrayList<String> list = new ArrayList<>();
        for(String key : SpawnManager.getNames()) {
            list.add(key);
        }
        return list;
    }


}
