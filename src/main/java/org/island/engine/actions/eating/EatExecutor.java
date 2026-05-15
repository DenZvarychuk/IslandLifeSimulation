package org.island.engine.actions.eating;

import org.island.engine.SimulationContext;
import org.island.engine.actions.ActionDecision;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.BaseExecutor;
import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.entity.plants.Plant;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.statistics.DeathReason;
import org.island.statistics.DeathRecord;

public class EatExecutor implements BaseExecutor<EatResult> {
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

        if (food == null) {
            result.setStatus(ActionResultStatus.FAILED_NO_TARGET_FOUND);
            return;
        }

        if (!food.isAtSameLocation(animal)) {
            result.setStatus(ActionResultStatus.FAILED_TARGET_GONE);
            return;
        }

        if (result.getStatus() == ActionResultStatus.FAILED_PROBABILITY_CHECK) {
            animal.setEnergy(animal.getEnergy() - animal.getActionEnergyCost());
            animal.setSatiety(animal.getSatiety() - animal.getActionSatietyCost());
            if (!animal.shouldExist()) {
                animal.markAsDeadAndRemove(location);
                result.setStatus(ActionResultStatus.FAILED_DIED_IN_PROCESS);
                simulationContext.getStatistics().registerDeath(new DeathRecord(animal, DeathReason.STARVATION));
            }
            return;
        }

        food.markAsDeadAndRemove(location);
        simulationContext.getStatistics().registerDeath(new DeathRecord(food, DeathReason.EATEN));
        applyNewEnergyAndSatiety(animal, food);

    }

    private void applyNewEnergyAndSatiety(Animal animal, Entity food) {
        double foodWeight = getFoodWeight(food);
        double newSatiety = Math.min(animal.getMaxSatiety(), animal.getSatiety() - animal.getActionSatietyCost() + foodWeight);

        animal.setEnergy(animal.getEnergy() - animal.getActionEnergyCost());
        animal.setSatiety(newSatiety);
    }

    private double getFoodWeight(Entity food) {
        if (food instanceof Animal animal) return animal.getWeight();
        if (food instanceof Plant plant) return plant.getWeight();
        return 0;
    }

}
