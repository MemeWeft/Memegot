package me.memeweft.paper.config;

import me.memeweft.paper.meta.KnockbackProfile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class ProfilesConfig {

    private final Path file;
    private final Map<String, KnockbackProfile> profiles = new HashMap<>();

    public ProfilesConfig(Path configDir) {
        this.file = configDir.resolve("profiles.yml");
    }

    public void load() {
        ensureExists("/configurations/profiles.yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file.toFile());
        profiles.clear();

        for (String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key + ".values");
            if (section == null) continue;

            profiles.put(key, new KnockbackProfile(
                key,
                section.getDouble("horizontal",      0.425),
                section.getDouble("vertical",        0.4),
                section.getDouble("extra-horizontal", 0.375),
                section.getDouble("extra-vertical",  0.035),
                section.getDouble("vertical-limit",  0.4),
                section.getDouble("friction",        2.0),
                section.getBoolean("stop-sprinting", false)
            ));
        }
    }

    public KnockbackProfile get(String name) {
        return profiles.get(name);
    }

    public KnockbackProfile getDefault() {
        return profiles.getOrDefault("default", KnockbackProfile.defaults());
    }

    private void ensureExists(String resource) {
        if (Files.exists(file)) return;
        try {
            Files.createDirectories(file.getParent());
            InputStream in = ProfilesConfig.class.getResourceAsStream(resource);
            if (in != null) Files.copy(in, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
