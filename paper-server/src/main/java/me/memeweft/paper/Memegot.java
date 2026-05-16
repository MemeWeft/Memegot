package me.memeweft.paper;

import me.memeweft.paper.config.GameConfig;

public class Memegot {

    private static Memegot instance;
    private static GameConfig gameConfig;

    public static Memegot getInstance() {
        return instance;
    }

    public static GameConfig getGameConfig() {
        return gameConfig;
    }

    public static void boot() {
        instance = new Memegot();
        gameConfig = new GameConfig();
    }

    public static void shutdown() {
        // subsystems afsluiten
        instance = null;
    }
}
