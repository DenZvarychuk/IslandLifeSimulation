package org.island.engine;

import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.eating.EatingExecutor;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.movements.MovementExecutor;
import org.island.engine.actions.resting.RestExecutor;
import org.island.engine.actions.resting.RestResult;
import org.island.playground.Island;
import org.island.statistics.SimulationStatistics;

import java.util.List;

public class Simulation {

    // TODO move it into config
    private static final int SIMULATION_CYCLE_COUNT = 30;
    private static int cycle = 0;

    private MovementExecutor movementExecutor;
    private EatingExecutor eatingExecutor;
    private RestExecutor restExecutor;
    private SimulationStatistics statistics;

    public Simulation(SimulationStatistics statistics) {
        this.statistics = statistics;
        this.movementExecutor = new MovementExecutor(statistics);
        this.eatingExecutor = new EatingExecutor(statistics);
        this.restExecutor = new RestExecutor(statistics);
    }

    public void start(Island island) throws InterruptedException {
        System.out.println("=== Simulation Starting ===\n");

        while (hasSimulationCycles()) {
            System.out.println("----- Step " + cycle + "-----");

            // actions
            // moving
            List<MoveResult> moveResults = movementExecutor.move(island);
            // apply move


            for (MoveResult result : moveResults) {
                movementExecutor.applyMove(result);
                System.out.println(result.getAnimal() +
                        " moved from " + result.getStartLocation().toString() +
                        " to " + result.getEndLocation() +
                        " in " + result.getStepsTaken() + " steps");
            }


            // eating
            List<EatResult> eatResults = eatingExecutor.eat(island);
            // apply eat
            for (EatResult result : eatResults) {
                eatingExecutor.applyEat(result);
                System.out.println(result.getAnimal() +
                        " ate " + result.getFood() +
                        " in " + result.getLocation());
            }


            // resting
            List<RestResult> restResults = restExecutor.rest(island);

            // apply rest
            for (RestResult result : restResults) {
                restExecutor.applyRest(result);
                System.out.println(result.getAnimal() +
                        " rested in: " + result.getLocation() +
                        " energy before: " + result.getEnergyBefore() +
                        " energy after: " + result.getEnergyAfter());
            }


            // stats
            printActionStats(moveResults, eatResults, restResults);
            System.out.println(island.getEntitiesInAllLocByCount());

            // increment
            cycle++;
            Thread.sleep(1000);
        }

        System.out.println("\n=== Simulation Complete ===");

    }

    private static boolean hasSimulationCycles() {
        return cycle < SIMULATION_CYCLE_COUNT;
    }

    private void printActionStats(List<MoveResult> results, List<EatResult> eatResults, List<RestResult> restResults) {
        int totalMoved = 0;
        int totalSteps = 0;
        int totalEaten = 0;
        int totalRest = 0;

        for (MoveResult result : results) {
            if (result.isMoveSuccessful()) {
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
            if (result.isRestSuccessful()) {
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
