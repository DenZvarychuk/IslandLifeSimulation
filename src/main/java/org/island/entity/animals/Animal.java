package org.island.entity.animals;

import org.island.config.entity.AnimalConfig;
import org.island.config.entity.DietConfig;
import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.eating.EatStrategy;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.movements.MoveStrategy;
import org.island.engine.actions.policy.ActionPolicy;
import org.island.engine.actions.resting.RestResult;
import org.island.engine.actions.resting.RestStrategy;
import org.island.entity.Entity;
import org.island.playground.Island;

public abstract class Animal extends Entity<AnimalType> {

    protected double weight;
    protected int moveSteps;
    protected double energy;
    protected double maxSatiety;
    protected double minSatiety;
    protected double satiety;
    protected int maxOnLocation;
    protected int sleepCycles = -1;
    // TODO reconsider energy
    protected double actionCost;

    private final DietConfig diet;
    private ActionPolicy actionPolicy;

    public Animal(AnimalConfig config, AnimalType type) {
        super(type);
        this.diet = config.getDiet();
        this.weight = config.getWeight();
        this.moveSteps = config.getMoveSteps();
        this.maxSatiety = config.getMaxSatiety();
        this.minSatiety = config.getMaxSatiety() / 2;
        this.energy = config.getMaxSatiety();
        this.satiety = config.getMaxSatiety();
        this.maxOnLocation = config.getMaxOnLocation();
        this.actionCost = config.getMaxSatiety() * 0.1;
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

    public double getActionCost() {
        return actionCost;
    }

    public DietConfig getDiet() {
        return diet;
    }

    public double getWeight() {
        return weight;
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
}