package org.island.engine.eating;

import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.entity.plants.Plant;
import org.island.playground.Island;
import org.island.playground.Location;

public class HerbivoreEatStrategy implements EatStrategy {

    @Override
    public EatResult calculateEat(Animal animal, Island island) {
        int currX = animal.getX();
        int currY = animal.getY();
        Location currentLocation = island.getLocation(currX, currY);

        Entity plant = currentLocation.getEntities().stream()
                .filter(entity -> entity instanceof Plant)
                .findFirst()
                .orElse(null);

        if (plant == null) {
            return new EatResult(animal, null, currentLocation, false);
        }

        return new EatResult(animal, plant, currentLocation, true);
    }
}
