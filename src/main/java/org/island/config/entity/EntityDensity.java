package org.island.config.entity;

public enum EntityDensity {
    VERY_HIGH(0.8, 1.0),   // 80-100% of maxOnLocation
    HIGH(0.6, 0.8),        // 60-80%
    MEDIUM(0.4, 0.6),      // 40-60%
    LOW(0.2, 0.4),         // 20-40%
    VERY_LOW(0.05, 0.2),   // 5-20%
    NONE(0.0, 0.0);        // 0%

    private final double minMultiplier;
    private final double maxMultiplier;

    EntityDensity(double minMultiplier, double maxMultiplier) {
        this.minMultiplier = minMultiplier;
        this.maxMultiplier = maxMultiplier;
    }

    public double getMinMultiplier() {
        return minMultiplier;
    }

    public double getMaxMultiplier() {
        return maxMultiplier;
    }
}