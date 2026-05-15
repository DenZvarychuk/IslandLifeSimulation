package org.island.playground;

import org.island.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Location {
    private final int x;
    private final int y;

    private List<Entity> entities;

    private BiomeType biome;
    private SurfaceType surface;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        this.entities = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        if (entity != null && entity.isExist())
            entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        if (entity != null)
            entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BiomeType getBiome() {
        return biome;
    }

    public void setBiome(BiomeType biome) {
        this.biome = biome;
    }

    public SurfaceType getSurface() {
        return surface;
    }

    public void setSurface(SurfaceType surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return String.format("Location: (%d, %d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}