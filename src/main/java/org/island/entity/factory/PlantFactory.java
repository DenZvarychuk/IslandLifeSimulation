package org.island.entity.factory;

import org.island.config.PlantConfig;
import org.island.entity.plants.*;
import org.island.playground.Location;

import java.util.Map;

public class PlantFactory {

    private final Map<PlantType, PlantConfig> plantConfig;
    public PlantFactory(Map<PlantType, PlantConfig> plantConfig) {
        this.plantConfig = plantConfig;
    }

    public Plant createPlant(PlantType type, Location location){
        PlantConfig config = plantConfig.get(type);

        // TODO exception
        if (config == null) return null;

        if (isLocationAllowed(config, location)) {
            return switch (type) {
                case TREE -> new Tree(config, location);
                case BUSH -> new Bush(config, location);
                case GRASS -> new Grass(config, location);
                default -> null;
            };
        } else return null;
    }

    private boolean isLocationAllowed(PlantConfig config, Location location) {
        if (config.getAllowedBiomes() == null || config.getAllowedSurfaces() == null)
            return true;

        return config.getAllowedBiomes().contains(location.getBiome()) &&
                config.getAllowedSurfaces().contains(location.getSurface());
    }

}
