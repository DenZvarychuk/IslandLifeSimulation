package org.island.entity;

import java.util.UUID;

public abstract class Entity<T extends EntityType> {

    protected int x;
    protected int y;
    protected String id;
    protected T type;
    protected boolean isExist;

    public Entity(T type){
        this.isExist = true;
        this.type = type;
        this.id = type + "-" + UUID.randomUUID().toString();
    }

    public void markAsDead() {
        this.isExist = false;
    }

    @Override
    public String toString() {
        return String.format("{id='%s', x=%d, y=%d} \n", id, x, y);

    }

    public T getEntityType() {
        return type;
    }

    public String getId() {
        return id;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isExist() {
        return isExist;
    }

}
