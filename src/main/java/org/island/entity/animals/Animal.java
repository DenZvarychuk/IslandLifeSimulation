package org.island.entity.animals;

import org.island.config.AnimalConfig;
import org.island.engine.movements.LandStrategy;
import org.island.engine.movements.MoveResult;
import org.island.engine.movements.MovementStrategy;
import org.island.entity.Entity;
import org.island.playground.Island;
import org.island.playground.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal extends Entity<AnimalType> {

    protected double weight;
    protected int moveSteps;
    protected double energy;
    protected double maxSatiety;
    protected double minSatiety;
    protected int maxOnLocation;

    protected MovementStrategy movementStrategy;

    public Animal(AnimalConfig config, AnimalType type) {
        super(type);
        this.weight = config.getWeight();
        this.moveSteps = config.getMoveSteps();
        this.maxSatiety = config.getMaxSatiety();
        this.energy = config.getMaxSatiety();
        this.minSatiety = config.getMaxSatiety() / 2;
        this.maxOnLocation = config.getMaxOnLocation();
        this.movementStrategy = new LandStrategy();
    }

    public MoveResult calculateMove(Island island) {
        if (moveSteps <= 0 || movementStrategy == null) {
            Location current = island.getLocation(this.x, this.y);
            return new MoveResult(this, current, current, 0, false);
        }

        return movementStrategy.calculateMove(this, island);
    }

    public abstract void eat();

    public abstract void reproduce();

    public int getMoveSteps() {
        return moveSteps;
    }

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

}
