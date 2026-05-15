package org.island.engine.actions.resting;

import org.island.engine.actions.BaseActionStrategy;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

public interface RestStrategy extends BaseActionStrategy {
    RestResult calculateRest(Animal animal, Island island);

}