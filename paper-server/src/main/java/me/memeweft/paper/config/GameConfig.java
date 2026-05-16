package me.memeweft.paper.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GameConfig {

    private final File configFile;
    private YamlConfiguration config;

    // Mob AI
    public boolean mobAi;

    // MongoDB
    public String mongoUri;
    public String mongoDatabase;

    // World
    public boolean voidWorld;
    public boolean disableNether;
    public boolean disableEnd;
    public int maxWorldRadius;

    // Performance
    public boolean disableWeather;
    public boolean disableFluidPhysics;
    public boolean disableLeafDecay;
    public boolean disableRedstone;
    public boolean disablePhantomSpawning;
    public boolean disableWanderingTrader;
    public boolean disableChatSigning;

    // Player data
    public boolean disablePlayerdata;
    public boolean disableStatistics;
    public boolean disableAdvancements;

    public GameConfig() {
        this.configFile = new File("game/game.yml");
        load();
    }

    public void load() {
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdirs();
        }

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Could not create game.yml", e);
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        config.options().copyDefaults(true);
        parse();

        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not save game.yml", e);
        }
    }

    private void parse() {
        config.addDefault("memegot.mob-ai", true);
        mobAi = config.getBoolean("memegot.mob-ai");

        config.addDefault("memegot.mongo.uri", "mongodb://localhost:27017");
        mongoUri = config.getString("memegot.mongo.uri");
        config.addDefault("memegot.mongo.database", "memegot");
        mongoDatabase = config.getString("memegot.mongo.database");

        config.addDefault("memegot.world.void", true);
        voidWorld = config.getBoolean("memegot.world.void");
        config.addDefault("memegot.world.disable-nether", true);
        disableNether = config.getBoolean("memegot.world.disable-nether");
        config.addDefault("memegot.world.disable-end", true);
        disableEnd = config.getBoolean("memegot.world.disable-end");
        config.addDefault("memegot.world.max-radius", 5000);
        maxWorldRadius = config.getInt("memegot.world.max-radius");

        config.addDefault("memegot.performance.disable-weather", true);
        disableWeather = config.getBoolean("memegot.performance.disable-weather");
        config.addDefault("memegot.performance.disable-fluid-physics", true);
        disableFluidPhysics = config.getBoolean("memegot.performance.disable-fluid-physics");
        config.addDefault("memegot.performance.disable-leaf-decay", true);
        disableLeafDecay = config.getBoolean("memegot.performance.disable-leaf-decay");
        config.addDefault("memegot.performance.disable-redstone", true);
        disableRedstone = config.getBoolean("memegot.performance.disable-redstone");
        config.addDefault("memegot.performance.disable-phantom-spawning", true);
        disablePhantomSpawning = config.getBoolean("memegot.performance.disable-phantom-spawning");
        config.addDefault("memegot.performance.disable-wandering-trader", true);
        disableWanderingTrader = config.getBoolean("memegot.performance.disable-wandering-trader");
        config.addDefault("memegot.performance.disable-chat-signing", true);
        disableChatSigning = config.getBoolean("memegot.performance.disable-chat-signing");

        config.addDefault("memegot.player-data.disable-playerdata", true);
        disablePlayerdata = config.getBoolean("memegot.player-data.disable-playerdata");
        config.addDefault("memegot.player-data.disable-statistics", true);
        disableStatistics = config.getBoolean("memegot.player-data.disable-statistics");
        config.addDefault("memegot.player-data.disable-advancements", true);
        disableAdvancements = config.getBoolean("memegot.player-data.disable-advancements");
    }

    public void reload() {
        load();
    }
}
