package me.memeweft.paper.world;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WorldService {

    public static CompletableFuture<World> createWorld(String name) {
        return WorldHandler.register(name);
    }

    public static void deleteWorld(org.bukkit.World world) {
        WorldHandler.unregister(world);
    }

    public static List<org.bukkit.World> getWorlds() {
        return Bukkit.getWorlds();
    }
}
