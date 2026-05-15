package org.island.engine;

import org.island.config.SimulationConfig;
import org.island.engine.actions.ActionExecutor;
import org.island.engine.actions.ActionResult;
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

        try {
            while (hasSimulationCycles()) {
                System.out.println("\n\n----- Step " + cycle + "-----");

                // update animal sleep cycles
                updateAnimalSleepCycles(island);
                // actions
                actionResults = actionExecutor.decideAndCalculate(island);
                actionExecutor.applyActions(actionResults);
                // print stats
                simulationContext.getStatistics().printActionStats(actionResults);
                System.out.println(island.getEntitiesInAllLocByCount());

                // increment cycle
                cycle++;
                Thread.sleep(cycleDelay);
            }
        } finally {
            simulationContext.shutdown();
            System.out.println("Simulation cleanup completed");
        }

        System.out.println("\n=== Simulation Complete ===");

    }

    private void updateAnimalSleepCycles(Island island) {
        List<Animal> animals = island.getAllAnimals();
        for (Animal animal : animals) {
            if (animal.isSleeping()) {
                animal.setSleepCycles(animal.getSleepCycles() - 1);
                if (animal.getSleepCycles() < 0) {
                    System.out.println(animal.getId() + " waked up");
                }
            }
        }
    }

    private static boolean hasSimulationCycles() {
        return cycle < simulationCycleCount;
    }

    public static int getSimulationCycle() {
        return cycle;
    }
}