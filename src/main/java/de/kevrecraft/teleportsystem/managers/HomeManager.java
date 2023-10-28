package de.kevrecraft.teleportsystem.managers;

import de.kevrecraft.teleportsystem.TeleportPoints.HomePoint;
import de.kevrecraft.teleportsystem.TeleportSystem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class HomeManager implements Listener {

    private static final HashMap<Player, HomePoint> homes = new HashMap<>();
    private static TeleportSystem plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        load(event.getPlayer());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        save(event.getPlayer());
    }

    public HomeManager(TeleportSystem pl) {
        plugin = pl;
        for(Player player : Bukkit.getOnlinePlayers()) {
            load(player);
        }
    }

    public void onDisable() {
        for(Player player : homes.keySet()) {
            save(player);
        }
    }

    private static void load(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                homes.put(player, new HomePoint(player));
            }
        });
    }

    private static void save(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                homes.get(player).save();
                homes.remove(player);
            }
        });
    }

    public static HomePoint get(Player player) {
        return homes.get(player);
    }

    public static HomePoint get(OfflinePlayer player) {
        return new HomePoint(player);
    }

}
