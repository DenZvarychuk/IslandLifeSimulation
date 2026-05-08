package org.island.engine.actions.policy;

import org.island.config.action.ActionConfig;
import org.island.engine.actions.NoActionStrategy;
import org.island.engine.actions.eating.AnimalConfigEatStrategy;
import org.island.engine.actions.eating.EatStrategy;
import org.island.engine.actions.movements.LandMoveStrategy;
import org.island.engine.actions.movements.MoveStrategy;
import org.island.engine.actions.resting.IdleRestStrategy;
import org.island.engine.actions.resting.RestStrategy;
import org.island.engine.actions.resting.SleepRestStrategy;
import org.island.entity.animals.Animal;

public class DefaultActionPolicy implements ActionPolicy {

    private final ActionConfig actionConfig;
    private final EatStrategy animalConfigEatStrategy;
    private final MoveStrategy landMoveStrategy;
    private final RestStrategy sleepRestStrategy;
    private final RestStrategy idleRestStrategy;
    private final RestStrategy noActionStrategy;

    public DefaultActionPolicy(ActionConfig actionConfig) {
        this.actionConfig = actionConfig;
        this.animalConfigEatStrategy = new AnimalConfigEatStrategy(actionConfig.getEatConfig());
        this.landMoveStrategy = new LandMoveStrategy(actionConfig.getMoveConfig());
        this.sleepRestStrategy = new SleepRestStrategy(actionConfig.getRestConfig());
        this.idleRestStrategy = new IdleRestStrategy(actionConfig.getRestConfig());
        this.noActionStrategy = new NoActionStrategy();
    }

    public EatStrategy getEatStrategy(Animal animal) {
        System.out.println(animal.getId() + " is starving, will try to eat");
        return animalConfigEatStrategy;
    }

    public RestStrategy getRestStrategy(Animal animal) {
        double energy = animal.getEnergy();
        double energyForSleep = animal.getMaxSatiety() * actionConfig.getRestConfig().getSleep().getMinEnergyRatio();
        double energyForIdle = animal.getMaxSatiety() * actionConfig.getRestConfig().getIdle().getMinEnergyRatio();

        if (energy < energyForSleep) {
            System.out.println(animal.getId() + " is exhausted, should sleep");
            return sleepRestStrategy;
        } else if (energy < energyForIdle) {
            System.out.println(animal.getId() + " is tired, should try to rest");
            return idleRestStrategy;
        }
        return noActionStrategy;
    }

    public MoveStrategy getMoveStrategy(Animal animal) {
        // TODO consider to add NoActionStrategy
        System.out.println(animal.getId() + " will move");
        return landMoveStrategy;
    }
}
