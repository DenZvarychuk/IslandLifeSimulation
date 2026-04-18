package org.island.entity.animals;

import org.island.entity.Entity;

public abstract class Animal extends Entity {

    protected double weight;
    protected double speed;
    protected double energy;
    protected double maxSatiety;
    protected double minSatiety;


    public abstract void move();
    public abstract void eat();
    public abstract void reproduce();
}
