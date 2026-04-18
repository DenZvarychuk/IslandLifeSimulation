package org.island.entity.animals;

import org.island.config.AnimalConfig;
import org.island.entity.Entity;

public abstract class Animal extends Entity<AnimalType> {

    protected double weight;
    protected double speed;
    protected double energy;
    protected double maxSatiety;
    protected double minSatiety;
    protected int maxOnLocation;

    public Animal(AnimalConfig config, AnimalType type) {
        this.weight = config.getWeight();
        this.speed = config.getSpeed();
        this.maxSatiety = config.getMaxSatiety();
        this.energy = config.getMaxSatiety();
        this.minSatiety = config.getMaxSatiety() / 2;
        this.maxOnLocation = config.getMaxOnLocation();
        super(type);
    }


    public abstract void move();

    public abstract void eat();

    public abstract void reproduce();
}
