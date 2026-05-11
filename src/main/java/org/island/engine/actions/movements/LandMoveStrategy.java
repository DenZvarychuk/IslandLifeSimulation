package org.island.engine.actions.movements;

import org.island.config.action.MoveConfig;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.playground.SurfaceType;

import java.util.ArrayList;
import java.util.List;

public class LandMoveStrategy implements MoveStrategy {
    private final MoveConfig config;
    private static final ActionType actionType = ActionType.MOVE_LAND;

    public LandMoveStrategy(MoveConfig config) {
        this.config = config;
    }

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
        System.out.println("animal " + animal.getId() + " will be mooving");
        if (path.size() >= 1)
            return new MoveResult(actionType, animal, path.get(0), currentLocation, stepsTaken, path, ActionResultStatus.SUCCESS);
        else
            return new MoveResult(actionType, animal, path.get(0), currentLocation, stepsTaken, path, ActionResultStatus.FAILED_NO_TARGET_FOUND);
    }

    private Location findValidLocation(Location current, Island island) {
        for (int attempt = 0; attempt < config.getFindLocationRetries(); attempt++) {
            Direction dir = Direction.getRandomDirection();
            Location candidate = island.getLocation(
                    current.getX() + dir.getDx(),
                    current.getY() + dir.getDy()
            );

            if (isLocationSuitable(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private boolean isLocationSuitable(Location candidate) {
        return candidate != null && candidate.getSurface() != SurfaceType.WATER;
    }

}
