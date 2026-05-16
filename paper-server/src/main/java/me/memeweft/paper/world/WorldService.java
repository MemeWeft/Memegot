package me.memeweft.paper.world;

import org.bukkit.Bukkit;

import java.util.List;

public class WorldService {

    public static void createWorld(String name) {
        WorldHandler.register(name);
    }

    public static void deleteWorld(org.bukkit.World world) {
        WorldHandler.unregister(world);
    }

    public static List<org.bukkit.World> getWorlds() {
        return Bukkit.getWorlds();
    }
}
