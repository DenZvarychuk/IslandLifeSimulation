package org.island.engine.actions;

import org.island.playground.Island;

import java.util.List;

public interface ActionExecutor<T extends ActionResult> {
    T calculate(ActionDecision decision, Island island);
    void apply(T result);
}