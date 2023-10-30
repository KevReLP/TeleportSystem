package de.kevrecraft.teleportsystem.TeleportPoints;

import de.kevrecraft.teleportsystem.TeleportSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class TeleportPoint {


    private final Location location;

    public TeleportPoint(Location location) {
        this.location = location;
    }

    public void teleport(Entity entity, TeleportSystem plugin) {
        if(entity.getVehicle() != null) {
            Entity vehicle = entity.getVehicle();
            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    vehicle.eject();
                    vehicle.teleport(location);
                    vehicle.setPassenger(entity);
                }
            });
        } else {
            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    entity.teleport(location);
                }
            });
        }
    }

    public Location getLocation() {
        return this.location;
    }
}
