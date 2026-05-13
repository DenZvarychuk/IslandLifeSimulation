package org.island.app;

import org.island.config.ConfigLoader;
import org.island.config.SimulationConfig;
import org.island.engine.Simulation;
import org.island.engine.SimulationContext;
import org.island.playground.Island;
import org.island.statistics.EventBus;
import org.island.statistics.SimulationStatistics;

public class Application {

    private Island island;
    private SimulationConfig config;
    private SimulationContext simulationContext;
    private Simulation simulation;


    public void run() {

        loadConfig();
        initializeSimulation();
        createAndPopulateIsland();

        try {
            simulation.start(island);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        simulationContext.getStatistics().printFullReport();

    }

    private void loadConfig() {
        config = ConfigLoader.load();
    }

    private void initializeSimulation() {
        EventBus eventBus = new EventBus();
        SimulationStatistics statistics = new SimulationStatistics(eventBus);
        this.simulationContext = new SimulationContext(eventBus, statistics, config);
        this.simulation = new Simulation(simulationContext, config);
    }

    private void createAndPopulateIsland() {
        island = new Island(config);
        island.generateBiomesAndSurfaces();
        island.generatePlants();
        island.generateAnimals();

        System.out.println(island.getEntitiesInAllLocByCount());
    }
}