package org.island.engine.actions;

import org.island.playground.Island;

public interface BaseExecutor<T extends ActionResult> {
    T calculate(ActionDecision decision, Island island);
    void apply(T result);
}
