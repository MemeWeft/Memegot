package me.memeweft.paper.module.potions;

import me.memeweft.paper.config.PotionConfig;

public final class PotionPhysics {

    private PotionPhysics() {}

    public static double[] computeInitialVelocity(
        float yaw, float pitch,
        double playerVX, double playerVZ,
        PotionConfig cfg
    ) {
        float adjustedPitch = pitch + cfg.verticalOffset();

        double yawRad   = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(adjustedPitch);

        double dx =  -Math.sin(yawRad) * Math.cos(pitchRad);
        double dy =  -Math.sin(pitchRad);
        double dz =   Math.cos(yawRad) * Math.cos(pitchRad);

        double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (len > 1e-6) {
            dx = (dx / len) * cfg.throwSpeed();
            dy = (dy / len) * cfg.throwSpeed();
            dz = (dz / len) * cfg.throwSpeed();
        }

        if (cfg.inheritPlayerVelocity()) {
            dx += playerVX;
            dz += playerVZ;
        }

        return new double[]{dx, dy, dz};
    }

    public static void tickVelocity(double[] vel, PotionConfig cfg) {
        vel[0] *= cfg.drag();
        vel[1] *= cfg.drag();
        vel[2] *= cfg.drag();
        vel[1] -= cfg.gravity();
    }

    public static double[] computeSpawnPosition(
        double playerX, double playerY, double playerZ,
        double eyeHeight
    ) {
        return new double[]{
            playerX,
            playerY + eyeHeight - 0.1,
            playerZ
        };
    }

    public static double[][] simulateArc(
        double spawnX, double spawnY, double spawnZ,
        double[] vel, int maxTicks,
        PotionConfig cfg
    ) {
        double[] v = vel.clone();
        double x = spawnX, y = spawnY, z = spawnZ;

        double[][] positions = new double[maxTicks][3];
        for (int t = 0; t < maxTicks; t++) {
            tickVelocity(v, cfg);
            x += v[0];
            y += v[1];
            z += v[2];
            positions[t][0] = x;
            positions[t][1] = y;
            positions[t][2] = z;
        }
        return positions;
    }
}
