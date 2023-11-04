package de.kevrecraft.teleportsystem;

import de.kevrecraft.teleportsystem.commands.HomeCommand;
import de.kevrecraft.teleportsystem.commands.SpawnCommand;
import de.kevrecraft.teleportsystem.commands.WarpCommand;
import de.kevrecraft.teleportsystem.managers.HomeManager;
import de.kevrecraft.teleportsystem.managers.SpawnManager;
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
    private static SpawnManager spawnManager;
    private static FileConfiguration configuration;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        configuration = getConfig();
        homeManager = new HomeManager(this);
        getServer().getPluginManager().registerEvents(homeManager, this);
        warpManager = new WarpManager(this);
        spawnManager = new SpawnManager(this);
        getServer().getPluginManager().registerEvents(spawnManager, this);

        this.getCommand("home").setExecutor(new HomeCommand(this));
        this.getCommand("home").setTabCompleter(new HomeTabCompleter());

        this.getCommand("warp").setExecutor(new WarpCommand(this));
        this.getCommand("warp").setTabCompleter(new WarpsTabCompleter());

        this.getCommand("spawn").setExecutor(new SpawnCommand(this));
    }

    @Override
    public void onDisable() {
        homeManager.onDisable();
        warpManager.onDisable();
        spawnManager.onDisable();
    }

    public static File getDataFolde() {
        return plugin.getDataFolder();
    }

    public static FileConfiguration getConfiguration(){
        return configuration;
    }

}
