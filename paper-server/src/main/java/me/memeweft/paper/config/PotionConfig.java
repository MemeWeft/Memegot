package me.memeweft.paper.config;

public final class PotionConfig {

    private final double throwSpeed;
    private final float verticalOffset;
    private final boolean inheritPlayerVelocity;
    private final double gravity;
    private final double drag;

    private PotionConfig(Builder b) {
        this.throwSpeed            = b.throwSpeed;
        this.verticalOffset        = b.verticalOffset;
        this.inheritPlayerVelocity = b.inheritPlayerVelocity;
        this.gravity               = b.gravity;
        this.drag                  = b.drag;
    }

    public double  throwSpeed()             { return throwSpeed; }
    public float   verticalOffset()         { return verticalOffset; }
    public boolean inheritPlayerVelocity()  { return inheritPlayerVelocity; }
    public double  gravity()                { return gravity; }
    public double  drag()                   { return drag; }

    public static PotionConfig legacy17() {
        return new Builder().build();
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private double  throwSpeed            = 0.5;
        private float   verticalOffset        = -20.0f;
        private boolean inheritPlayerVelocity = true;
        private double  gravity               = 0.03;
        private double  drag                  = 0.99;

        public Builder throwSpeed(double v)             { throwSpeed = v;             return this; }
        public Builder verticalOffset(float v)          { verticalOffset = v;         return this; }
        public Builder inheritPlayerVelocity(boolean v) { inheritPlayerVelocity = v;  return this; }
        public Builder gravity(double v)                { gravity = v;                return this; }
        public Builder drag(double v)                   { drag = v;                   return this; }

        public PotionConfig build() { return new PotionConfig(this); }
    }
}
