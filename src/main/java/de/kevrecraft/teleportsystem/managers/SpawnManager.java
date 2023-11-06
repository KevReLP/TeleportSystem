package de.kevrecraft.teleportsystem.managers;

import de.kevrecraft.teleportsystem.ServerProperties;
import de.kevrecraft.teleportsystem.TeleportPoints.TeleportPoint;
import de.kevrecraft.teleportsystem.TeleportSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class SpawnManager implements Listener {

    private static File file;
    private static final YamlConfiguration config = new YamlConfiguration();
    private static TeleportSystem plugin;

    private static final String defaultSpawnName = "spawn";

    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent event) {
        getSpawn(event.getPlayer()).teleport(event.getPlayer(), plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setBedSpawnLocation(getSpawn(event.getPlayer()).getLocation(), true);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if(!event.getPlayer().hasPlayedBefore())
                    getSpawn(event.getPlayer()).teleport(event.getPlayer(), plugin);
            }
        });
    }

    public SpawnManager(TeleportSystem pl) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
            @Override
            public void run() {
                plugin = pl;
                file = new File(plugin.getDataFolder().getAbsolutePath(), "sapwns");
                if(file.exists()) {
                    try {
                        config.load(file);
                    } catch (IOException | InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(!config.contains(defaultSpawnName)) {
                    config.set(defaultSpawnName, Bukkit.getWorld(ServerProperties.get(ServerProperties.ServerPropertieType.worldname)).getSpawnLocation());
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

    public static TeleportPoint getSpawn(Player player) {
        for(String key : config.getKeys(false)) {
            if(player.hasPermission("spawn." + key)) {
                return new TeleportPoint(config.getLocation(key));
            }
        }
        return new TeleportPoint(config.getLocation(defaultSpawnName));
    }

    public static boolean exists(String name) {
        return config.contains(name);
    }

    public static TeleportPoint getSpawn(String name) {
        if(exists(name))
            return new TeleportPoint(config.getLocation(name));
        return null;
    }

    public static Set<String> getNames() {
        return config.getKeys(false);
    }

    public static void setSpawn(String name, Location location) {
        config.set(name, location);
    }

}
