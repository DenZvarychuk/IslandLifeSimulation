package org.island.engine.actions.movements;

import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.playground.SurfaceType;

public class LandMoveStrategy implements MovementStrategy {
    //TODO move it to config
    private static final int MAX_RETRIES = 5;
    private static final ActionType actionType = ActionType.MOVE_LAND;

    // TODO not to move in the same location
    // TODO consider movements for finding prey to eat
    @Override
    public MoveResult calculateMove(Animal animal, Island island) {
        int currX = animal.getX();
        int currY = animal.getY();
        Location fromLocation = island.getLocation(currX, currY);

        int remainingMoveSteps = animal.getMoveSteps();
        int stepsTaken = 0;

        MoveResult result = new MoveResult(
                actionType,
                animal,
                fromLocation,
                fromLocation,
                stepsTaken,
                true);

        result.addPathStep(fromLocation);

        while (remainingMoveSteps > 0) {
            Location nextLocation = findValidLocation(currX, currY, island);

            if (nextLocation == null) break;

            currX = nextLocation.getX();
            currY = nextLocation.getY();
            result.addPathStep(nextLocation);
            stepsTaken++;
            remainingMoveSteps--;
        }

        Location finalLocation = island.getLocation(currX, currY);

        return new MoveResult(actionType, animal, fromLocation, finalLocation, stepsTaken, true);
    }

    private Location findValidLocation(int x, int y, Island island) {

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++){
            Direction dir = Direction.getRandomDirection();
            int nextX = x + dir.getDx();
            int nextY = y + dir.getDy();

            Location candidate = island.getLocation(nextX, nextY);

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
