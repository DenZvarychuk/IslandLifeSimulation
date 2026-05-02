package org.island.entity.animals;

import org.island.config.AnimalConfig;
import org.island.config.DietConfig;
import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.eating.EatStrategy;
import org.island.engine.actions.eating.AnimalConfigEatStrategy;
import org.island.engine.actions.movements.LandMoveStrategy;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.movements.MovementStrategy;
import org.island.engine.actions.resting.IdleRestStrategy;
import org.island.engine.actions.resting.RestResult;
import org.island.engine.actions.resting.RestStrategy;
import org.island.engine.actions.resting.SleepRestStrategy;
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
        this.movementStrategy = new LandMoveStrategy();
        this.eatStrategy = new AnimalConfigEatStrategy();
    }

    public MoveResult move(Island island) {
        if (moveSteps <= 0 || movementStrategy == null) {
            Location current = island.getLocation(this.x, this.y);
            return new MoveResult(this, current, current, 0, false);
        }
        return movementStrategy.calculateMove(this, island);
    }

    public EatResult eat(Island island) {
        Location location = island.getLocation(this.x, this.y);
        if (satiety <= minSatiety) {
            System.out.println(this.getId() + " is starving");
            return eatStrategy.calculateEat(this, island);
        }
        return new EatResult(this, null, location, false);
    };

    public RestResult rest(Island island) {
        Location location = island.getLocation(this.x, this.y);

        if (energy < maxSatiety * 0.1){
            System.out.println(this.getId() + " is exhausted");
            restStrategy = new SleepRestStrategy();
            return restStrategy.calculateRest(this, island);
        } else if (energy < maxSatiety * 0.5) {
            System.out.println(this.getId() + " is tired");
            restStrategy = new IdleRestStrategy();
            return restStrategy.calculateRest(this, island);
        }



        return new RestResult(this, location, false, energy, energy);
    }

    public abstract void reproduce();

    public boolean shouldExist() {
        return satiety > 0;
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

    public double getActionCost() {
        return actionCost;
    }

    public DietConfig getDiet() {
        return diet;
    }

    public double getWeight() {
        return weight;
    }
}
