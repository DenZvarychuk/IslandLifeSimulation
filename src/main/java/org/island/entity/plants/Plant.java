package org.island.entity.plants;

import org.island.entity.Entity;

public abstract class Plant extends Entity {

    private double weight;

    public abstract void grow();
    public abstract void fade();
    public abstract void reproduce();

}
