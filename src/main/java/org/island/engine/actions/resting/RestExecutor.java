package org.island.engine.actions.resting;

import org.island.engine.SimulationContext;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

import java.util.ArrayList;
import java.util.List;

public class RestExecutor {
    private SimulationContext simulationContext;

    public RestExecutor(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public List<RestResult> rest(Island island) {

        List<Animal> animals = island.getAllAnimals();
        List<RestResult> restResults = new ArrayList<>();

        for (Animal animal : animals) {
            if (animal.isExist()) {
                RestResult result = animal.rest(island);
                restResults.add(result);
            }
        }

        return restResults;
    }

    public void applyRest(RestResult restResult) {
        if (!restResult.isSuccessful()) return;

        Animal animal = restResult.getAnimal();

        if (!animal.isExist()) return;

        animal.setEnergy(restResult.getEnergyAfter());
        // animal.setSatiety(animal.getSatiety() - animal.getActionCost() / 2);

    }

}
