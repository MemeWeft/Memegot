package me.memeweft.paper.world;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WorldHandler {

    static final File WORLDS_FILE = new File("game/worlds.yml");

    static List<String> loadRegistry() {
        ensureExists(WORLDS_FILE);
        return YamlConfiguration.loadConfiguration(WORLDS_FILE).getStringList("worlds");
    }

    static void loadAll(List<String> names) {
        for (String name : names) {
            Bukkit.createWorld(new WorldCreator(name));
        }
    }

    static void register(String name) {
        Bukkit.createWorld(new WorldCreator(name));
        List<String> updated = new ArrayList<>(World.getRegistry());
        updated.add(name);
        World.setRegistry(updated);
        save(updated);
    }

    static void unregister(org.bukkit.World world) {
        Path folder = world.getWorldFolder().toPath();
        Bukkit.unloadWorld(world, false);

        try {
            Files.walk(folder)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try { Files.delete(path); } catch (IOException ignored) {}
                });
        } catch (IOException e) {
            throw new RuntimeException("Could not delete world folder: " + folder, e);
        }

        List<String> updated = new ArrayList<>(World.getRegistry());
        updated.remove(world.getName());
        World.setRegistry(updated);
        save(updated);
    }

    private static void save(List<String> registry) {
        ensureExists(WORLDS_FILE);
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("worlds", registry);
        try {
            yaml.save(WORLDS_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Could not save worlds.yml", e);
        }
    }

    private static void ensureExists(File file) {
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Could not create " + file.getName(), e);
            }
        }
    }
}
