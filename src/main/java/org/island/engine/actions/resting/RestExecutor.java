package org.island.engine.actions.resting;

import org.island.config.ActionConfig;
import org.island.engine.SimulationContext;
import org.island.engine.actions.NoActionStrategy;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

import java.util.ArrayList;
import java.util.List;

public class RestExecutor {
    private final double idleRestMinEnergyRatio;
    private final double sleepRestMinEnergyRatio;
    private SimulationContext simulationContext;
    private final NoActionStrategy noActionStrategy = new NoActionStrategy();
    private final IdleRestStrategy idleRestStrategy = new IdleRestStrategy();
    private final SleepRestStrategy sleepRestStrategy = new SleepRestStrategy();

    public RestExecutor(SimulationContext simulationContext, ActionConfig actionConfig) {
        this.idleRestMinEnergyRatio = actionConfig.getIdleRestMinEnergyRatio();
        this.sleepRestMinEnergyRatio = actionConfig.getSleepRestMinEnergyRatio();
        this.simulationContext = simulationContext;
    }

    public List<RestResult> rest(Island island) {

        List<Animal> animals = island.getAllAnimals();
        List<RestResult> restResults = new ArrayList<>();

        for (Animal animal : animals) {
            // TODO move it into action executor
            RestStrategy strategy = pickStrategy(animal);
            RestResult result = strategy.calculateRest(animal, island);
            restResults.add(result);
        }

        return restResults;
    }

    private RestStrategy pickStrategy(Animal animal) {
        if (shouldRest(animal)) {
            double energy = animal.getEnergy();
            double energyForSleep = animal.getMaxSatiety() * sleepRestMinEnergyRatio;
            double energyForIdle = animal.getMaxSatiety() * idleRestMinEnergyRatio;

            if (energy < energyForSleep){
                System.out.println(animal.getId() + " is exhausted");
                return sleepRestStrategy;
            } else if (energy < energyForIdle) {
                System.out.println(animal.getId() + " is tired");
                return idleRestStrategy;
            }
        }
        return noActionStrategy;
    }

    private boolean shouldRest(Animal animal) {
        return animal.isExist()
                && !animal.isSleeping()
                && animal.getEnergy() < animal.getMaxSatiety() * 0.5;
    }

    public void applyRest(RestResult restResult) {
        if (!restResult.isSuccessful()) return;

        Animal animal = restResult.getAnimal();

        if (!animal.isExist()) return;

        animal.setEnergy(restResult.getEnergyAfter());
        // animal.setSatiety(animal.getSatiety() - animal.getActionCost() / 2);

    }

}
