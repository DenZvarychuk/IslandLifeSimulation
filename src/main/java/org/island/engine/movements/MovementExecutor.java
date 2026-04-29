package org.island.engine.movements;

import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.statistics.DeathReason;
import org.island.statistics.DeathRecord;
import org.island.statistics.SimulationStatistics;

import java.util.ArrayList;
import java.util.List;

public class MovementExecutor {
    private SimulationStatistics statistics;

    public MovementExecutor(SimulationStatistics statistics){
        this.statistics = statistics;
    }

    public List<MoveResult> calculateMove(Island island) {

        List<Animal> animals = island.getAllAnimals();

        List<MoveResult> moveResults = new ArrayList<>();

        for (Animal animal : animals) {
            if (animal.isExist()) {
                MoveResult result = animal.move(island);
                moveResults.add(result);
            }
        }
        return moveResults;
    }

    public void applyMove(MoveResult result) {
        if (!result.isSuccessful()) {
            return;
        }

        Animal animal = result.getAnimal();

        if (!animal.isExist()) {
            return;
        }

        Location from = result.getStartLocation();
        Location to = result.getEndLocation();

        from.removeEntity(animal);

        animal.setX(to.getX());
        animal.setY(to.getY());
        animal.setEnergy(animal.getEnergy() - animal.getMoveCost());
        animal.setSatiety(animal.getSatiety() - animal.getMoveCost());

        if (!animal.shouldExist()) {
            animal.markAsDead();
            statistics.registerDeath(new DeathRecord(animal, DeathReason.STARVATION));
            return;
        }
        to.addEntity(animal);
    }

}
