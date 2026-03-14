package me.memeweft.paper.module.potions;

import me.memeweft.paper.config.PotionConfig;

public final class PotionCalculation {

    private PotionCalculation() {}

    public static double[] computeVelocity(
        float yaw, float pitch,
        double playerVX, double playerVZ,
        PotionConfig cfg
    ) {
        float adjustedPitch = pitch + cfg.verticalOffset();

        double yawRad   = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(adjustedPitch);

        double dx = -Math.sin(yawRad) * Math.cos(pitchRad);
        double dy = -Math.sin(pitchRad);
        double dz =  Math.cos(yawRad) * Math.cos(pitchRad);

        double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (len > 1e-6) {
            double speed = cfg.throwSpeed() / len;
            dx *= speed;
            dy *= speed;
            dz *= speed;
        }

        if (cfg.inheritPlayerVelocity()) {
            dx += playerVX;
            dz += playerVZ;
        }

        return new double[]{dx, dy, dz};
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
}
