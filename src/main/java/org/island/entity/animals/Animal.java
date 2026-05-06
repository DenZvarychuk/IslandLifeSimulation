package org.island.entity.animals;

import org.island.config.AnimalConfig;
import org.island.config.DietConfig;
import org.island.engine.actions.eating.AnimalConfigEatStrategy;
import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.eating.EatStrategy;
import org.island.engine.actions.movements.LandMoveStrategy;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.movements.MovementStrategy;
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
    protected int sleepCycles = 0;
    // TODO reconsider energy
    protected double actionCost;


    private final DietConfig diet;
    protected MovementStrategy movementStrategy;
    protected EatStrategy eatStrategy;
    protected RestStrategy restStrategy;

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

    // methods move(), eat() and rest() not needed after refactoring
    // leaved in code for testing purposes
    public MoveResult move(Island island) {
        return movementStrategy.calculateMove(this, island);
    }
    public EatResult eat(Island island) {
        return eatStrategy.calculateEat(this, island);
    }
    public RestResult rest(Island island) {
        return restStrategy.calculateRest(this, island);
    }

    public abstract void reproduce();

    public boolean shouldExist() {
        return satiety > 0;
    }

    public boolean isSleeping() {
        return sleepCycles > 0;
    }

    public int getMoveSteps() {
        return moveSteps;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public EatStrategy getEatStrategy() {
        return eatStrategy;
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

}