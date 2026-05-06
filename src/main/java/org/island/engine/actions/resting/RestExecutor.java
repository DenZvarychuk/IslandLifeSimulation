package org.island.engine.actions.resting;

import org.island.engine.SimulationContext;
import org.island.engine.actions.ActionDecision;
import org.island.engine.actions.ActionExecutor;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

import java.util.ArrayList;
import java.util.List;

public class RestExecutor implements ActionExecutor<RestResult> {
    private SimulationContext simulationContext;

    public RestExecutor(SimulationContext simulationContext) {

        this.simulationContext = simulationContext;
    }

    public RestResult calculate(ActionDecision decision, Island island) {

        Animal animal = decision.getAnimal();
        RestStrategy strategy = (RestStrategy) decision.getStrategy();
        return strategy.calculateRest(animal, island);
    }

    public void apply(RestResult restResult) {
        if (!restResult.isSuccessful()) return;

        Animal animal = restResult.getAnimal();

        if (!animal.isExist()) return;

        animal.setEnergy(restResult.getEnergyAfter());
        // animal.setSatiety(animal.getSatiety() - animal.getActionCost() / 2);

    }

}
