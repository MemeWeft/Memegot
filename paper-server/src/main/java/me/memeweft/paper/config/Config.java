package me.memeweft.paper.config;

import me.memeweft.paper.config.impl.GameConfig;

public class Config {

    private static GameConfig gameConfig;

    public static void boot() {
        gameConfig = ConfigHandler.loadGameConfig();
    }

    public static void reload() {
        gameConfig = ConfigHandler.loadGameConfig();
    }

    static GameConfig getGameConfig() {
        return gameConfig;
    }
}
