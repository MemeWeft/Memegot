package me.memeweft.paper;

import me.memeweft.paper.config.Config;
import me.memeweft.paper.mongo.Mongo;
import me.memeweft.paper.world.World;

public class Memegot {

    private static Memegot instance;

    public static Memegot getInstance() {
        return instance;
    }

    public static void boot() {
        instance = new Memegot();

        Config.boot();
        Mongo.boot();
        World.boot();
    }

    public static void postBoot() {
        World.postBoot();
    }

    public static void shutdown() {
        Mongo.shutdown();
        World.shutdown();

        instance = null;
    }
}
