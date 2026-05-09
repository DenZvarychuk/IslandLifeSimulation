package org.island.engine.actions;

import org.island.engine.SimulationContext;
import org.island.engine.actions.eating.EatExecutor;
import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.movements.MoveExecutor;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.resting.RestExecutor;
import org.island.engine.actions.resting.RestResult;
import org.island.playground.Island;

public class ActionExecutor {
    private MoveExecutor moveExecutor;
    private EatExecutor eatExecutor;
    private RestExecutor restExecutor;

    public ActionExecutor(SimulationContext context) {
        // TODO move Executors to Factory
        this.moveExecutor = new MoveExecutor(context);
        this.eatExecutor = new EatExecutor(context);
        this.restExecutor = new RestExecutor(context);
    }

    public ActionResult executeDecision(ActionDecision decision, Island island) {
        return switch (decision.getActionType()) {
            case EAT -> eatExecutor.calculate(decision, island);
            case MOVE -> moveExecutor.calculate(decision, island);
            case REST -> restExecutor.calculate(decision, island);
            case NONE -> {
                NoActionStrategy strategy = (NoActionStrategy) decision.getStrategy();
                yield strategy.calculateRest(decision.getAnimal(), island);
            }
            default -> throw new IllegalStateException("Unexpected value: " + decision.getActionType());
        };
    }

    public <T extends ActionResult> void applyResult(ActionResult result) {
        switch (result.getActionType()) {
            case EAT  -> { if (result instanceof EatResult r) eatExecutor.apply(r); }
            case MOVE_LAND  -> { if (result instanceof MoveResult r) moveExecutor.apply(r); }
            case REST_IDLE, REST_SLEEP  -> { if (result instanceof RestResult r) restExecutor.apply(r); }
            case NONE -> {}
            default -> throw new IllegalStateException("Unexpected value: " + result.getActionType());
        }
    }

}