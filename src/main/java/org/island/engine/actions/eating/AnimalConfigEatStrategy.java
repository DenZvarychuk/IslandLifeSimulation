package org.island.engine.actions.eating;

import org.island.config.DietConfig;
import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.entity.plants.Plant;
import org.island.playground.Island;
import org.island.playground.Location;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AnimalConfigEatStrategy implements EatStrategy {
    private final Random random = new Random();

    @Override
    public EatResult calculateEat(Animal animal, Island island) {
        int currX = animal.getX();
        int currY = animal.getY();
        Location currentLocation = island.getLocation(currX, currY);

        List<Entity> availableFood = findAvailableFood(animal, currentLocation);

        if (availableFood.isEmpty()) {
            return new EatResult(animal, null, currentLocation, false);
        }

        return attemptToEat(animal, availableFood, currentLocation);
    }

    private List<Entity> findAvailableFood(Animal animal, Location location) {
        DietConfig diet = animal.getDiet();

        return location.getEntities().stream()
                .filter(entity -> canEat(entity, diet))
                .collect(Collectors.toList());
    }

    private boolean canEat(Entity entity, DietConfig diet) {
        if (entity instanceof Plant) {
            Plant plant = (Plant) entity;
            return diet.getEdiblePlants().containsKey(plant.getType());
        } else if (entity instanceof Animal) {
            Animal animal = (Animal) entity;
            return diet.getEdibleAnimals().containsKey(animal.getType());
        }
        return false;
    }

    private EatResult attemptToEat(Animal animal, List<Entity> availableFood, Location location) {
        // TODO make the chose based on satiety and energy to pick the most suitable prey by weight and possibilityScore
        Entity food = availableFood.get(0);

        double possibilityScore = getPossibilityScore(food, animal);
        boolean success = random.nextDouble() <= possibilityScore;

        return new EatResult(animal, food, location, success);
    }

    private double getPossibilityScore(Entity food, Animal animal) {
        DietConfig diet = animal.getDiet();

        if (food instanceof Plant) {
            return diet.getEdiblePlants().getOrDefault(food.getType(), 0.0);
        } else if (food instanceof Animal) {
            return diet.getEdibleAnimals().getOrDefault(food.getType(), 0.0);
        }
        return 0.0;

    }

}
