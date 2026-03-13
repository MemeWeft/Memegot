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

    public GameConfig(Path configDir) {
        this.file = configDir.resolve("game.yml");
    }

    public void load() {
        ensureExists("/configurations/game.yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file.toFile());
        globalMode = CombatMode.valueOf(config.getString("global-mode", "MODERN").toUpperCase());
    }

    public CombatMode getGlobalMode() {
        return globalMode;
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
