package org.island.entity.plants;

import org.island.config.entity.PlantConfig;
import org.island.entity.Entity;

public abstract class Plant extends Entity<PlantType> {

    private double weight;
    private int maxOnLocation;

    public Plant(PlantConfig config, PlantType type) {
        super(type, config.getWeight(), maxOnLocation = config.getMaxOnLocation());
    }

    public abstract void grow();

    public abstract void fade();

    public abstract void reproduce();

}