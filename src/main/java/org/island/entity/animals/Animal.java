package org.island.entity.animals;

import org.island.config.AnimalConfig;
import org.island.engine.movements.LandStrategy;
import org.island.engine.movements.MoveResult;
import org.island.engine.movements.MovementStrategy;
import org.island.entity.Entity;
import org.island.playground.Island;
import org.island.playground.Location;

public abstract class Animal extends Entity<AnimalType> {

    protected double weight;
    protected int moveSteps;
    protected double energy;
    protected double maxSatiety;
    protected double minSatiety;
    protected double satiety;
    protected int maxOnLocation;
    // TODO reconsider energy
    protected double moveCost;

    protected MovementStrategy movementStrategy;

    public Animal(AnimalConfig config, AnimalType type) {
        super(type);
        this.weight = config.getWeight();
        this.moveSteps = config.getMoveSteps();
        this.maxSatiety = config.getMaxSatiety();
        this.minSatiety = config.getMaxSatiety() / 2;
        this.energy = config.getMaxSatiety();
        this.satiety = config.getMaxSatiety();
        this.maxOnLocation = config.getMaxOnLocation();
        this.moveCost = config.getMaxSatiety() * 0.1;
        this.movementStrategy = new LandStrategy();
    }

    public MoveResult move(Island island) {
        if (moveSteps <= 0 || movementStrategy == null) {
            Location current = island.getLocation(this.x, this.y);
            return new MoveResult(this, current, current, 0, false);
        }
        return movementStrategy.calculateMove(this, island);
    }

    public abstract void eat();

    public abstract void reproduce();

    public boolean shouldExist() {
        return energy > 0 && maxSatiety > 0;
    }

    public void markAsDead() {
        this.isExist = false;
    }

    public int getMoveSteps() {
        return moveSteps;
    }

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getMaxSatiety() {
        return maxSatiety;
    }

    public double getSatiety() {
        return satiety;
    }

    public void setSatiety(double satiety) {
        this.satiety = satiety;
    }

    public double getMoveCost() {
        return moveCost;
    }
}
