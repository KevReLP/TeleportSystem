package de.kevrecraft.teleportsystem;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class HomePoint {

    private final File file;
    private YamlConfiguration config;

    public HomePoint(Player player) {
        File folder = new File(TeleportSystem.getDataFolde().getAbsolutePath(), "HomePoints");
        if(!folder.exists()) {
            folder.mkdirs();
        }

        this.file = new File(folder.getAbsolutePath(), player.getUniqueId().toString());
        try {
            if(!this.file.exists()) {
                this.file.createNewFile();
            }
            this.config.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(String name, Location location) {
        this.config.set(name, location);
    }

    public Set<String> getHomes() {
        return this.config.getKeys(false);
    }

    public boolean exist(String name) {
        return this.config.getLocation(name) != null;
    }


    public TeleportPoint get(String name) {
        return new TeleportPoint(this.config.getLocation(name));
    }
}
