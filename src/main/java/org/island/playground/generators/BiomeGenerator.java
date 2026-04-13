package main.java.org.island.playground;

public class BiomeGenerator {

    public void generate(Location[][] location, int size, int noiseScale, long seed) {
        double scale = 0.85;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location loc = location[x][y];
                // check top, right, bottom, left Island coordinates
                boolean top = x < 1;
                boolean bottom = x >= size - 1;
                boolean leftSide = y < 1;
                boolean rightSide = y >= size - 1;

                double v = noise(x * scale, y * scale, noiseScale, seed);

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
        double n00 = Randome.generate(x0, y0, seed);
        double n10 = Randome.generate(x1, y0, seed);
        double n01 = Randome.generate(x0, y1, seed);
        double n11 = Randome.generate(x1, y1, seed);

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
        if (v < 0.2) return BiomeType.WATER;
        if (v < 0.8) return BiomeType.FIELD;
        return BiomeType.FOREST;
    }
}
