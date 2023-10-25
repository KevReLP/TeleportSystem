package de.kevrecraft.teleportsystem;

import de.kevrecraft.teleportsystem.commands.HomeCommand;
import de.kevrecraft.teleportsystem.managers.HomeManager;
import de.kevrecraft.teleportsystem.tabcompleters.HomeTabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TeleportSystem extends JavaPlugin {

    private static TeleportSystem plugin;
    private static HomeManager homeManager;
    private static FileConfiguration configuration;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        configuration = getConfig();
        homeManager = new HomeManager();
        getServer().getPluginManager().registerEvents(homeManager, this);

        this.getCommand("home").setExecutor(new HomeCommand());
        this.getCommand("home").setTabCompleter(new HomeTabCompleter());
    }

    @Override
    public void onDisable() {
        homeManager.onDisable();
    }

    public static File getDataFolde() {
        return plugin.getDataFolder();
    }

    public static FileConfiguration getConfiguration(){
        return configuration;
    }

}
