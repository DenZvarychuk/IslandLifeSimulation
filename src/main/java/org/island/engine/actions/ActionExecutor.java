package org.island.engine.actions;

import org.island.config.action.ActionConfig;
import org.island.engine.SimulationContext;
import org.island.engine.actions.eating.EatExecutor;
import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.movements.MoveExecutor;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.resting.RestExecutor;
import org.island.engine.actions.resting.RestResult;
import org.island.playground.Island;

import javax.swing.*;
import java.util.List;

public class ActionExecutor {
    private MoveExecutor moveExecutor;
    private EatExecutor eatExecutor;
    private RestExecutor restExecutor;
    private ActionPicker actionPicker;

    public ActionExecutor(SimulationContext context, ActionConfig actionConfig) {
        // TODO move Executors to Factory
        this.moveExecutor = new MoveExecutor(context);
        this.eatExecutor = new EatExecutor(context);
        this.restExecutor = new RestExecutor(context);

        this.actionPicker = new ActionPicker(context, actionConfig);
    }

    public List<ActionResult> decideAndCalculate(Island island) {
        System.out.println("\n----- Deciding actions phase -----");
        List<ActionDecision> actionDecisions = actionPicker.pickAction(island);

        System.out.println("\n----- Action calculating phase -----");
        return actionDecisions.stream()
                .map(decision -> executeDecision(decision, island))
                .toList();
    }

    public void applyActions(List<ActionResult> actionResults) {
        System.out.println("\n----- Action applying phase -----");
        for (ActionResult result : actionResults) {
            applyResult(result);
        }
    }

    private ActionResult executeDecision(ActionDecision decision, Island island) {
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

    private <T extends ActionResult> void applyResult(ActionResult result) {

        if (!result.getAnimal().isExist()) {
            result.setStatus(ActionResultStatus.FAILED_DIED);
            System.out.println(result.getAnimal() +
                    " action: " + result.getActionType() +
                    "\n status: " + result.getStatus());
            return;
        }

        if (result.getStatus() != ActionResultStatus.SUCCESS) {
            System.out.println(result.getAnimal() +
                    " action: " + result.getActionType() +
                    "\n failed because of status :" + result.getStatus());
            return;
        }

        System.out.println(result.getAnimal() +
                " action: " + result.getActionType() +
                "\n status: " + result.getStatus());

        switch (result.getActionType()) {
            case EAT  -> { if (result instanceof EatResult r) eatExecutor.apply(r); }
            case MOVE_LAND  -> { if (result instanceof MoveResult r) moveExecutor.apply(r); }
            case REST_IDLE, REST_SLEEP  -> { if (result instanceof RestResult r) restExecutor.apply(r); }
            case NONE -> {}
            default -> throw new IllegalStateException("Unexpected value: " + result.getActionType());
        }
    }

}