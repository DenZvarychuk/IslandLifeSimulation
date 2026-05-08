package org.island.engine.actions.movements;

import org.island.engine.SimulationContext;
import org.island.engine.actions.ActionDecision;
import org.island.engine.actions.ActionExecutor;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.statistics.DeathReason;
import org.island.statistics.DeathRecord;

public class MoveExecutor implements ActionExecutor<MoveResult> {
    private SimulationContext simulationContext;

    public MoveExecutor(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public MoveResult calculate(ActionDecision decision, Island island) {

        Animal animal = decision.getAnimal();
        MoveStrategy strategy = (MoveStrategy) decision.getStrategy();

        return strategy.calculateMove(animal, island);
    }

    public void apply(MoveResult result) {

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