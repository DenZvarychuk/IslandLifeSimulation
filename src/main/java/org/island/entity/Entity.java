package org.island.entity;

import org.island.entity.animals.AnimalType;

import java.util.UUID;

public abstract class Entity<T extends EntityType> {

    protected int x;
    protected int y;
    protected String id;
    protected String type;
    protected boolean isExist;

    public Entity(T type){
        this.isExist = true;
        this.type = type.toString();
        this.id = type + "-" + UUID.randomUUID().toString();
    }


    public abstract void update();

    @Override
    public String toString() {
        return String.format("%s{id='%s', x=%d, y=%d, exists=%s} \n",
                type, id, x, y, isExist);

    }

    public String getType() {
        return type;
    }
}
