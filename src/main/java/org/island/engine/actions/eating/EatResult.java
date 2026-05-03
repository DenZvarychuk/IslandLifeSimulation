package org.island.engine.actions.eating;

import org.island.engine.actions.ActionType;
import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.playground.Location;

public class EatResult {
    private final Animal animal;
    private final Entity food;
    private final Location location;
    private final boolean successfull;
    private final ActionType actionType = ActionType.EAT;

    public EatResult(Animal animal, Entity food, Location location, boolean successful) {
        this.animal = animal;
        this.food = food;
        this.location = location;
        this.successfull = successful;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Entity getFood() {
        return food;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isSuccessful() {
        return successfull;
    }

    public ActionType getActionType() {
        return actionType;
    }
}