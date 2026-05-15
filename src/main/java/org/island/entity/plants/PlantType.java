package org.island.entity.plants;

import org.island.entity.EntityType;

public enum PlantType implements EntityType {
    TREE, GRASS, BUSH;

    @Override
    public String toString() {
        return this.name();
    }
}