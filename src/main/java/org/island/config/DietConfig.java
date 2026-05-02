package org.island.config;

import org.island.entity.animals.AnimalType;
import org.island.entity.plants.PlantType;

import java.util.Map;

public class DietConfig {
    private Map<AnimalType, Double> edibleAnimals;
    private Map<PlantType, Double> ediblePlants;

    public Map<AnimalType, Double> getEdibleAnimals() {
        return edibleAnimals;
    }

    public void setEdibleAnimals(Map<AnimalType, Double> edibleAnimals) {
        this.edibleAnimals = edibleAnimals;
    }

    public Map<PlantType, Double> getEdiblePlants() {
        return ediblePlants;
    }

    public void setEdiblePlants(Map<PlantType, Double> ediblePlants) {
        this.ediblePlants = ediblePlants;
    }
}
