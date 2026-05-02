package org.island.entity.plants;

import org.island.config.PlantConfig;
import org.island.entity.Entity;
import org.island.entity.animals.AnimalType;

public abstract class Plant extends Entity<PlantType> {

    private double weight;
    private int maxOnLocation;

    public Plant(PlantConfig config, PlantType type) {

        this.weight = config.getWeight();
        this.maxOnLocation = config.getMaxOnLocation();
        super(type);
    }

    public abstract void grow();

    public abstract void fade();

    public abstract void reproduce();

    public double getWeight() {
        return weight;
    }

}
