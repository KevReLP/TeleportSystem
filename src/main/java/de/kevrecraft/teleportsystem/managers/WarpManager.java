package de.kevrecraft.teleportsystem.managers;

import de.kevrecraft.teleportsystem.TeleportPoints.TeleportPoint;
import de.kevrecraft.teleportsystem.TeleportSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class WarpManager {

    private static File file;
    private static YamlConfiguration config = new YamlConfiguration();
    private static TeleportSystem plugin;

    public WarpManager(TeleportSystem pl) {
        Bukkit.getScheduler().runTask(pl, new Runnable() {
            @Override
            public void run() {
                plugin = pl;
                file = new File(plugin.getDataFolder(), "warps");
                if(file.exists()) {
                    try {
                        config.load(file);
                    } catch (IOException | InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void onDisable() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean exits(String name) {
        return config.contains(name);
    }

    public static TeleportPoint getTeleportPoint(String name) {
        if (exits(name))
            return new TeleportPoint(config.getLocation(name));
        return null;
    }

    public static Location getLocation(String name) {
        return config.getLocation(name);
    }

    public static void set(String name, Location location) {
        config.set(name, location);
    }

    public static void teleport(String name, Entity entity) {
        if(exits(name))
            getTeleportPoint(name).teleport(entity, plugin);
    }

    public static Set<String> getWarpsList() {
        return config.getKeys(false);
    }

}
