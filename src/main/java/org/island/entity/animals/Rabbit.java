package org.island.entity.animals;

import org.island.config.entity.AnimalConfig;
import org.island.playground.Location;

public class Rabbit extends Animal{

    public Rabbit(AnimalConfig config, Location location){
        super(config, AnimalType.RABBIT);
        this.x = location.getX();
        this.y = location.getY();
        location.addEntity(this);
    }

    @Override
    public void reproduce() {

    }
}