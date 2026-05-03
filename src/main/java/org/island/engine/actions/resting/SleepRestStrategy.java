package org.island.engine.actions.resting;

import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;

public class SleepRestStrategy implements RestStrategy {

    public RestResult calculateRest(Animal animal, Island island) {
        int currX = animal.getX();
        int currY = animal.getY();
        Location currentLocation = island.getLocation(currX, currY);

        double energyBefore = animal.getEnergy();

        // TODO move it into actionPicker or something
        if (animal.getEnergy() < animal.getMaxSatiety() * 0.1){
            double gainedEnergy = animal.getMaxSatiety();
            return new RestResult(animal, currentLocation, true, energyBefore, gainedEnergy);
        }

        return new RestResult(animal, currentLocation, false, energyBefore, energyBefore);
    }
}
