package org.island.engine.actions;

import org.island.config.ActionConfig;
import org.island.engine.SimulationContext;
import org.island.engine.actions.eating.AnimalConfigEatStrategy;
import org.island.engine.actions.eating.EatStrategy;
import org.island.engine.actions.movements.LandMoveStrategy;
import org.island.engine.actions.movements.MovementStrategy;
import org.island.engine.actions.resting.IdleRestStrategy;
import org.island.engine.actions.resting.RestStrategy;
import org.island.engine.actions.resting.SleepRestStrategy;
import org.island.entity.animals.Animal;
import org.island.playground.Island;

import java.util.ArrayList;
import java.util.List;


public class ActionPicker {

    private final ActionConfig actionConfig;

    private final NoActionStrategy noActionStrategy;
    private final IdleRestStrategy idleRestStrategy;
    private final SleepRestStrategy sleepRestStrategy;
    private final AnimalConfigEatStrategy animalConfigEatStrategy;
    private final LandMoveStrategy landMoveStrategy;

    public ActionPicker(SimulationContext simulationContext, ActionConfig config) {
        this.actionConfig = config;
        // initialize strategies;
        this.noActionStrategy = new NoActionStrategy();
        this.idleRestStrategy = new IdleRestStrategy(actionConfig);
        this.sleepRestStrategy = new SleepRestStrategy(actionConfig);
        this.animalConfigEatStrategy = new AnimalConfigEatStrategy(actionConfig);
        this.landMoveStrategy = new LandMoveStrategy(actionConfig);
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
        if (shouldEat(animal))
            return new ActionDecision(animal, ActionType.EAT, pickEatStrategy(animal));

        if (shouldRest(animal))
            return new ActionDecision(animal, ActionType.REST, pickRestStrategy(animal));

        if (shouldMove(animal))
            return new ActionDecision(animal, ActionType.MOVE, pickMoveStrategy(animal));

        return new ActionDecision(animal, ActionType.NONE, noActionStrategy);
    }

    private boolean shouldEat(Animal animal) {
        return animal.isExist()
                && animal.getEnergy() > animal.getMaxSatiety() * actionConfig.getEatMinEnergyRatio()
                && !animal.isSleeping()
                && animal.getSatiety() <= animal.getMinSatiety();
    }

    private EatStrategy pickEatStrategy(Animal animal) {
        System.out.println(animal.getId() + " is starving, will try to eat");
        return animal.getEatStrategy() == null ? animalConfigEatStrategy : animal.getEatStrategy();
    }

    private boolean shouldRest(Animal animal) {
        return animal.isExist()
                && !animal.isSleeping()
                && animal.getEnergy() < animal.getMaxSatiety() * actionConfig.getBaseRestMinEnergyRatio();
    }

    private RestStrategy pickRestStrategy(Animal animal) {
        double energy = animal.getEnergy();
        double energyForSleep = animal.getMaxSatiety() * actionConfig.getSleepRestMinEnergyRatio();
        double energyForIdle = animal.getMaxSatiety() * actionConfig.getIdleRestMinEnergyRatio();

        if (energy < energyForSleep) {
            System.out.println(animal.getId() + " is exhausted, should sleep");
            return sleepRestStrategy;
        } else if (energy < energyForIdle) {
            System.out.println(animal.getId() + " is tired, should try to rest");
            return idleRestStrategy;
        }
        return noActionStrategy;
    }

    private boolean shouldMove(Animal animal) {
        double minEnergyToMove = animal.getMaxSatiety() * actionConfig.getMovementMinEnergyRatio();
        return animal.isExist()
                && animal.getEnergy() > minEnergyToMove
                && !animal.isSleeping()
                && animal.getMoveSteps() > 0;
    }

    private MovementStrategy pickMoveStrategy(Animal animal) {
        // TODO consider to add NoActionStrategy
        System.out.println(animal.getId() + " will move");
        return animal.getMovementStrategy() == null ? landMoveStrategy : animal.getMovementStrategy();
    }

}