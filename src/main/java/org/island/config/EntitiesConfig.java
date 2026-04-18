package org.island.config;

import java.util.Map;

public class EntitiesConfig {

    private Map<String, PlantConfig> plantConfig;
    private Map<String, AnimalConfig> animalConfig;

    public Map<String, PlantConfig> getPlantConfig() {
        return plantConfig;
    }

    public void setPlantConfig(Map<String, PlantConfig> plantConfig) {
        this.plantConfig = plantConfig;
    }

    public Map<String, AnimalConfig> getAnimalConfig() {
        return animalConfig;
    }

    public void setAnimalConfig(Map<String, AnimalConfig> animalConfig) {
        this.animalConfig = animalConfig;
    }
}

