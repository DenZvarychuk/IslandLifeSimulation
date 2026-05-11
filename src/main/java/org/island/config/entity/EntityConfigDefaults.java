package org.island.config.entity;

// !!!! remember to add default initialization into Factory !!!

public class EntityConfigDefaults {
    private double baseEnergy;
    private double actionEnergyCostRatio;
    private double actionSatietyCostRatio;
    private double minSatietyRatio;

    public double getBaseEnergy() {
        return baseEnergy;
    }

    public void setBaseEnergy(double baseEnergy) {
        this.baseEnergy = baseEnergy;
    }

    public double getMinSatietyRatio() {
        return minSatietyRatio;
    }

    public void setMinSatietyRatio(double minSatietyRatio) {
        this.minSatietyRatio = minSatietyRatio;
    }

    public double getActionEnergyCostRatio() {
        return actionEnergyCostRatio;
    }

    public void setActionEnergyCostRatio(double actionEnergyCostRatio) {
        this.actionEnergyCostRatio = actionEnergyCostRatio;
    }

    public double getActionSatietyCostRatio() {
        return actionSatietyCostRatio;
    }

    public void setActionSatietyCostRatio(double actionSatietyCostRatio) {
        this.actionSatietyCostRatio = actionSatietyCostRatio;
    }
}