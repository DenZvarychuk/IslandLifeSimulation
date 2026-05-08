package org.island.entity.animals;

import org.island.config.entity.AnimalConfig;
import org.island.playground.Location;

public class Wolf extends Animal{

   public Wolf(AnimalConfig config, Location location){
       super(config, AnimalType.WOLF);
       this.x = location.getX();
       this.y = location.getY();
       location.addEntity(this);
   }

    @Override
    public void reproduce() {

    }
}
