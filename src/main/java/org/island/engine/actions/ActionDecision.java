package org.island.engine.actions;

import org.island.entity.animals.Animal;

public class ActionDecision {
    private final Animal animal;
    private final ActionType actionType;
    private final BaseActionStrategy strategy;

    public ActionDecision(Animal animal, ActionType actionType, BaseActionStrategy strategy) {
        this.animal = animal;
        this.actionType = actionType;
        this.strategy = strategy;
    }

    public Animal getAnimal() {
        return animal;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public BaseActionStrategy getStrategy() {
        return strategy;
    }

}