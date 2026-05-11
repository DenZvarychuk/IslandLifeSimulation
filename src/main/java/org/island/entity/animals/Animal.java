package org.island.entity.animals;

import org.island.config.entity.AnimalConfig;
import org.island.config.entity.DietConfig;
import org.island.engine.actions.policy.ActionPolicy;
import org.island.entity.Entity;

public abstract class Animal extends Entity<AnimalType> {

    protected int moveSteps;
    protected double fullEnergy;
    protected double energy;
    protected double maxSatiety;
    protected double minSatiety;
    protected double satiety;
    protected int sleepCycles = -1;
    // TODO reconsider energy
    protected double actionEnergyCost;
    protected double actionSatietyCost;

    private final DietConfig diet;
    private ActionPolicy actionPolicy;

    public Animal(AnimalConfig config, AnimalType type) {
        super(type, config.getWeight(), config.getMaxOnLocation());
        this.diet = config.getDiet();
        this.moveSteps = config.getMoveSteps();
        this.maxSatiety = config.getMaxSatiety();
        this.minSatiety = maxSatiety * config.getMinSatietyRatio();
        this.satiety = maxSatiety;
        this.fullEnergy = config.getBaseEnergy();
        this.energy = fullEnergy;
        this.actionEnergyCost = fullEnergy * config.getActionEnergyCostRatio();
        this.actionSatietyCost = maxSatiety * config.getActionSatietyCostRatio();
    }

    public abstract void reproduce();

    public boolean shouldExist() {
        return satiety > 0;
    }

    public boolean isSleeping() {
        return sleepCycles > -1;
    }

    public int getMoveSteps() {
        return moveSteps;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        if (energy <= 0) {
            this.energy = 0;
        } else this.energy = energy;
    }

    public double getMaxSatiety() {
        return maxSatiety;
    }

    public double getMinSatiety() {
        return minSatiety;
    }

    public double getSatiety() {
        return satiety;
    }

    public void setSatiety(double satiety) {
        if (satiety <= 0) {
            this.satiety = 0;
        } else this.satiety = satiety;
    }

    public double getActionEnergyCost() {
        return actionEnergyCost;
    }

    public double getActionSatietyCost() {
        return actionSatietyCost;
    }

    public DietConfig getDiet() {
        return diet;
    }

    public int getSleepCycles() {
        return sleepCycles;
    }

    public void setSleepCycles(int cycles) {
        this.sleepCycles = cycles;
    }

    public ActionPolicy getActionPolicy() {
        return actionPolicy;
    }

    public void setActionPolicy(ActionPolicy actionPolicy) {
        this.actionPolicy = actionPolicy;
    }

    public double getFullEnergy() {
        return fullEnergy;
    }
}