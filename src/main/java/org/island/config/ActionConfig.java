package org.island.config;

public class ActionConfig {
    private double idleRestMinEnergyRatio;
    private double sleepRestMinEnergyRatio;
    private double movementMinEnergyRatio;

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
}
