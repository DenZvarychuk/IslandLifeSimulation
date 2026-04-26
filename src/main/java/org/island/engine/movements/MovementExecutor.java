package org.island.engine.movements;

import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;

import java.util.ArrayList;
import java.util.List;

public class MovementExecutor {

    public List<MoveResult> executeMovementTurn(Island island) {

        List<Animal> animals = getAllAnimals(island);

        List<MoveResult> moveResults = new ArrayList<>();

        for (Animal animal : animals) {
            MoveResult result = animal.calculateMove(island);
            moveResults.add(result);
        }

        for (MoveResult result : moveResults) {
            applyMove(result);
        }
        return moveResults;
    }

    private void applyMove(MoveResult result){
        if (!result.isSuccessful()) {
            return;
        }

        Animal animal = result.getAnimal();
        Location from = result.getStartLocation();
        Location to = result.getEndLocation();

        from.removeEntity(animal);

        animal.setX(to.getX());
        animal.setY(to.getY());

        to.addEntity(animal);
    }

    private List<Animal> getAllAnimals(Island island) {
        List<Animal> animals = new ArrayList<>();

        // Iterate through all locations
        for (int x = 0; x < island.getSize(); x++) {
            for (int y = 0; y < island.getSize(); y++) {
                Location location = island.getLocation(x, y);

                // Filter only animals
                for (Entity entity : location.getEntities()) {
                    if (entity instanceof Animal) {
                        animals.add((Animal) entity);
                    }
                }
            }
        }

        return animals;
    }

}
