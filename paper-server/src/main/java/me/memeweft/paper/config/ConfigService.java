package me.memeweft.paper.config;

import me.memeweft.paper.config.impl.GameConfig;

public class ConfigService {

    public static GameConfig getGameConfig() {
        return Config.getGameConfig();
    }
}
