package org.island.engine.actions.movements;

import org.island.engine.SimulationContext;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.statistics.DeathReason;
import org.island.statistics.DeathRecord;

import java.util.ArrayList;
import java.util.List;

public class MovementExecutor {
    private SimulationContext simulationContext;

    public MovementExecutor(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public List<MoveResult> move(Island island) {

        List<Animal> animals = island.getAllAnimals();

        List<MoveResult> moveResults = new ArrayList<>();

        for (Animal animal : animals) {
            // TODO move it into action executor
            if (animal.isExist() && animal.getEnergy() > 0) {
                MoveResult result = animal.move(island);
                moveResults.add(result);
            }
        }
        return moveResults;
    }

    public void applyMove(MoveResult result) {
        if (!result.isMoveSuccessful()) {
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
        animal.setEnergy(animal.getEnergy() - animal.getActionCost());
        animal.setSatiety(animal.getSatiety() - animal.getActionCost());

        if (!animal.shouldExist()) {
            animal.markAsDead();
            simulationContext.getStatistics().registerDeath(new DeathRecord(animal, DeathReason.STARVATION));
            return;
        }
        to.addEntity(animal);
    }

}
