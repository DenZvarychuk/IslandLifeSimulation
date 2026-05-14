package org.island.app;

import org.island.config.ConfigLoader;
import org.island.config.SimulationConfig;
import org.island.playground.Island;

public class Application {

    private Island island;
    private SimulationConfig config;

    public void run(){

        config = ConfigLoader.load();

        island = new Island(config);
        System.out.println(island.getIslandMapCLI());
        // TODO generate animals
        // TODO split island to tiles 10x10 or something
        // TODO behaviour tasks on Tiles
    }
}
