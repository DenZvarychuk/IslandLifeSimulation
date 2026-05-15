package org.island.engine.actions.resting;

import org.island.config.action.RestConfig;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;

import java.util.Random;

// TODO REFACTOR
public class IdleRestStrategy implements RestStrategy {
    private final Random random = new Random();
    private final ActionType actionType = ActionType.REST_IDLE;
    private final RestConfig config;

    public IdleRestStrategy(RestConfig config) {
        this.config = config;
    }

    @Override
    public RestResult calculateRest(Animal animal, Island island) {
        int currX = animal.getX();
        int currY = animal.getY();
        Location currentLocation = island.getLocation(currX, currY);

        double energyBefore = animal.getEnergy();

        if (random.nextDouble() < config.getIdle().getChance()) {
            double gainedEnergy = energyBefore * config.getIdle().getEnergyBoost();
            System.out.println("animal " + animal.getId() + " will be resting");
            return new RestResult(actionType, animal, currentLocation, energyBefore, gainedEnergy, ActionResultStatus.SUCCESS);
        } else
            return new RestResult(actionType, animal, currentLocation, energyBefore, energyBefore, ActionResultStatus.FAILED_PROBABILITY_CHECK);
    }
}