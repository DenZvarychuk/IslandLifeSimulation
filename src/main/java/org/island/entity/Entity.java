package org.island.entity;

import org.island.playground.Location;

import java.util.UUID;

public abstract class Entity {

    protected String id;
    protected String type;
    protected boolean isExist;
    protected Location location;

    public abstract void update();

}
