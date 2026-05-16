package me.memeweft.paper.world;

import java.util.List;

public class World {

    private static List<String> registry;

    public static void boot() {
        registry = WorldHandler.loadRegistry();
    }

    public static void postBoot() {
        WorldHandler.loadAll(registry);
    }

    public static void shutdown() {
        registry = null;
    }

    static List<String> getRegistry() {
        return registry;
    }

    static void setRegistry(List<String> worlds) {
        registry = worlds;
    }
}
