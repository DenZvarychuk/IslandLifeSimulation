package org.island.engine.actions;

import org.island.config.action.ActionConfig;
import org.island.engine.SimulationContext;
import org.island.engine.actions.policy.ActionPolicy;
import org.island.engine.actions.policy.DefaultActionPolicy;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

import java.util.ArrayList;
import java.util.List;


public class ActionPicker {

    private final ActionConfig actionConfig;
    private final ActionPolicy defaultPolicy;

    private final NoActionStrategy noActionStrategy;

    public ActionPicker(SimulationContext simulationContext, ActionConfig config) {
        this.actionConfig = config;
        this.defaultPolicy = new DefaultActionPolicy(actionConfig);
        this.noActionStrategy = new NoActionStrategy();

    }

    public List<ActionDecision> pickAction(Island island) {
        List<Animal> animals = island.getAllAnimals();
        List<ActionDecision> decisions = new ArrayList<>();

        for (Animal animal : animals) {
            decisions.add(pick(animal));
        }
        return decisions;
    }

    private ActionDecision pick(Animal animal) {

        if (!animal.isExist()) {
            return new ActionDecision(animal, ActionType.NONE, noActionStrategy);
        }
        return pickActionForLivingAnimal(animal);
    }

    private ActionDecision pickActionForLivingAnimal(Animal animal) {
        ActionPolicy policy = animal.getActionPolicy() != null ? animal.getActionPolicy() : defaultPolicy;

        if (shouldEat(animal)) return new ActionDecision(animal, ActionType.EAT, policy.getEatStrategy(animal));
        if (shouldRest(animal)) return new ActionDecision(animal, ActionType.REST, policy.getRestStrategy(animal));
        if (shouldMove(animal)) return new ActionDecision(animal, ActionType.MOVE, policy.getMoveStrategy(animal));
        return new ActionDecision(animal, ActionType.NONE, noActionStrategy);
    }

    private boolean shouldEat(Animal animal) {
        return animal.isExist()
                && animal.getEnergy() > animal.getFullEnergy() * actionConfig.getEatConfig().getMinEnergyRatio()
                && !animal.isSleeping()
                && animal.getSatiety() <= animal.getMinSatiety();
    }

    private boolean shouldRest(Animal animal) {
        return animal.isExist()
                && !animal.isSleeping()
                && animal.getEnergy() < animal.getFullEnergy() * actionConfig.getRestConfig().getBaseEnergyRatio();
    }

    private boolean shouldMove(Animal animal) {
        return animal.isExist()
                && animal.getEnergy() > animal.getFullEnergy() * actionConfig.getMoveConfig().getMinEnergyRatio()
                && !animal.isSleeping()
                && animal.getMoveSteps() > 0;
    }

}