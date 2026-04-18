package org.island.entity.plants;

import org.island.entity.Entity;
import org.island.entity.animals.AnimalType;

public abstract class Plant extends Entity<PlantType> {

    private double weight;

    public Plant(PlantType type) {
        super(type);
    }

    public abstract void grow();

    public abstract void fade();

    public abstract void reproduce();

}
