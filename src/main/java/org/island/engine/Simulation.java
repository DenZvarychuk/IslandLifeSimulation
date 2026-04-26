package org.island.engine;

import org.island.engine.movements.MoveResult;
import org.island.engine.movements.MovementExecutor;
import org.island.playground.Island;

import java.util.List;

public class Simulation {

    // TODO move it into config
    private static final int SIMULATION_STEP_COUNT = 10;
    private static int step = 0;
    private MovementExecutor movementExecutor;

    public Simulation() {
        this.movementExecutor = new MovementExecutor();
    }

    public void start(Island island) throws InterruptedException {
        System.out.println("=== Simulation Starting ===\n");

        while (hasSteps()){
            System.out.println("----- Step " + step + "-----");

            List<MoveResult> moveResults = movementExecutor.executeMovementTurn(island);

            printMovementStats(moveResults);

            System.out.println(island.getEntitiesInAllLocByCount());

            step++;
            Thread.sleep(1000);
        }

        System.out.println("\n=== Simulation Complete ===");

    }

    private static boolean hasSteps() {

        return step < SIMULATION_STEP_COUNT;
    }

    private void printMovementStats(List<MoveResult> results) {
        int totalMoved = 0;
        int totalSteps = 0;

        for (MoveResult result : results) {
            if (result.isSuccessful()) {
                totalMoved++;
                totalSteps += result.getStepsTaken();
            }
        }

        System.out.println("Animals moved: " + totalMoved + "/" + results.size());
        System.out.println("Total steps taken: " + totalSteps);
        System.out.println();
    }


}
