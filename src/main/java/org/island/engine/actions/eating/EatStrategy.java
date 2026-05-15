package org.island.engine.actions.eating;

import org.island.engine.actions.BaseActionStrategy;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

public interface EatStrategy extends BaseActionStrategy {

    EatResult calculateEat(Animal animal, Island island);
}