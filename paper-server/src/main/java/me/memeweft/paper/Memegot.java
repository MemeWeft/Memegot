package me.memeweft.paper;

import me.memeweft.paper.config.Config;

public class Memegot {

    private static Memegot instance;

    public static Memegot getInstance() {
        return instance;
    }

    public static void boot() {
        instance = new Memegot();
        Config.boot();
    }

    public static void shutdown() {
        instance = null;
    }
}
