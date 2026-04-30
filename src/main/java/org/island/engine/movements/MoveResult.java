package org.island.engine.movements;

import org.island.entity.animals.Animal;
import org.island.playground.Location;

import java.util.ArrayList;
import java.util.List;

public class MoveResult {
    private final Animal animal;
    private final Location startLocation;
    private final Location endLocation;
    private final List<Location> path;
    private final boolean successful;
    private final int stepsTaken;

    public MoveResult(Animal animal, Location start, Location end, int stepsTaken, boolean successful) {
        this.animal = animal;
        this.startLocation = start;
        this.endLocation = end;
        this.stepsTaken = stepsTaken;
        this.successful = successful;
        this.path = new ArrayList<>();
    }

    public void addPathStep(Location location) {path.add(location);}

    public Animal getAnimal() {return animal;}
    public Location getStartLocation() {return startLocation;}
    public Location getEndLocation() {return endLocation;}
    public List<Location> getPath() {return path;}
    public boolean wasSuccessful() {return successful;}
    public int getStepsTaken() {return stepsTaken;}

    public boolean isSuccessful() {
        return !startLocation.equals(endLocation);
    }
}
