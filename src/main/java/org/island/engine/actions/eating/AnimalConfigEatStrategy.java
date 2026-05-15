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
                .toList();
    }

    private boolean canEat(Entity entity, DietConfig diet) {
        if (entity instanceof Plant plant) {
            return diet.getEdiblePlants().containsKey(plant.getEntityType());
        } else if (entity instanceof Animal animal) {
            return diet.getEdibleAnimals().containsKey(animal.getEntityType());
        }
        return false;
    }

    private EatResult attemptToEat(Animal animal, List<Entity> availableFood, Location location) {
        Entity<?> food = selectFood(availableFood, animal);

        double possibilityScore = getPossibilityScore(food, animal);
        boolean success = random.nextDouble() <= possibilityScore;

        if (success) {
            System.out.println("animal " + animal.getId() + " will be eating " + food.getId());
        }
        return new EatResult(actionType, animal, food, location, success ? ActionResultStatus.SUCCESS : ActionResultStatus.FAILED_PROBABILITY_CHECK);
    }

    private Entity<?> selectFood(List<Entity> availableFood, Animal animal) {
        List<Entity> sortedFood = availableFood.stream()
                .sorted((e1, e2) -> Double.compare(
                        getPossibilityScore(e2, animal) * getFoodWeight(e2),
                        getPossibilityScore(e1, animal) * getFoodWeight(e1)
                ))
                .toList();

        if (animal.getSatiety() > animal.getSatiety() * config.getRandomFoodSelectionSatietyMultiplier())
            return sortedFood.get(random.nextInt(sortedFood.size()));
        return sortedFood.getFirst();
    }

    private double getPossibilityScore(Entity food, Animal animal) {
        DietConfig diet = animal.getDiet();

        if (food instanceof Plant prey) {
            return diet.getEdiblePlants().getOrDefault(prey.getEntityType(), 0.0);
        } else if (food instanceof Animal prey) {
            return diet.getEdibleAnimals().getOrDefault(prey.getEntityType(), 0.0);
        }
        return 0.0;
    }

    private double getFoodWeight(Entity food) {
        if (food instanceof Animal animal) return animal.getWeight();
        if (food instanceof Plant plant) return plant.getWeight();
        return 0;
    }
}
