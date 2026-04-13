package main.java.org.island.app;

import main.java.org.island.playground.Island;

public class Application {

    private Island island;

    public void run(){
        island = new Island(10, 488567489565351L);
        System.out.println(island.getIslandMapCLI());
        // TODO generate animals
        // TODO split island to tiles 10x10 or something
        // TODO behaviour tasks on Tiles
    }
}
