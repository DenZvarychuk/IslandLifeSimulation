package org.island.entity.plants;

import org.island.config.PlantConfig;
import org.island.playground.Location;

public class Bush extends Plant{

    public Bush(PlantConfig config, Location location) {
        super(config, PlantType.TREE);
        this.x = location.getX();
        this.y = location.getY();
        location.addEntity(this);
    }


    @Override
    public void grow() {

    }

    @Override
    public void fade() {

    }

    @Override
    public void reproduce() {

    }

    @Override
    public void update() {

    }
}
