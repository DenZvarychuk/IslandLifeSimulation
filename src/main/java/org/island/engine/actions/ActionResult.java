package org.island.engine.actions;

import org.island.entity.animals.Animal;
import org.island.playground.Location;

public abstract class ActionResult {
    protected ActionType actionType;
    protected final Animal animal;
    protected final Location baseActionLocation;
    protected ActionResultStatus status = ActionResultStatus.NONE;
    protected final boolean isSuccessful;
    protected boolean isFailed;

    public ActionResult(ActionType actionType, Animal animal, Location baseActionLocation, boolean isSuccessful, ActionResultStatus status) {
        this.actionType = actionType;
        this.animal = animal;
        this.baseActionLocation = baseActionLocation;
        this.isSuccessful = isSuccessful;
        this.isFailed = isSuccessful ? false : true;
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

    public boolean isFailed() {
        return isFailed;
    }

    public void setFailed(boolean failed) {
        this.isFailed = failed;
    }

    public ActionResultStatus getStatus() {
        return status;
    }

    public void setStatus(ActionResultStatus status) {
        this.status = status;
    }
}