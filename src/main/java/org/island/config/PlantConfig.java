package org.island.config;

import org.island.playground.BiomeType;
import org.island.playground.SurfaceType;

import java.util.List;

public class PlantConfig {
    private double weight;
    private int maxOnLocation;
    List<BiomeType> allowedBiomes;
    List<SurfaceType> allowedSurfaces;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getMaxOnLocation() {
        return maxOnLocation;
    }

    public void setMaxOnLocation(int maxOnLocation) {
        this.maxOnLocation = maxOnLocation;
    }

    public List<BiomeType> getAllowedBiomes() {
        return allowedBiomes;
    }

    public void setAllowedBiomes(List<BiomeType> allowedBiomes) {
        this.allowedBiomes = allowedBiomes;
    }

    public List<SurfaceType> getAllowedSurfaces() {
        return allowedSurfaces;
    }

    public void setAllowedSurfaces(List<SurfaceType> allowedSurfaces) {
        this.allowedSurfaces = allowedSurfaces;
    }
}
