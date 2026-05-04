package org.island.engine;

import org.island.config.SimulationConfig;
import org.island.engine.actions.ActionType;
import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.eating.EatingExecutor;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.movements.MovementExecutor;
import org.island.engine.actions.resting.RestExecutor;
import org.island.engine.actions.resting.RestResult;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

import java.util.List;

public class Simulation {

    // TODO move it into config
    private static int simulationCycleCount;
    private static int cycle = 0;

    private MovementExecutor movementExecutor;
    private EatingExecutor eatingExecutor;
    private RestExecutor restExecutor;
    private SimulationContext simulationContext;

    public Simulation(SimulationContext simulationContext, SimulationConfig config) {
        this.simulationContext = simulationContext;
        this.simulationCycleCount = config.getSimulationCycleCount();
        // TODO move Executors to Factory
        this.movementExecutor = new MovementExecutor(simulationContext, config.getActionConfig());
        this.eatingExecutor = new EatingExecutor(simulationContext);
        this.restExecutor = new RestExecutor(simulationContext, config.getActionConfig());
    }

    public void start(Island island) throws InterruptedException {
        System.out.println("=== Simulation Starting ===\n");

        while (hasSimulationCycles()) {
            System.out.println("\n\n----- Step " + cycle + "-----");

            // actions
            // moving
            List<MoveResult> moveResults = movementExecutor.move(island);
            // apply move


            for (MoveResult result : moveResults) {
                movementExecutor.applyMove(result);

                if (result.getActionType() != ActionType.NONE) {
                    System.out.println(result.getAnimal() +
                            " moved from " + result.getBaseActionLocation().toString() +
                            " to " + result.getEndLocation() +
                            " in " + result.getStepsTaken() + " steps" +
                            " result: " + result.isSuccessful() +
                            "\n" + result.getAnimal().getEnergy() + " "
                            + result.getAnimal().getSatiety());

                }
            }

            // eating
            List<EatResult> eatResults = eatingExecutor.eat(island);
            // apply eat
            for (EatResult result : eatResults) {
                eatingExecutor.applyEat(result);

                if (result.getActionType() != ActionType.NONE) {
                    System.out.println(result.getAnimal() +
                            " ate " + result.getFood() +
                            " in " + result.getBaseActionLocation() +
                            " result " + result.isSuccessful() +
                            "\n" + result.getAnimal().getEnergy() + " "
                            + result.getAnimal().getSatiety());
                }
            }


            // resting
            List<RestResult> restResults = restExecutor.rest(island);

            // apply rest
            for (RestResult result : restResults) {
                restExecutor.applyRest(result);

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
            }


            // stats
            printActionStats(moveResults, eatResults, restResults);
            System.out.println(island.getEntitiesInAllLocByCount());

            // make animal not sleeping
            List<Animal> animals = island.getAllAnimals();
            for (Animal animal : animals) {
                if (animal.isSleeping()) {
                    animal.setSleepCycles(animal.getSleepCycles() - 1);
                    if (animal.getSleepCycles() == 0) System.out.println(animal.getId() + " waked up");
                }
            }

            // increment
            cycle++;
            Thread.sleep(1000);
        }

        System.out.println("\n=== Simulation Complete ===");

    }

    private static boolean hasSimulationCycles() {
        return cycle < simulationCycleCount;
    }

    private void printActionStats(List<MoveResult> results, List<EatResult> eatResults, List<RestResult> restResults) {
        int totalMoved = 0;
        int totalSteps = 0;
        int totalEaten = 0;
        int totalRest = 0;

        for (MoveResult result : results) {
            if (result.isSuccessful()) {
                totalMoved++;
                totalSteps += result.getStepsTaken();
            }
        }

        for (EatResult result : eatResults) {
            if (result.isSuccessful()) {
                totalEaten++;
            }
        }

        for (RestResult result : restResults) {
            if (result.isSuccessful()) {
                totalRest++;
            }
        }

        System.out.println("Animals moved: " + totalMoved + "/" + results.size());
        System.out.println("Total steps taken: " + totalSteps);
        System.out.println("Animals or Plants eaten: " + totalEaten);
        System.out.println("Animals rested: " + totalRest + "/" + restResults.size());
        System.out.println();
    }

    public static int getSimulationCycle() {
        return cycle;
    }
}
