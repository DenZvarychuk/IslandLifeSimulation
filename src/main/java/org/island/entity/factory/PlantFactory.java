package org.island.entity.factory;

import org.island.config.entity.EntityDensity;
import org.island.config.entity.PlantConfig;
import org.island.entity.plants.*;
import org.island.playground.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PlantFactory {

    private final Map<PlantType, PlantConfig> plantConfig;
    private final Random random;

    public PlantFactory(Map<PlantType, PlantConfig> plantConfig, long seed) {
        this.plantConfig = plantConfig;
        this.random = new Random(seed);
    }

    public void createPlant(PlantType type, Location location) {
        List<Plant> plants = new ArrayList<>();
        PlantConfig config = plantConfig.get(type);

        // TODO exception

        if (isLocationAllowed(config, location)) {

            int countPerLocation = calculateCount(config, location);

            for (int i = 0; i < countPerLocation; i++) {
                Plant plant = switch (type) {
                    case TREE -> new Tree(config, location);
                    case BUSH -> new Bush(config, location);
                    case GRASS -> new Grass(config, location);
                    default -> null;
                };
                if (plant != null) plants.add(plant);
            }
        }
    }

    private boolean isLocationAllowed(PlantConfig config, Location location) {
        if (config.getAllowedBiomes() == null || config.getAllowedSurfaces() == null)
            return true;

        return config.getAllowedBiomes().contains(location.getBiome()) &&
                config.getAllowedSurfaces().contains(location.getSurface());
    }

    private int calculateCount(PlantConfig config, Location location) {

        String key = location.getBiome().name() + "_" + location.getSurface().name();

        Map<String, EntityDensity> densityMap = config.getDensity();
        if (densityMap != null && densityMap.containsKey(key)) {
            EntityDensity density = densityMap.get(key);
            return calculateCountFromDensity(config.getMaxOnLocation(), density);
        }

        // Fallback: use MEDIUM density if not specified
        return calculateCountFromDensity(config.getMaxOnLocation(), EntityDensity.MEDIUM);
    }

    private int calculateCountFromDensity(int maxOnLocation, EntityDensity entityDensity) {
        double min = maxOnLocation * entityDensity.getMinMultiplier();
        double max = maxOnLocation * entityDensity.getMaxMultiplier();
        return (int) (min + random.nextDouble() * (max - min));
    }

}