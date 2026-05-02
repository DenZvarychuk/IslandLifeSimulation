package org.island.engine.actions.movements;

import org.island.entity.animals.Animal;
import org.island.playground.Island;

public interface MovementStrategy {

    MoveResult calculateMove(Animal animal, Island island);
}
