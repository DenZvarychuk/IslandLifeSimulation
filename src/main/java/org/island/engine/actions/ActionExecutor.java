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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ActionExecutor {
    private MoveExecutor moveExecutor;
    private EatExecutor eatExecutor;
    private RestExecutor restExecutor;
    private ActionPicker actionPicker;
    private final ExecutorService executorService;

    public ActionExecutor(SimulationContext context, ActionConfig actionConfig) {
        // TODO move Executors to Factory
        this.moveExecutor = new MoveExecutor(context);
        this.eatExecutor = new EatExecutor(context);
        this.restExecutor = new RestExecutor(context);
        this.actionPicker = new ActionPicker(context, actionConfig);
        this.executorService = context.getExecutorService();
    }

    public List<ActionResult> decideAndCalculate(Island island) {
        System.out.println("\n----- Deciding actions phase -----");
        long startTime = System.nanoTime();
        List<ActionDecision> actionDecisions = actionPicker.pickAction(island);

        System.out.println("\n----- Action calculating phase -----");
        List<Future<ActionResult>> futures = new ArrayList<>(actionDecisions.size());

        for (ActionDecision decision : actionDecisions) {
            futures.add(executorService.submit(() -> executeDecision(decision, island)));
        }

        List<ActionResult> results = new ArrayList<>(actionDecisions.size());

        for (int i = 0; i < futures.size(); i++) {
            ActionDecision decision = actionDecisions.get(i);
            try {
                results.add(futures.get(i).get());
            } catch (ExecutionException e) {
                System.err.println("Action execution failed for decision: " +
                        decision.getAnimal().getId() +
                        ": " + e.getMessage());
                results.add(new NoActionStrategy().calculateRest(decision.getAnimal(), island));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Action execution interrupted for decision: " +
                        decision.getAnimal().getId() +
                        ": " + e.getMessage());
                results.add(new NoActionStrategy().calculateRest(decision.getAnimal(), island));
            }
        }

        long difMs = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Action picking, deciding and calculating took: " + difMs + "ms for " + island.getAllAnimals().size() + " animals");

        return results;
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