package main.java.org.island.playground;

public class SurfaceGenerator {
    public void generate(Location[][] location, int size, long seed) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location loc = location[x][y];
                double v = Randome.generate(x, y, seed);

                if (loc.getBiome() == BiomeType.WATER) {
                    loc.setSurface(SurfaceType.WATER);
                    continue;
                }

                if (isNextToWater(x, y, size, location)){
                    loc.setSurface(SurfaceType.SAND);
                } else {
                    if (v < 0.2) loc.setSurface(SurfaceType.ROCK);
                    else loc.setSurface(SurfaceType.GRASS);
                }

            }
        }
    }

    private boolean isNextToWater(int x, int y, int size, Location[][] location) {
        int[][] directons = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directons) {
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
