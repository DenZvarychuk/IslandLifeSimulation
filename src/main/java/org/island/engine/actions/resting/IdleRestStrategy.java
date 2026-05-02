package org.island.engine.actions.resting;

import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;

import java.util.Random;

public class IdleRestStrategy implements RestStrategy {
    private final Random random = new Random();

    @Override
    public RestResult calculateRest(Animal animal, Island island) {
        int currX = animal.getX();
        int currY = animal.getY();
        Location currentLocation = island.getLocation(currX, currY);

        double energyBefore = animal.getEnergy();

        // TODO move it into actionPicker or something
        if (random.nextDouble() < 0.5) {
            double gainedEnergy = energyBefore * 1.2;
            return new RestResult(animal, currentLocation, true, energyBefore, gainedEnergy);
        }

        return new RestResult(animal, currentLocation, false, energyBefore, energyBefore);
    }
}
