package org.island.config;

public class ActionConfig {
    private double baseRestMinEnergyRatio;
    private double idleRestMinEnergyRatio;
    private double sleepRestMinEnergyRatio;
    private int sleepRestCycleCount;
    private double idleRestChance;
    private double idleRestEnergyBoost;
    private double movementMinEnergyRatio;
    private int landMoveFindLocationRetries;
    private double eatMinEnergyRatio;

    public double getIdleRestMinEnergyRatio() {
        return idleRestMinEnergyRatio;
    }

    public void setIdleRestMinEnergyRatio(double idleRestMinEnergyRatio) {
        this.idleRestMinEnergyRatio = idleRestMinEnergyRatio;
    }

    public double getSleepRestMinEnergyRatio() {
        return sleepRestMinEnergyRatio;
    }

    public void setSleepRestMinEnergyRatio(double sleepRestMinEnergyRatio) {
        this.sleepRestMinEnergyRatio = sleepRestMinEnergyRatio;
    }

    public double getMovementMinEnergyRatio() {
        return movementMinEnergyRatio;
    }

    public void setMovementMinEnergyRatio(double movementMinEnergyRatio) {
        this.movementMinEnergyRatio = movementMinEnergyRatio;
    }

    public double getBaseRestMinEnergyRatio() {
        return baseRestMinEnergyRatio;
    }

    public void setBaseRestMinEnergyRatio(double baseRestMinEnergyRatio) {
        this.baseRestMinEnergyRatio = baseRestMinEnergyRatio;
    }

    public double getIdleRestChance() {
        return idleRestChance;
    }

    public void setIdleRestChance(double idleRestChance) {
        this.idleRestChance = idleRestChance;
    }

    public double getIdleRestEnergyBoost() {
        return idleRestEnergyBoost;
    }

    public void setIdleRestEnergyBoost(double idleRestEnergyBoost) {
        this.idleRestEnergyBoost = idleRestEnergyBoost;
    }

    public double getEatMinEnergyRatio() {
        return eatMinEnergyRatio;
    }

    public void setEatMinEnergyRatio(double eatMinEnergyRatio) {
        this.eatMinEnergyRatio = eatMinEnergyRatio;
    }

    public int getSleepRestCycleCount() {
        return sleepRestCycleCount;
    }

    public void setSleepRestCycleCount(int sleepRestCycleCount) {
        this.sleepRestCycleCount = sleepRestCycleCount;
    }

    public int getLandMoveFindLocationRetries() {
        return landMoveFindLocationRetries;
    }

    public void setLandMoveFindLocationRetries(int landMoveFindLocationRetries) {
        this.landMoveFindLocationRetries = landMoveFindLocationRetries;
    }
}