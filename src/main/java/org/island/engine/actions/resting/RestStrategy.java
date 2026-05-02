package org.island.engine.actions.resting;

import org.island.entity.animals.Animal;
import org.island.playground.Island;

public interface RestStrategy {

    RestResult calculateRest(Animal animal, Island island);

}
