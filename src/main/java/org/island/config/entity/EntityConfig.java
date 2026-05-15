package org.island.config.entity;

import org.island.entity.animals.AnimalType;
import org.island.entity.plants.PlantType;

import java.util.Map;

public class EntityConfig {
    private Map<PlantType, PlantConfig> plantConfig;
    private Map<AnimalType, AnimalConfig> animalConfig;
    private EntityConfigDefaults defaults;

    public Map<PlantType, PlantConfig> getPlantConfig() {
        return plantConfig;
    }

    public void setPlantConfig(Map<PlantType, PlantConfig> plantConfig) {
        this.plantConfig = plantConfig;
    }

    public Map<AnimalType, AnimalConfig> getAnimalConfig() {
        return animalConfig;
    }

    public void setAnimalConfig(Map<AnimalType, AnimalConfig> animalConfig) {
        this.animalConfig = animalConfig;
    }

    public EntityConfigDefaults getDefaults() {
        return defaults;
    }

    public void setDefaults(EntityConfigDefaults defaults) {
        this.defaults = defaults;
    }
}