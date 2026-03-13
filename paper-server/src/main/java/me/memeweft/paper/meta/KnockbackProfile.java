package me.memeweft.paper.meta;

public record KnockbackProfile(
    String name,
    double horizontal,
    double vertical,
    double extraHorizontal,
    double extraVertical,
    double verticalLimit,
    double friction,
    boolean stopSprinting
) {
    public static KnockbackProfile defaults() {
        return new KnockbackProfile("default", 0.425, 0.4, 0.375, 0.035, 0.4, 2.0, false);
    }
}
