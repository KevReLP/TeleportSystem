package de.kevrecraft.teleportsystem;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class TeleportPoint {


    private final Location location;

    public TeleportPoint(Location location) {
        this.location = location;
    }

    public void teleport(Entity entity) {
        if(entity.getVehicle() != null) {
            Entity vehicle = entity.getVehicle();
            vehicle.eject();
            vehicle.teleport(this.location);
            vehicle.setPassenger(entity);
        } else {
            entity.teleport(this.location);
        }
    }

    public Location getLocation() {
        return this.location;
    }
}
