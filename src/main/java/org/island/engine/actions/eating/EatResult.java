package org.island.engine.actions.eating;

import org.island.engine.actions.ActionResult;
import org.island.engine.actions.ActionType;
import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.playground.Location;

public class EatResult extends ActionResult {
    private final Entity food;

    public EatResult(ActionType actionType, Animal animal, Entity food, Location location, boolean isSuccessful) {
        super(actionType, animal, location, isSuccessful);
        this.food = food;
    }

    public Entity getFood() {
        return food;
    }

}