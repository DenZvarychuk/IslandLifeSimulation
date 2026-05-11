package org.island.engine;

import org.island.config.SimulationConfig;
import org.island.engine.actions.ActionExecutor;
import org.island.engine.actions.ActionResult;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.ActionType;
import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.resting.RestResult;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private static int simulationCycleCount;
    private static int cycle = 0;
    private static long cycleDelay;

    List<ActionResult> actionResults = new ArrayList<>();

    private ActionExecutor actionExecutor;
    private SimulationContext simulationContext;

    public Simulation(SimulationContext simulationContext, SimulationConfig config) {
        this.simulationContext = simulationContext;
        this.simulationCycleCount = config.getSimulationCycleCount();
        this.cycleDelay = config.getCycleDelay();

        this.actionExecutor = new ActionExecutor(simulationContext, config.getActionConfig());
    }

    public void start(Island island) throws InterruptedException {
        System.out.println("=== Simulation Starting ===\n");

        while (hasSimulationCycles()) {
            System.out.println("\n\n----- Step " + cycle + "-----");

            // update animal sleep cycles
            updateAnimalSleepCycles(island);
            // actions
            actionResults = actionExecutor.decideAndCalculate(island);
            actionExecutor.applyActions(actionResults);
            // print stats
            printActionStats(actionResults);
            System.out.println(island.getEntitiesInAllLocByCount());

            // increment cycle
            cycle++;
            Thread.sleep(cycleDelay);
        }

        System.out.println("\n=== Simulation Complete ===");

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
                        "\n in " + result.getStepsTaken() + " steps" +
                        "\n status: " + result.getStatus() +
                        "\n energy: " + result.getAnimal().getEnergy() +
                        "\n satiety " + result.getAnimal().getSatiety());

            }

            if (result.getStatus() == ActionResultStatus.SUCCESS) {
                totalMoved++;
                totalSteps += result.getStepsTaken();
            }
        }

        for (EatResult result : eatResults) {
            if (result.getActionType() != ActionType.NONE) {
                System.out.println(result.getAnimal() +
                        " ate " + result.getFood() +
                        "\n in " + result.getBaseActionLocation() +
                        "\n status: " + result.getStatus() +
                        "\n energy: " + result.getAnimal().getEnergy() +
                        "\n satiety: " + result.getAnimal().getSatiety());
            }

            if (result.getStatus() == ActionResultStatus.SUCCESS) {
                totalEaten++;
            }
        }

        for (RestResult result : restResults) {
            if (result.getActionType() != ActionType.NONE) {
                System.out.println(result.getAnimal() +
                        " is " + result.getActionType() +
                        " in: " + result.getBaseActionLocation() +
                        "\n energy before: " + result.getEnergyBefore() +
                        "\n energy after: " + result.getEnergyAfter() +
                        "\n status: " + result.getStatus() +
                        "\n energy: " + result.getAnimal().getEnergy() +
                        "\n satiety:  " + result.getAnimal().getSatiety());
            }

            if (result.getStatus() == ActionResultStatus.SUCCESS) {
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
