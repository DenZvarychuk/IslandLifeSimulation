package org.island.engine;

import org.island.engine.movements.MoveResult;
import org.island.engine.movements.MovementExecutor;
import org.island.playground.Island;
import org.island.statistics.SimulationStatistics;

import java.util.List;

public class Simulation {

    // TODO move it into config
    private static final int SIMULATION_CYCLE_COUNT = 20;
    private static int cycle = 0;
    private MovementExecutor movementExecutor;
    private SimulationStatistics statistics;

    public Simulation(SimulationStatistics statistics) {
        this.statistics = statistics;
        this.movementExecutor = new MovementExecutor(statistics);
    }

    public void start(Island island) throws InterruptedException {
        System.out.println("=== Simulation Starting ===\n");

        while (hasSteps()){
            System.out.println("----- Step " + cycle + "-----");

            // actions
            List<MoveResult> moveResults = movementExecutor.calculateMove(island);

            for (MoveResult result : moveResults) {
                movementExecutor.applyMove(result);
            }


            // stats
            printMovementStats(moveResults);
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

    private void printMovementStats(List<MoveResult> results) {
        int totalMoved = 0;
        int totalSteps = 0;

        for (MoveResult result : results) {
            if (result.isMoveSuccessful()) {
                totalMoved++;
                totalSteps += result.getStepsTaken();
            }
        }

        System.out.println("Animals moved: " + totalMoved + "/" + results.size());
        System.out.println("Total steps taken: " + totalSteps);
        System.out.println();
    }

    public static int getSimulationCycle() {
        return cycle;
    }
}
