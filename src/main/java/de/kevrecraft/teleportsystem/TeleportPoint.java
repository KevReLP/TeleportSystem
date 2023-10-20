package de.kevrecraft.teleportsystem;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class TeleportPoint {


    private final Location location;

    public TeleportPoint(Location location) {
        this.location = location;
    }

    public void teleport(Entity entity) {
        if(!entity.getVehicle().isEmpty()) {
            entity.getVehicle().teleport(this.location);
        } else {
            entity.teleport(this.location);
        }
    }

    public Location getLocation() {
        return this.location;
    }
}
