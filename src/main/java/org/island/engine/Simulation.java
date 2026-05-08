package org.island.engine;

import org.island.config.SimulationConfig;
import org.island.engine.actions.*;
import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.eating.EatExecutor;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.movements.MoveExecutor;
import org.island.engine.actions.resting.RestExecutor;
import org.island.engine.actions.resting.RestResult;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private static int simulationCycleCount;
    private static int cycle = 0;
    private static long cycleDelay;

    List<ActionDecision> actionDecisions = new ArrayList<>();
    List<ActionResult> actionResults = new ArrayList<>();

    private ActionPicker actionPicker;
    private MoveExecutor moveExecutor;
    private EatExecutor eatExecutor;
    private RestExecutor restExecutor;
    private SimulationContext simulationContext;
    private ActionExecutor<? extends ActionResult> actionExecutor;

    public Simulation(SimulationContext simulationContext, SimulationConfig config) {
        this.simulationContext = simulationContext;
        this.simulationCycleCount = config.getSimulationCycleCount();
        this.cycleDelay = config.getCycleDelay();

        // TODO move Executors to Factory
        this.moveExecutor = new MoveExecutor(simulationContext);
        this.eatExecutor = new EatExecutor(simulationContext);
        this.restExecutor = new RestExecutor(simulationContext);
        this.actionPicker = new ActionPicker(simulationContext, config.getActionConfig());
    }

    public void start(Island island) throws InterruptedException {
        System.out.println("=== Simulation Starting ===\n");

        while (hasSimulationCycles()) {
            System.out.println("\n\n----- Step " + cycle + "-----");

            // actions
            decideAndCalculateActions(island);
            applyActions(actionResults);
            // update animal sleep cycles
            updateAnimalSleepCycles(island);
            // print stats
            printActionStats(actionResults);
            System.out.println(island.getEntitiesInAllLocByCount());


            // increment cycle
            cycle++;
            Thread.sleep(cycleDelay);
        }

        System.out.println("\n=== Simulation Complete ===");

    }

    private void decideAndCalculateActions(Island island) {
        System.out.println("\n----- Deciding actions phase -----");
        actionDecisions = actionPicker.pickAction(island);

        System.out.println("\n----- Action calculating phase -----");
        // TODO reconsider, is it ok to clear every Cycle?
        actionResults.clear();
        for (ActionDecision decision : actionDecisions) {
            actionResults.add(executeDecision(decision, island));
        }
    }

    private void applyActions(List<ActionResult> actionResults) {
        System.out.println("\n----- Action applying phase -----");
        for (ActionResult result : actionResults) {
            applyResult(result);
        }
    }

    private void updateAnimalSleepCycles(Island island) {
        List<Animal> animals = island.getAllAnimals();
        for (Animal animal : animals) {
            if (animal.isSleeping()) {
                animal.setSleepCycles(animal.getSleepCycles() - 1);
                if (animal.getSleepCycles() < 0) System.out.println(animal.getId() + " waked up");
            }
        }
    }

    private ActionResult executeDecision(ActionDecision decision, Island island) {
        return switch (decision.getActionType()) {
            case EAT  -> eatExecutor.calculate(decision, island);
            case MOVE -> moveExecutor.calculate(decision, island);
            case REST -> restExecutor.calculate(decision, island);
            // TODO process NONE case
            // case NONE -> ActionResult.noAction(decision.getAnimal());
            default -> throw new IllegalStateException("Unexpected value: " + decision.getActionType());
        };
    }

    private <T extends ActionResult> void applyResult(ActionResult result) {
        switch (result.getActionType()) {
            case EAT  -> { if (result instanceof EatResult r) eatExecutor.apply(r); }
            case MOVE_LAND  -> { if (result instanceof MoveResult r) moveExecutor.apply(r); }
            case REST_IDLE, REST_SLEEP  -> { if (result instanceof RestResult r) restExecutor.apply(r); }
            // TODO process NONE case
            // case NONE -> ActionResult.noAction(decision.getAnimal());
            default -> throw new IllegalStateException("Unexpected value: " + result.getActionType());
        }
    }

    private static boolean hasSimulationCycles() {
        return cycle < simulationCycleCount;
    }

    private void printActionStats(List<ActionResult> results) {
        System.out.println("\n----- Action results -----");

        int totalMoved = 0;
        int totalSteps = 0;
        int totalEaten = 0;
        int totalRest = 0;

        List<MoveResult> moveResult = results.stream()
                .filter(MoveResult.class::isInstance)
                .map(MoveResult.class::cast)
                .toList();

        List<EatResult> eatResults = results.stream()
                .filter(EatResult.class::isInstance)
                .map(EatResult.class::cast)
                .toList();

        List<RestResult> restResults = results.stream()
                .filter(RestResult.class::isInstance)
                .map(RestResult.class::cast)
                .toList();

        for (MoveResult result : moveResult) {
            if (result.getActionType() != ActionType.NONE) {
                System.out.println(result.getAnimal() +
                        " moved from " + result.getBaseActionLocation().toString() +
                        " to " + result.getEndLocation() +
                        " in " + result.getStepsTaken() + " steps" +
                        " result: " + result.isSuccessful() +
                        "\n" + result.getAnimal().getEnergy() + " "
                        + result.getAnimal().getSatiety());

            }

            if (result.isSuccessful()) {
                totalMoved++;
                totalSteps += result.getStepsTaken();
            }
        }

        for (EatResult result : eatResults) {
            if (result.getActionType() != ActionType.NONE) {
                System.out.println(result.getAnimal() +
                        " ate " + result.getFood() +
                        " in " + result.getBaseActionLocation() +
                        " result " + result.isSuccessful() +
                        "\n" + result.getAnimal().getEnergy() + " "
                        + result.getAnimal().getSatiety());
            }

            if (result.isSuccessful()) {
                totalEaten++;
            }
        }

        for (RestResult result : restResults) {
            if (result.getActionType() != ActionType.NONE) {
                System.out.println(result.getAnimal() +
                        " is " + result.getActionType() +
                        " in: " + result.getBaseActionLocation() +
                        " energy before: " + result.getEnergyBefore() +
                        " energy after: " + result.getEnergyAfter() +
                        " result " + result.isSuccessful() +
                        "\n" + result.getAnimal().getEnergy() + " "
                        + result.getAnimal().getSatiety());
            }

            if (result.isSuccessful()) {
                totalRest++;
            }
        }

        System.out.println("Animals moved: " + totalMoved + "/" + moveResult.size());
        System.out.println("Total steps taken: " + totalSteps);
        System.out.println("Animals or Plants eaten: " + totalEaten);
        System.out.println("Animals rested: " + totalRest + "/" + restResults.size());
        System.out.println();
    }

    public static int getSimulationCycle() {
        return cycle;
    }
}
