package org.island.engine.actions.movements;

import org.island.engine.SimulationContext;
import org.island.engine.actions.ActionDecision;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.BaseExecutor;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.playground.Location;
import org.island.statistics.DeathReason;
import org.island.statistics.DeathRecord;

public class MoveExecutor implements BaseExecutor<MoveResult> {
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

        Location from = result.getBaseActionLocation();
        Location to = result.getEndLocation();

        animal.setEnergy(animal.getEnergy() - animal.getActionEnergyCost());
        animal.setSatiety(animal.getSatiety() - animal.getActionSatietyCost());

        if (!animal.shouldExist()) {
            animal.markAsDeadAndRemove(from);
            result.setStatus(ActionResultStatus.FAILED_DIED_IN_PROCESS);
            simulationContext.getStatistics().registerDeath(new DeathRecord(animal, DeathReason.STARVATION));
            return;
        }

        if (!result.isMovedAtSameLocation()) {
            animal.setX(to.getX());
            animal.setY(to.getY());
            from.removeEntity(animal);
            to.addEntity(animal);
        }
    }

}