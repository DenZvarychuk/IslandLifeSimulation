package org.island.engine.actions.movements;

import org.island.engine.actions.ActionResult;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Location;

import java.util.ArrayList;
import java.util.List;

public class MoveResult extends ActionResult {
    private final Location endLocation;
    private final List<Location> path;
    private final int stepsTaken;

    public MoveResult(ActionType actionType,
                      Animal animal,
                      Location start,
                      Location end,
                      int stepsTaken,
                      List<Location> path,
                      ActionResultStatus status) {
        super(actionType, animal, start, status);
        this.endLocation = end;
        this.stepsTaken = stepsTaken;
        this.path = path != null ? new ArrayList<>(path) : new ArrayList<>();
    }

    public void addPathStep(Location location) {
        path.add(location);
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public List<Location> getPath() {
        return path;
    }

    public int getStepsTaken() {
        return stepsTaken;
    }

    // TODO refactor
    public boolean isMovedAtSameLocation() {
        return baseActionLocation.equals(endLocation);
    }

}