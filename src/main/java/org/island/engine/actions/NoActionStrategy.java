package org.island.engine.actions;

import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.eating.EatStrategy;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.movements.MoveStrategy;
import org.island.engine.actions.resting.RestResult;
import org.island.engine.actions.resting.RestStrategy;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;

public class NoActionStrategy implements MoveStrategy, RestStrategy, EatStrategy {
    private final ActionType NONE = ActionType.NONE;
    private final boolean notSuccessful = false;

    @Override
    public EatResult calculateEat(Animal animal, Island island) {
        Location currentLocation = getCurrentLocation(animal, island);
        return new EatResult(NONE, animal, null, currentLocation, notSuccessful);
    }

    @Override
    public MoveResult calculateMove(Animal animal, Island island) {
        Location currentLocation = getCurrentLocation(animal, island);
        return new MoveResult(NONE, animal, currentLocation, currentLocation, 0, null, notSuccessful);
    }

    @Override
    public RestResult calculateRest(Animal animal, Island island) {
        Location currentLocation = getCurrentLocation(animal, island);
        double energy = animal.getEnergy();
        return new RestResult(NONE, animal, currentLocation, energy, energy, notSuccessful);
    }
}