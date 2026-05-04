package org.island.engine.actions.movements;

import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.playground.SurfaceType;

import java.util.ArrayList;
import java.util.List;

public class LandMoveStrategy implements MovementStrategy {
    //TODO move it to config
    private static final int MAX_RETRIES = 5;
    private static final ActionType actionType = ActionType.MOVE_LAND;

    @Override
    public MoveResult calculateMove(Animal animal, Island island) {
        Location currentLocation = getCurrentLocation(animal, island);
        int stepsTaken = 0;
        List<Location> path = new ArrayList<>();
        path.add(currentLocation);

        while (stepsTaken < animal.getMoveSteps()) {
            Location nextLocation = findValidLocation(currentLocation, island);

            if (nextLocation == null) break;

            currentLocation = nextLocation;
            path.add(nextLocation);
            stepsTaken++;
        }

        return new MoveResult(actionType, animal, path.get(0), currentLocation, stepsTaken, path,true);
    }

    private Location findValidLocation(Location current, Island island) {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++){
            Direction dir = Direction.getRandomDirection();
            Location candidate = island.getLocation(
                    current.getX() + dir.getDx(),
                    current.getY() + dir.getDy()
            );

            if (isLocationSuitable(candidate)){
                return candidate;
            }
        }
        return null;
    }

    private boolean isLocationSuitable(Location candidate) {
        return candidate != null && candidate.getSurface() != SurfaceType.WATER;
    }

}
