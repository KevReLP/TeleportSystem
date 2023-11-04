package de.kevrecraft.teleportsystem;

import org.bukkit.Bukkit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerProperties {

    public enum ServerPropertieType {
        worldname("level-name");

        String key;
        ServerPropertieType(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static String get(ServerPropertieType type) {
        String path = Bukkit.getWorldContainer().toPath().resolve("server.properties").toString();
        try (InputStream input = new FileInputStream(path)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty(type.getKey());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
