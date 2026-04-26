package org.island.config;

public class AnimalConfig {
    private double weight;
    private int maxOnLocation;
    private int moveSteps;
    private double maxSatiety;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getMaxOnLocation() {
        return maxOnLocation;
    }

    public void setMaxOnLocation(int maxOnLocation) {
        this.maxOnLocation = maxOnLocation;
    }

    public int getMoveSteps() {
        return moveSteps;
    }

    public void setMoveSteps(int moveSteps) {
        this.moveSteps = moveSteps;
    }

    public double getMaxSatiety() {
        return maxSatiety;
    }

    public void setMaxSatiety(double maxSatiety) {
        this.maxSatiety = maxSatiety;
    }
}
