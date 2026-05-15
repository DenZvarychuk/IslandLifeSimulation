package org.island.engine.actions.resting;

import org.island.config.action.RestConfig;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;

// TODO REFACTOR
public class SleepRestStrategy implements RestStrategy {
    private final RestConfig config;
    private final ActionType actionType = ActionType.REST_SLEEP;

    public SleepRestStrategy(RestConfig config) {
        this.config = config;
    }

    public RestResult calculateRest(Animal animal, Island island) {
        Location currentLocation = getCurrentLocation(animal, island);

        double energyBefore = animal.getEnergy();
        double gainedEnergy = animal.getMaxSatiety();

        animal.setSleepCycles(config.getSleep().getCycleCount());
        // TODO remove sout
        System.out.println(animal.getId() + "will falling to sleep");
        return new RestResult(actionType, animal, currentLocation, energyBefore, gainedEnergy, ActionResultStatus.SUCCESS);
    }
}
