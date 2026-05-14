package org.island.engine.actions.policy;

import org.island.engine.actions.eating.EatStrategy;
import org.island.engine.actions.movements.MoveStrategy;
import org.island.engine.actions.resting.RestStrategy;
import org.island.entity.animals.Animal;

public interface ActionPolicy {
    EatStrategy getEatStrategy(Animal animal);
    RestStrategy getRestStrategy(Animal animal);
    MoveStrategy getMoveStrategy(Animal animal);
}