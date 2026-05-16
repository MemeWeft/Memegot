package me.memeweft.paper.world;

import me.memeweft.paper.config.ConfigService;
import me.memeweft.paper.config.impl.GameConfig;
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
import java.util.concurrent.CompletableFuture;

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

    static CompletableFuture<org.bukkit.World> register(String name) {
        CompletableFuture<org.bukkit.World> future = new CompletableFuture<>();

        net.minecraft.server.MinecraftServer.getServer().execute(() -> {
            org.bukkit.World world = Bukkit.createWorld(new WorldCreator(name));

            if (world == null) {
                future.completeExceptionally(new RuntimeException("World creation returned null"));
                return;
            }

            List<String> updated = new ArrayList<>(World.getRegistry());
            updated.add(name);
            World.setRegistry(updated);
            save(updated);

            future.complete(world);
        });

        return future;
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

    static void loadSpawn() {
        GameConfig config = ConfigService.getGameConfig();
        if (config.spawnWorld == null || config.spawnWorld.isEmpty()) return;

        org.bukkit.World world = Bukkit.getWorld(config.spawnWorld);
        if (world == null) return;

        int chunkX = ((int) config.spawnX) >> 4;
        int chunkZ = ((int) config.spawnZ) >> 4;

        for (int x = chunkX - 1; x <= chunkX + 1; x++) {
            for (int z = chunkZ - 1; z <= chunkZ + 1; z++) {
                world.setChunkForceLoaded(x, z, true);
            }
        }
    }
}
