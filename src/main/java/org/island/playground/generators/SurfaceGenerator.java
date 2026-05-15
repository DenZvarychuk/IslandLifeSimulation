package org.island.playground.generators;

import org.island.config.island.IslandConfig;
import org.island.playground.BiomeType;
import org.island.playground.Location;
import org.island.playground.SurfaceType;

public class SurfaceGenerator {
    private double rockPercentage;

    public SurfaceGenerator(IslandConfig islandConfig){
        this.rockPercentage = islandConfig.getSurfaceConfig().getRockPercentage();
    }

    public void generate(Location[][] location, int size, long seed) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location loc = location[x][y];
                double v = RandomGenerator.generate(x, y, seed);

                if (loc.getBiome() == BiomeType.WATER) {
                    loc.setSurface(SurfaceType.WATER);
                    continue;
                }

                if (isNextToWater(x, y, size, location) && loc.getBiome() != BiomeType.FOREST){
                    loc.setSurface(SurfaceType.SAND);
                } else {
                    if (v < rockPercentage) loc.setSurface(SurfaceType.ROCK);
                    else loc.setSurface(SurfaceType.SOIL);
                }

            }
        }
    }

    // TODO check diagonals
    private boolean isNextToWater(int x, int y, int size, Location[][] location) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directions) {
            int nx = x + direction[0];
            int ny = y + direction[1];
            if (nx >= 0 && nx < size &&
                    ny >= 0 && ny < size)
                if (location[nx][ny].getBiome() == BiomeType.WATER)
                    return true;
        }
        return false;
    }
}