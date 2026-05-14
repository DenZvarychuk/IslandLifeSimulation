package org.island.entity.factory;

import org.island.config.entity.AnimalConfig;
import org.island.config.entity.EntityConfigDefaults;
import org.island.entity.animals.Animal;
import org.island.entity.animals.AnimalType;
import org.island.entity.animals.Rabbit;
import org.island.entity.animals.Wolf;
import org.island.playground.Location;

import java.util.Map;

public class AnimalFactory {
    private final Map<AnimalType, AnimalConfig> animalConfig;
    private final EntityConfigDefaults defaults;

    public AnimalFactory(Map<AnimalType, AnimalConfig> animalConfig, EntityConfigDefaults defaults) {
        this.animalConfig = animalConfig;
        this.defaults = defaults;
    }

    public Animal createAnimal(AnimalType type, Location location) {
        AnimalConfig config = animalConfig.get(type);

        //TODO add Exception

        applyDefaults(config);

        return switch (type) {
            case AnimalType.WOLF -> new Wolf(config, location);
            case AnimalType.RABBIT -> new Rabbit(config, location);
            case null, default -> null;
        };

    }

    private void applyDefaults(AnimalConfig config) {
        if (config.getBaseEnergy() == null) {
            config.setBaseEnergy(defaults.getBaseEnergy());
        }
        if (config.getActionEnergyCostRatio() == null) {
            config.setActionEnergyCostRatio(defaults.getActionEnergyCostRatio());
        }
        if (config.getActionSatietyCostRatio() == null) {
            config.setActionSatietyCostRatio(defaults.getActionSatietyCostRatio());
        }
        if (config.getMinSatietyRatio() == null) {
            config.setMinSatietyRatio(defaults.getMinSatietyRatio());
        }
        if (config.getMaxSatiety() <= 0) {
            throw new IllegalArgumentException("maxSatiety must be > 0 for animal config");
        }
    }

}
