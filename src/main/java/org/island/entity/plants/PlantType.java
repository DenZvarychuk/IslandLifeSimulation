package org.island.entity.plants;

import org.island.entity.EntityType;

public enum PlantType implements EntityType {
    NULL;

    @Override
    public String toString(){
        return this.name();
    }
}
