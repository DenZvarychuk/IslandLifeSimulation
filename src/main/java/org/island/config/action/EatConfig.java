package org.island.config.action;

public class EatConfig {
    private double minEnergyRatio;
    private double randomFoodSelectionSatietyMultiplier;

    public double getMinEnergyRatio() {
        return minEnergyRatio;
    }

    public void setMinEnergyRatio(double minEnergyRatio) {
        this.minEnergyRatio = minEnergyRatio;
    }

    public double getRandomFoodSelectionSatietyMultiplier() {
        return randomFoodSelectionSatietyMultiplier;
    }

    public void setRandomFoodSelectionSatietyMultiplier(double randomFoodSelectionSatietyMultiplier) {
        this.randomFoodSelectionSatietyMultiplier = randomFoodSelectionSatietyMultiplier;
    }
}
