package me.memeweft.paper.config;

import me.memeweft.paper.Memegot;
import me.memeweft.paper.enums.CombatMode;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GameConfig {

    private final Path file;
    private CombatMode globalMode = CombatMode.MODERN;
    private boolean mobAi = false;
    private boolean playerStats = false;

    public GameConfig(Path configDir) {
        this.file = configDir.resolve("game.yml");
    }

    public void load() {
        ensureExists("/configurations/game.yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file.toFile());
        globalMode = CombatMode.valueOf(config.getString("global-mode", "MODERN").toUpperCase());
        mobAi = config.getBoolean("mob-ai", false);
        playerStats = config.getBoolean("player-stats", false);
    }

    public CombatMode getGlobalMode() {
        return globalMode;
    }

    public boolean isMobAi() {
        return mobAi;
    }

    public boolean isPlayerStats() {
        return playerStats;
    }

    private void ensureExists(String resource) {
        if (Files.exists(file)) return;
        try {
            Files.createDirectories(file.getParent());
            InputStream in = Memegot.class.getResourceAsStream(resource);
            if (in != null) Files.copy(in, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
