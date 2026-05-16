package me.memeweft.paper.config;

import me.memeweft.paper.config.impl.GameConfig;

public class ConfigService {

    public static GameConfig getGameConfig() {
        return Config.getGameConfig();
    }

    public static void setSpawn(org.bukkit.Location location) {
        ConfigHandler.setSpawn(location);
    }

    public static org.bukkit.Location getSpawn() {
        GameConfig config = Config.getGameConfig();
        if (config.spawnWorld == null || config.spawnWorld.isEmpty()) return null;
        org.bukkit.World world = org.bukkit.Bukkit.getWorld(config.spawnWorld);
        if (world == null) return null;
        return new org.bukkit.Location(world, config.spawnX, config.spawnY, config.spawnZ, config.spawnYaw, config.spawnPitch);
    }
}
