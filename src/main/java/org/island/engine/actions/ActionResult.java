package org.island.engine.actions;

import org.island.entity.animals.Animal;
import org.island.playground.Location;

public abstract class ActionResult {
    protected ActionType actionType;
    protected final Animal animal;
    protected final Location baseActionLocation;
    protected final boolean isSuccessful;

    public ActionResult(ActionType actionType, Animal animal, Location baseActionLocation, boolean isSuccessful) {
        this.actionType = actionType;
        this.animal = animal;
        this.baseActionLocation = baseActionLocation;
        this.isSuccessful = isSuccessful;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Location getBaseActionLocation() {
        return baseActionLocation;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
