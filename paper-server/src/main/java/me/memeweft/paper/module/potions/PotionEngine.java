package me.memeweft.paper.module.potions;

import me.memeweft.paper.enums.CombatMode;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PotionEngine {

    private final Map<UUID, CombatMode> modes = new ConcurrentHashMap<>();

    public void set(UUID uuid, CombatMode mode) {
        modes.put(uuid, mode);
    }

    public CombatMode get(UUID uuid) {
        return modes.getOrDefault(uuid, CombatMode.MODERN);
    }

    public boolean isLegacy(UUID uuid) {
        return get(uuid) == CombatMode.LEGACY;
    }

    public void remove(UUID uuid) {
        modes.remove(uuid);
    }

    public void clear() {
        modes.clear();
    }
}
