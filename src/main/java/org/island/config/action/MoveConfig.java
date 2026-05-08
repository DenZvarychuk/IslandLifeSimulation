package org.island.config.action;

public class MoveConfig {
    private double minEnergyRatio;
    private int findLocationRetries;


    public double getMinEnergyRatio() {
        return minEnergyRatio;
    }

    public void setMinEnergyRatio(double minEnergyRatio) {
        this.minEnergyRatio = minEnergyRatio;
    }

    public int getFindLocationRetries() {
        return findLocationRetries;
    }

    public void setFindLocationRetries(int findLocationRetries) {
        this.findLocationRetries = findLocationRetries;
    }
}