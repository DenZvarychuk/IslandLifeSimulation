package org.island.engine.actions.eating;

import org.island.config.action.EatConfig;
import org.island.config.entity.DietConfig;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.ActionType;
import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.entity.plants.Plant;
import org.island.playground.Island;
import org.island.playground.Location;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AnimalConfigEatStrategy implements EatStrategy {
    private final EatConfig config;
    private final ActionType actionType = ActionType.EAT;
    private final Random random = new Random();

    public AnimalConfigEatStrategy(EatConfig config) {
        this.config = config;
    }

    @Override
    public EatResult calculateEat(Animal animal, Island island) {
        Location currentLocation = getCurrentLocation(animal, island);
        List<Entity> availableFood = findAvailableFood(animal, currentLocation);

        if (availableFood.isEmpty()) {
            return new EatResult(actionType, animal, null, currentLocation, ActionResultStatus.FAILED_NO_TARGET_FOUND);
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
            return diet.getEdiblePlants().containsKey(plant.getEntityType());
        } else if (entity instanceof Animal) {
            Animal animal = (Animal) entity;
            return diet.getEdibleAnimals().containsKey(animal.getEntityType());
        }
        return false;
    }

    private EatResult attemptToEat(Animal animal, List<Entity> availableFood, Location location) {
        // TODO make the chose based on satiety and energy to pick the most suitable prey by weight and possibilityScore
        Entity food = availableFood.get(0);

        double possibilityScore = getPossibilityScore(food, animal);
        boolean success = random.nextDouble() <= possibilityScore;

        if (success) {
            System.out.println("animal " + animal.getId() + " will be eating " + food.getId());
        }
        return new EatResult(actionType, animal, food, location, success ? ActionResultStatus.SUCCESS : ActionResultStatus.FAILED_PROBABILITY_CHECK);
    }

    private double getPossibilityScore(Entity food, Animal animal) {
        DietConfig diet = animal.getDiet();

        if (food instanceof Plant) {
            return diet.getEdiblePlants().getOrDefault(food.getEntityType(), 0.0);
        } else if (food instanceof Animal) {
            return diet.getEdibleAnimals().getOrDefault(food.getEntityType(), 0.0);
        }
        return 0.0;

    }

}