package org.island.entity.factory;

import org.island.config.AnimalConfig;
import org.island.entity.animals.Animal;
import org.island.entity.animals.AnimalType;
import org.island.entity.animals.Rabbit;
import org.island.entity.animals.Wolf;
import org.island.playground.Location;

import java.util.Map;

public class AnimalFactory {
    private final Map<AnimalType, AnimalConfig> animalConfig;

    public AnimalFactory(Map<AnimalType, AnimalConfig> animalConfig) {
        this.animalConfig = animalConfig;
    }

    public Animal createAnimal(AnimalType type, Location location) {
        AnimalConfig config = animalConfig.get(type);

        //TODO add Exception

        return switch (type) {
            case AnimalType.WOLF -> new Wolf(config, location);
            case AnimalType.RABBIT -> new Rabbit(config, location);
            case null, default -> null;
        };

    }

}
