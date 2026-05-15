package org.island.engine.actions;

import org.island.entity.animals.Animal;
import org.island.playground.Location;

public abstract class ActionResult {
    protected ActionType actionType;
    protected final Animal animal;
    protected final Location baseActionLocation;
    protected ActionResultStatus status;

    public ActionResult(ActionType actionType, Animal animal, Location baseActionLocation, ActionResultStatus status) {
        this.actionType = actionType;
        this.animal = animal;
        this.baseActionLocation = baseActionLocation;
        this.status = status;
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

    public ActionResultStatus getStatus() {
        return status;
    }

    public void setStatus(ActionResultStatus status) {
        this.status = status;
    }
}
