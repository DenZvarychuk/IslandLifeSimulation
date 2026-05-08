package org.island.engine.actions.eating;

import org.island.engine.SimulationContext;
import org.island.engine.actions.ActionDecision;
import org.island.engine.actions.ActionExecutor;
import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.entity.plants.Plant;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.statistics.DeathReason;
import org.island.statistics.DeathRecord;

public class EatExecutor implements ActionExecutor<EatResult> {
    private SimulationContext simulationContext;

    public EatExecutor(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public EatResult calculate(ActionDecision decision, Island island) {

        Animal animal = decision.getAnimal();
        EatStrategy strategy = (EatStrategy) decision.getStrategy();

        return strategy.calculateEat(animal, island);
    }


    public void apply(EatResult result) {
        Animal animal = result.getAnimal();
        Entity food = result.getFood();
        Location location = result.getBaseActionLocation();

        if (!animal.isExist()) {
            return;
        }

        if (!result.isSuccessful() || !food.isExist()) {
            animal.setEnergy(animal.getEnergy() - animal.getActionCost());
            animal.setSatiety(animal.getSatiety() - animal.getActionCost());
            if (!animal.shouldExist()) {
                animal.markAsDead();
                simulationContext.getStatistics().registerDeath(new DeathRecord(animal, DeathReason.STARVATION));
            }
            return;
        }

        food.markAsDead();
        location.removeEntity(food);
        simulationContext.getStatistics().registerDeath(new DeathRecord(food, DeathReason.EATEN));
        applyNewEnergyAndSatiety(animal, food);

    }

    private void applyNewEnergyAndSatiety(Animal animal, Entity food) {
        double foodWeight = getFoodWeight(food);
        double newSatiety = Math.min(animal.getMaxSatiety(), animal.getSatiety() - animal.getActionCost() + foodWeight);

        animal.setEnergy(animal.getEnergy() - animal.getActionCost());
        animal.setSatiety(newSatiety);
    }

    private double getFoodWeight(Entity food) {
        if (food instanceof Animal) return ((Animal) food).getWeight();
        if (food instanceof Plant) return ((Plant) food).getWeight();
        return 0;
    }

}