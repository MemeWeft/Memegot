package me.memeweft.paper.config.impl;

public class GameConfig {

    // Mob AI
    public boolean mobAi;

    // MongoDB
    public String mongoUri;

    // Spawn
    public String spawnWorld = "";
    public double spawnX;
    public double spawnY;
    public double spawnZ;
    public float spawnYaw;
    public float spawnPitch;
    
    // World
    public boolean voidWorld;
    public boolean disableNether;
    public boolean disableEnd;
    public int maxWorldRadius;

    // Performance
    public boolean disableWeather;
    public boolean disableFluidPhysics;
    public boolean disableLeafDecay;
    public boolean disableRedstone;
    public boolean disablePhantomSpawning;
    public boolean disableWanderingTrader;
    public boolean disableChatSigning;

    // Player data
    public boolean disablePlayerdata;
    public boolean disableStatistics;
    public boolean disableAdvancements;
}
