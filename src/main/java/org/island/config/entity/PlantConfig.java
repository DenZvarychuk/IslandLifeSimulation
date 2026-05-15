package org.island.config.entity;

import org.island.playground.BiomeType;
import org.island.playground.SurfaceType;

import java.util.List;
import java.util.Map;

public class PlantConfig {
    private double weight;
    private int maxOnLocation;
    private List<BiomeType> allowedBiomes;
    private List<SurfaceType> allowedSurfaces;
    private Map<String, EntityDensity> density;

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

    public Map<String, EntityDensity> getDensity() {
        return density;
    }

    public void setDensity(Map<String, EntityDensity> density) {
        this.density = density;
    }
}

