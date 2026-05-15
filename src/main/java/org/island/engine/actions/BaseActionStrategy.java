package org.island.engine.actions;

import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;

public interface BaseActionStrategy {
    default Location getCurrentLocation(Animal animal, Island island) {
        return island.getLocation(animal.getX(), animal.getY());
    }

}