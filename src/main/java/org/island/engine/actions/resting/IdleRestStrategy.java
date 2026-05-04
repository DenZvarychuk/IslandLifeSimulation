package org.island.engine.actions.resting;

import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;

import java.util.Random;

// TODO REFACTOR
public class IdleRestStrategy implements RestStrategy {
    private final Random random = new Random();
    private final ActionType actionType = ActionType.REST_IDLE;

    @Override
    public RestResult calculateRest(Animal animal, Island island) {
        int currX = animal.getX();
        int currY = animal.getY();
        Location currentLocation = island.getLocation(currX, currY);

        double energyBefore = animal.getEnergy();

        // TODO move it into actionPicker or something
        if (random.nextDouble() < 0.5) {
            double gainedEnergy = energyBefore * 1.2;
            return new RestResult(actionType, animal, currentLocation, energyBefore, gainedEnergy, true);
        } else return new RestResult(actionType, animal, currentLocation, energyBefore, energyBefore, false);
    }
}
