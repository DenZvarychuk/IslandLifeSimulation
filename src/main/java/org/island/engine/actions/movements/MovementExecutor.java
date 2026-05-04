package org.island.engine.actions.movements;

import org.island.config.ActionConfig;
import org.island.engine.SimulationContext;
import org.island.engine.actions.NoActionStrategy;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.statistics.DeathReason;
import org.island.statistics.DeathRecord;

import java.util.ArrayList;
import java.util.List;

public class MovementExecutor {
    private final double movementMinEnergyRatio;
    private SimulationContext simulationContext;
    private final NoActionStrategy noActionStrategy = new NoActionStrategy();

    public MovementExecutor(SimulationContext simulationContext, ActionConfig actionConfig) {
        this.movementMinEnergyRatio = actionConfig.getMovementMinEnergyRatio();
        this.simulationContext = simulationContext;
    }

    public List<MoveResult> move(Island island) {

        List<Animal> animals = island.getAllAnimals();
        List<MoveResult> moveResults = new ArrayList<>();

        for (Animal animal : animals) {
            // TODO move it into action executor
            MovementStrategy strategy = pickStrategy(animal);
            MoveResult result = strategy.calculateMove(animal, island);
            moveResults.add(result);
        }
        return moveResults;
    }

    private MovementStrategy pickStrategy(Animal animal) {
        if (shouldMove(animal)) {
            System.out.println(animal.getId() + " is moving");
            System.out.println(animal.getEnergy() + " " + animal.getSatiety());
            return animal.getMovementStrategy();
        }
        return noActionStrategy;
    }

    private boolean shouldMove(Animal animal) {
        double minEnergyToMove = animal.getMaxSatiety() * movementMinEnergyRatio;
        return animal.isExist()
                && animal.getEnergy() > minEnergyToMove
                && !animal.isSleeping()
                && animal.getMoveSteps() > 0
                && animal.getMovementStrategy() != null;
    }

    public void applyMove(MoveResult result) {

        Animal animal = result.getAnimal();

        if (!animal.isExist() || !result.isSuccessful()) {
            return;
        }

        Location from = result.getBaseActionLocation();
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