package org.island.engine.actions.eating;

import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.entity.plants.Plant;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.statistics.DeathReason;
import org.island.statistics.DeathRecord;
import org.island.statistics.SimulationStatistics;

import java.util.ArrayList;
import java.util.List;

public class EatingExecutor {

    private SimulationStatistics statistics;

    public EatingExecutor(SimulationStatistics statistics) {
        this.statistics = statistics;
    }

    public List<EatResult> eat(Island island) {

        List<Animal> animals = island.getAllAnimals();
        List<EatResult> eatResults = new ArrayList<>();

        for (Animal animal : animals) {
            if (animal.isExist()) {
                EatResult result = animal.eat(island);
                eatResults.add(result);
            }
        }

        return eatResults;
    }

    public void applyEat(EatResult result) {
        if (!result.isEatSuccessfully()) {
            return;
        }

        Animal animal = result.getAnimal();
        Entity food = result.getFood();
        if (!animal.isExist() || !food.isExist()) {
            return;
        }

        Location location = result.getLocation();
        // set energy after trying to eat
        animal.setEnergy(animal.getEnergy() - animal.getActionCost());
        animal.setSatiety(animal.getSatiety() - animal.getActionCost());

        // TODO add statistics
        if (!animal.shouldExist()) {
            animal.markAsDead();
            statistics.registerDeath(new DeathRecord(animal, DeathReason.STARVATION));
            return;
        }
        // remove eaten entity
        food.markAsDead();
        location.removeEntity(food);
        statistics.registerDeath(new DeathRecord(food, DeathReason.EATEN));
        // set satiety
        calculateNewSatiety(animal, food);

    }

    private void calculateNewSatiety(Animal animal, Entity food) {
        double maxSatiety = animal.getMaxSatiety();
        double weight = 0;
        if (food instanceof Animal) {
            weight = ((Animal) food).getWeight();
        }
        if (food instanceof Plant) {
            weight = ((Plant) food).getWeight();
        }

        if (weight > maxSatiety) {
            animal.setSatiety(maxSatiety);
        } else {
            animal.setSatiety(animal.getSatiety() + weight);
        }

        System.out.println(animal + " succesfully ate " + food);

    }

}
