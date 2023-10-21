package de.kevrecraft.teleportsystem;

import de.kevrecraft.teleportsystem.commands.HomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TeleportSystem extends JavaPlugin {

    private static TeleportSystem plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        this.getCommand("home").setExecutor(new HomeCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static File getDataFolde() {
        return plugin.getDataFolder();
    }

}
