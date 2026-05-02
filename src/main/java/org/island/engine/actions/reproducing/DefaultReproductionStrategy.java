package org.island.engine.actions.reproducing;

/*
public class DefaultReproductionStrategy implements ReproductionStrategy {

    @Override
    public ReproduceResult calculateReproduction(Animal animal, Island island) {
        Location currentLocation = island.getLocation(animal.getX(), animal.getY());

        // Find mate of same species
        Animal mate = currentLocation.getEntities().stream()
                .filter(entity -> entity instanceof Animal)
                .map(entity -> (Animal) entity)
                .filter(other -> !other.equals(animal))
                .filter(other -> other.getType().equals(animal.getType()))
                .findFirst()
                .orElse(null);

        if (mate == null) {
            return new ReproduceResult(animal, null, null, currentLocation, false, 0,
                    "No mate found");
        }

        // Check if both can reproduce (energy/satiety)
        if (!canReproduce(animal) || !canReproduce(mate)) {
            return new ReproduceResult(animal, mate, null, currentLocation, false, 0,
                    "Insufficient energy/satiety to reproduce");
        }

        // Create offspring (will be added in apply phase)
        Animal offspring = createOffspring(animal, mate, currentLocation);
        double energyCost = 15.0;

        return new ReproduceResult(animal, mate, offspring, currentLocation, true,
                energyCost, "Created offspring");
    }

    private boolean canReproduce(Animal animal) {
        return animal.getEnergy() > 30.0
                && animal.getSatiety() > animal.getMaxSatiety() * 0.7;
    }

    private Animal createOffspring(Animal parent1, Animal parent2, Location location) {
        // TODO: Create new animal instance based on parents
        return null;
    }
}

 */
