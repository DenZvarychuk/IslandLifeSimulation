package org.island.engine;

import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.eating.EatingExecutor;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.movements.MovementExecutor;
import org.island.playground.Island;
import org.island.statistics.SimulationStatistics;

import java.util.List;

public class Simulation {

    // TODO move it into config
    private static final int SIMULATION_CYCLE_COUNT = 30;
    private static int cycle = 0;
    private MovementExecutor movementExecutor;
    private EatingExecutor eatingExecutor;
    private SimulationStatistics statistics;

    public Simulation(SimulationStatistics statistics) {
        this.statistics = statistics;
        this.movementExecutor = new MovementExecutor(statistics);
        this.eatingExecutor = new EatingExecutor(statistics);
    }

    public void start(Island island) throws InterruptedException {
        System.out.println("=== Simulation Starting ===\n");

        while (hasSteps()){
            System.out.println("----- Step " + cycle + "-----");

            // actions
            List<MoveResult> moveResults = movementExecutor.move(island);
            List<EatResult> eatResults = eatingExecutor.eat(island);

            for (MoveResult result : moveResults) {
                movementExecutor.applyMove(result);
                System.out.println(result.getAnimal() +
                        " moved from " + result.getStartLocation().toString() +
                        " to " + result.getEndLocation() +
                        " in " + result.getStepsTaken() + " steps");
            }

            for (EatResult result : eatResults) {
                eatingExecutor.applyEat(result);
                System.out.println(result.getAnimal() +
                        " ate " + result.getFood() +
                        " in " + result.getLocation());
            }


            // stats
            printActionStats(moveResults, eatResults);
            System.out.println(island.getEntitiesInAllLocByCount());

            // increment
            cycle++;
            Thread.sleep(1000);
        }

        System.out.println("\n=== Simulation Complete ===");

    }

    private static boolean hasSteps() {

        return cycle < SIMULATION_CYCLE_COUNT;
    }

    private void printActionStats(List<MoveResult> results, List<EatResult> eatResults) {
        int totalMoved = 0;
        int totalSteps = 0;
        int totalEaten = 0;

        for (MoveResult result : results) {
            if (result.isMoveSuccessful()) {
                totalMoved++;
                totalSteps += result.getStepsTaken();
            }
        }

        for (EatResult result : eatResults) {
            if (result.isEatSuccessfully()) {
                totalEaten++;
            }
        }

        System.out.println("Animals moved: " + totalMoved + "/" + results.size());
        System.out.println("Total steps taken: " + totalSteps);
        System.out.println("Animals or Plants eaten: " + totalEaten);
        System.out.println();
    }

    public static int getSimulationCycle() {
        return cycle;
    }
}
