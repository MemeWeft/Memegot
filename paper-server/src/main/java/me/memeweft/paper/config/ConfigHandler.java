package me.memeweft.paper.config;

import me.memeweft.paper.config.impl.GameConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    static final File GAME_CONFIG_FILE = new File("game/game.yml");

    static GameConfig loadGameConfig() {
        ensureExists(GAME_CONFIG_FILE);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(GAME_CONFIG_FILE);
        yaml.options().copyDefaults(true);
        GameConfig config = parse(yaml);
        save(yaml, GAME_CONFIG_FILE);
        return config;
    }

    private static void ensureExists(File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Could not create " + file.getName(), e);
            }
        }
    }

    private static GameConfig parse(YamlConfiguration yaml) {
        GameConfig config = new GameConfig();

        yaml.addDefault("memegot.mob-ai", true);
        config.mobAi = yaml.getBoolean("memegot.mob-ai");

        yaml.addDefault("memegot.mongo.uri", "mongodb://192.168.2.18:27017");
        config.mongoUri = yaml.getString("memegot.mongo.uri");

        yaml.addDefault("memegot.world.void", true);
        config.voidWorld = yaml.getBoolean("memegot.world.void");
        yaml.addDefault("memegot.world.disable-nether", true);
        config.disableNether = yaml.getBoolean("memegot.world.disable-nether");
        yaml.addDefault("memegot.world.disable-end", true);
        config.disableEnd = yaml.getBoolean("memegot.world.disable-end");
        yaml.addDefault("memegot.world.max-radius", 5000);
        config.maxWorldRadius = yaml.getInt("memegot.world.max-radius");

        yaml.addDefault("memegot.performance.disable-weather", true);
        config.disableWeather = yaml.getBoolean("memegot.performance.disable-weather");
        yaml.addDefault("memegot.performance.disable-fluid-physics", true);
        config.disableFluidPhysics = yaml.getBoolean("memegot.performance.disable-fluid-physics");
        yaml.addDefault("memegot.performance.disable-leaf-decay", true);
        config.disableLeafDecay = yaml.getBoolean("memegot.performance.disable-leaf-decay");
        yaml.addDefault("memegot.performance.disable-redstone", true);
        config.disableRedstone = yaml.getBoolean("memegot.performance.disable-redstone");
        yaml.addDefault("memegot.performance.disable-phantom-spawning", true);
        config.disablePhantomSpawning = yaml.getBoolean("memegot.performance.disable-phantom-spawning");
        yaml.addDefault("memegot.performance.disable-wandering-trader", true);
        config.disableWanderingTrader = yaml.getBoolean("memegot.performance.disable-wandering-trader");
        yaml.addDefault("memegot.performance.disable-chat-signing", true);
        config.disableChatSigning = yaml.getBoolean("memegot.performance.disable-chat-signing");

        yaml.addDefault("memegot.player-data.disable-playerdata", true);
        config.disablePlayerdata = yaml.getBoolean("memegot.player-data.disable-playerdata");
        yaml.addDefault("memegot.player-data.disable-statistics", true);
        config.disableStatistics = yaml.getBoolean("memegot.player-data.disable-statistics");
        yaml.addDefault("memegot.player-data.disable-advancements", true);
        config.disableAdvancements = yaml.getBoolean("memegot.player-data.disable-advancements");

        return config;
    }

    private static void save(YamlConfiguration yaml, File file) {
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Could not save " + file.getName(), e);
        }
    }
}
