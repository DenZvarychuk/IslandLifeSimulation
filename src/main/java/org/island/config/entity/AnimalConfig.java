package org.island.config.entity;

public class AnimalConfig {
    private double weight;
    private int maxOnLocation;
    private int moveSteps;
    private double maxSatiety;
    private Double baseEnergy;
    private Double actionEnergyCostRatio;
    private Double actionSatietyCostRatio;
    private Double minSatietyRatio;
    private DietConfig diet;

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

    public DietConfig getDiet() {
        return diet;
    }

    public void setDiet(DietConfig diet) {
        this.diet = diet;
    }

    public Double getBaseEnergy() {
        return baseEnergy;
    }

    public void setBaseEnergy(Double baseEnergy) {
        this.baseEnergy = baseEnergy;
    }

    public Double getActionEnergyCostRatio() {
        return actionEnergyCostRatio;
    }

    public void setActionEnergyCostRatio(Double actionEnergyCostRatio) {
        this.actionEnergyCostRatio = actionEnergyCostRatio;
    }

    public Double getMinSatietyRatio() {
        return minSatietyRatio;
    }

    public void setMinSatietyRatio(Double minSatietyRatio) {
        this.minSatietyRatio = minSatietyRatio;
    }

    public Double getActionSatietyCostRatio() {
        return actionSatietyCostRatio;
    }

    public void setActionSatietyCostRatio(Double actionSatietyCostRatio) {
        this.actionSatietyCostRatio = actionSatietyCostRatio;
    }
}