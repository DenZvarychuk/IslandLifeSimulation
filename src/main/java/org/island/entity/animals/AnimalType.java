package org.island.entity.animals;

import org.island.entity.EntityType;

public enum AnimalType implements EntityType {
    WOLF, RABBIT;

    @Override
    public String toString(){
        return this.name();
    }
}