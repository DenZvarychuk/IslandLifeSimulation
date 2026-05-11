package org.island.engine.actions.resting;

import org.island.engine.actions.ActionResult;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Location;

public class RestResult extends ActionResult {
    private final double energyBefore;
    private final double energyAfter;

    public RestResult(ActionType actionType, Animal animal, Location location, double energyBefore, double energyAfter, ActionResultStatus status) {
        super(actionType, animal, location, status);
        this.energyBefore = energyBefore;
        this.energyAfter = energyAfter;
    }

    public double getEnergyBefore() {
        return energyBefore;
    }

    public double getEnergyAfter() {
        return energyAfter;
    }

}