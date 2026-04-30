package org.island.app;

import org.island.config.ConfigLoader;
import org.island.config.SimulationConfig;
import org.island.engine.Simulation;
import org.island.engine.movements.MovementExecutor;
import org.island.playground.Island;
import org.island.statistics.SimulationStatistics;

public class Application {

    private Island island;
    private SimulationConfig config;
    private SimulationStatistics statistics = new SimulationStatistics();
    private Simulation simulation = new Simulation(statistics);


    public void run() {

        loadConfig();
        createAndPopulateIsland();

        try {
            simulation.start(island);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        statistics.printFullReport();

        // TODO behaviour tasks on Tiles
    }

    private void loadConfig() {
        config = ConfigLoader.load();
    }

    private void createAndPopulateIsland() {
        island = new Island(config);
        island.generateBiomesAndSurfaces();
        island.generatePlants();
        island.generateAnimals();

        System.out.println(island.getEntitiesInAllLocByCount());
    }
}
