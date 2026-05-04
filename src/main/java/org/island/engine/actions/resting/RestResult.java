package org.island.engine.actions.resting;

import org.island.engine.actions.ActionResult;
import org.island.engine.actions.ActionType;
import org.island.entity.animals.Animal;
import org.island.playground.Location;

public class RestResult extends ActionResult {
    private final double energyBefore;
    private final double energyAfter;

    public RestResult(ActionType actionType, Animal animal, Location location, double energyBefore, double energyAfter, boolean isSuccessful) {
        super(actionType, animal, location, isSuccessful);
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