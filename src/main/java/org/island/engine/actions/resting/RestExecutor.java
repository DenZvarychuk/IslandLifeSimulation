package org.island.engine.actions.resting;

import org.island.engine.SimulationContext;
import org.island.engine.actions.ActionDecision;
import org.island.engine.actions.BaseExecutor;
import org.island.entity.animals.Animal;
import org.island.playground.Island;
import org.island.statistics.DeathReason;
import org.island.statistics.DeathRecord;

public class RestExecutor implements BaseExecutor<RestResult> {
    private SimulationContext simulationContext;

    public RestExecutor(SimulationContext simulationContext) {

        this.simulationContext = simulationContext;
    }

    public RestResult calculate(ActionDecision decision, Island island) {

        Animal animal = decision.getAnimal();
        RestStrategy strategy = (RestStrategy) decision.getStrategy();
        return strategy.calculateRest(animal, island);
    }

    public void apply(RestResult result) {
        if (!result.isSuccessful()) return;

        Animal animal = result.getAnimal();

        if (!animal.isExist()) {
            result.setFailed(true);
            return;
        }

        animal.setEnergy(result.getEnergyAfter());
        animal.setSatiety(animal.getSatiety() - animal.getActionSatietyCost()  / 2);

        if (!animal.shouldExist()) {
            animal.markAsDeadAndRemove(result.getBaseActionLocation());
            System.out.println("dead while resting?");
            simulationContext.getStatistics().registerDeath(new DeathRecord(animal, DeathReason.STARVATION));
            return;
        }

    }

}
