package org.island.engine.actions.resting;

import org.island.entity.animals.Animal;
import org.island.playground.Location;

public class RestResult {
    private final Animal animal;
    private final Location location;
    private final boolean successful;
    private final double energyBefore;
    private final double energyAfter;

    public RestResult(Animal animal, Location location, boolean successful, double energyBefore, double energyAfter) {
        this.animal = animal;
        this.location = location;
        this.successful = true;
        this.energyBefore = energyBefore;
        this.energyAfter = energyAfter;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public double getEnergyBefore() {
        return energyBefore;
    }

    public double getEnergyAfter() {
        return energyAfter;
    }

    public boolean isRestSuccessful(){
        return energyBefore != energyAfter;
    }
}
