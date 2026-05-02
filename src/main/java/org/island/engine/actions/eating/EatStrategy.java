package org.island.engine.actions.eating;

import org.island.entity.animals.Animal;
import org.island.playground.Island;

public interface EatStrategy {

    EatResult calculateEat(Animal animal, Island island);
}
