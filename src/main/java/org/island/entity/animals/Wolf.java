package org.island.entity.animals;

import org.island.config.AnimalConfig;
import org.island.engine.movements.MoveResult;
import org.island.playground.Island;
import org.island.playground.Location;

public class Wolf extends Animal{

   public Wolf(AnimalConfig config, Location location){
       super(config, AnimalType.WOLF);
       this.x = location.getX();
       this.y = location.getY();
       location.addEntity(this);
   }

    @Override
    public void eat() {

    }

    @Override
    public void reproduce() {

    }

    @Override
    public void update() {

    }
}
