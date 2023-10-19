package de.kevrecraft.teleportsystem;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TeleportSystem extends JavaPlugin {

    private static TeleportSystem plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static File getDataFolde() {
        return plugin.getDataFolder();
    }

}
