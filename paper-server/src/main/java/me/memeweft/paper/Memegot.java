package me.memeweft.paper;

import me.memeweft.paper.config.Config;
import me.memeweft.paper.mongo.Mongo;

public class Memegot {

    private static Memegot instance;

    public static Memegot getInstance() {
        return instance;
    }

    public static void boot() {
        instance = new Memegot();

        Config.boot();
        Mongo.boot();
    }

    public static void shutdown() {
        Mongo.shutdown();

        instance = null;
    }
}
