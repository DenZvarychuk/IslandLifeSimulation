package main.java.org.island.playground.generators;

import main.java.org.island.playground.BiomeType;
import main.java.org.island.playground.Location;

public class BiomeGenerator {
    private final double SCALE = 0.85;
    private final double WATER_PERCENTAGE = 0.2;
    private final double FIELD_PERCENTAGE = 0.8;

    public void generate(Location[][] location, int size, int noiseScale, long seed) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location loc = location[x][y];
                // check top, right, bottom, left Island coordinates
                boolean top = x < 1;
                boolean bottom = x >= size - 1;
                boolean leftSide = y < 1;
                boolean rightSide = y >= size - 1;

                double v = noise(x * SCALE, y * SCALE, noiseScale, seed);

                // TODO make WATER around Island smoother using Circle
                if (top || bottom || leftSide || rightSide)
                    loc.setBiome(BiomeType.WATER);
                else loc.setBiome(mapToBiome(v));

                // System.out.print(v + " ");
            }
            // System.out.println("\n");
        }
    }

    private double noise(double x, double y, int noiseScale, long seed) {

        int x0 = (int) (x / noiseScale);
        int y0 = (int) (y / noiseScale);
        int x1 = x0 + 1;
        int y1 = y0 + 1;

        double sx = smooth((x % noiseScale) / (double) noiseScale);
        double sy = smooth((y % noiseScale) / (double) noiseScale);

        // corners fow blocks
        double n00 = RandomGenerator.generate(x0, y0, seed);
        double n10 = RandomGenerator.generate(x1, y0, seed);
        double n01 = RandomGenerator.generate(x0, y1, seed);
        double n11 = RandomGenerator.generate(x1, y1, seed);

        // interpolar
        double ix0 = lepr(n00, n10, sx);
        double ix1 = lepr(n01, n11, sx);

        return lepr(ix0, ix1, sy);
    }
    private double smooth(double t) {
        return t * t * (3 - 2 * t);
    }

    private double lepr(double a, double b, double d) {
        return a + (b - a) * d;
    }

    private BiomeType mapToBiome(double v) {
        if (v < WATER_PERCENTAGE) return BiomeType.WATER;
        if (v < FIELD_PERCENTAGE) return BiomeType.FIELD;
        return BiomeType.FOREST;
    }
}
