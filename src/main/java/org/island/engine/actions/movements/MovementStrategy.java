package org.island.engine.actions.movements;

import org.island.engine.actions.BaseActionStrategy;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

public interface MovementStrategy extends BaseActionStrategy {

    MoveResult calculateMove(Animal animal, Island island);
}