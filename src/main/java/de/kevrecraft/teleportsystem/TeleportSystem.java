package de.kevrecraft.teleportsystem;

import de.kevrecraft.teleportsystem.commands.HomeCommand;
import de.kevrecraft.teleportsystem.commands.WarpCommand;
import de.kevrecraft.teleportsystem.managers.HomeManager;
import de.kevrecraft.teleportsystem.managers.WarpManager;
import de.kevrecraft.teleportsystem.tabcompleters.HomeTabCompleter;
import de.kevrecraft.teleportsystem.tabcompleters.WarpsTabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TeleportSystem extends JavaPlugin {

    private static TeleportSystem plugin;
    private static HomeManager homeManager;
    private static WarpManager warpManager;
    private static FileConfiguration configuration;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        configuration = getConfig();
        homeManager = new HomeManager(this);
        warpManager = new WarpManager(this);
        getServer().getPluginManager().registerEvents(homeManager, this);

        this.getCommand("home").setExecutor(new HomeCommand(this));
        this.getCommand("home").setTabCompleter(new HomeTabCompleter());

        this.getCommand("warp").setExecutor(new WarpCommand(this));
        this.getCommand("warp").setTabCompleter(new WarpsTabCompleter());
    }

    @Override
    public void onDisable() {
        homeManager.onDisable();
        warpManager.onDisable();
    }

    public static File getDataFolde() {
        return plugin.getDataFolder();
    }

    public static FileConfiguration getConfiguration(){
        return configuration;
    }

}
