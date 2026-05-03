package org.island.engine.actions.movements;

import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;

public class NoMoveStrategy implements MovementStrategy {
    @Override
    public MoveResult calculateMove(Animal animal, Island island) {
        Location currentLocation = island.getLocation(animal.getX(), animal.getY());
        return new MoveResult(ActionType.NONE, animal, currentLocation, currentLocation, 0, false);
    }
}
