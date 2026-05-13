package org.island.engine.actions;

import org.island.config.action.ActionConfig;
import org.island.engine.SimulationContext;
import org.island.engine.actions.policy.ActionPolicy;
import org.island.engine.actions.policy.DefaultActionPolicy;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class ActionPicker {

    private final ActionConfig actionConfig;
    private final ActionPolicy defaultPolicy;
    private final NoActionStrategy noActionStrategy;
    private final ExecutorService executorService;

    public ActionPicker(SimulationContext simulationContext, ActionConfig config) {
        this.actionConfig = config;
        this.defaultPolicy = new DefaultActionPolicy(actionConfig);
        this.noActionStrategy = new NoActionStrategy();
        this.executorService = simulationContext.getExecutorService();
    }

    public List<ActionDecision> pickAction(Island island) {
        List<Animal> animals = island.getAllAnimals();
        List<Future<ActionDecision>> futures = new ArrayList<>(animals.size());

        for (Animal animal : animals) {
            futures.add(executorService.submit(() -> pick(animal)));
        }

        List<ActionDecision> decisions = new ArrayList<>(animals.size());

        // for(Future<ActionDecision> future : futures)
        for (int i = 0; i < futures.size(); i++) {
            try {
                decisions.add(futures.get(i).get());
            } catch (ExecutionException e) {
                System.err.println("Error while picking action for animal: " + e.getMessage());
                decisions.add(new ActionDecision(animals.get(i), ActionType.NONE, noActionStrategy));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Action picking interrupted for animal " + animals.get(i).getId());
                decisions.add(new ActionDecision(animals.get(i), ActionType.NONE, noActionStrategy));
            }
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