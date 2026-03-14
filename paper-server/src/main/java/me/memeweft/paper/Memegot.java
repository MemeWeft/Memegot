package me.memeweft.paper;

import me.memeweft.paper.config.GameConfig;
import me.memeweft.paper.config.ProfilesConfig;
import me.memeweft.paper.module.potions.PotionEngine;

import java.nio.file.Path;

public final class Memegot {

    private Memegot() {}

    private static boolean initialized;
    private static GameConfig gameConfig;
    private static ProfilesConfig profilesConfig;
    private static PotionEngine potionEngine;

    public static void boot(Path gameFolder) {
        if (initialized) return;
        initialized = true;

        Path configDir = gameFolder.resolve("game");

        gameConfig = new GameConfig(configDir);
        gameConfig.load();

        profilesConfig = new ProfilesConfig(configDir);
        profilesConfig.load();

        potionEngine = new PotionEngine();
    }

    public static void shutdown() {
        if (!initialized) return;
        initialized = false;
        gameConfig = null;
        profilesConfig = null;
        potionEngine = null;
    }

    public static GameConfig gameConfig() {
        return gameConfig;
    }

    public static ProfilesConfig profilesConfig() {
        return profilesConfig;
    }

    public static PotionEngine potionEngine() {
        return potionEngine;
    }

    public static boolean ready() {
        return initialized;
    }
}
