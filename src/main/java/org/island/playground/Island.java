package org.island.playground;

import org.island.config.BiomeConfig;
import org.island.config.IslandConfig;
import org.island.config.SimulationConfig;
import org.island.playground.generators.BiomeGenerator;
import org.island.playground.generators.SurfaceGenerator;

public class Island {
    private final int noiseCorrelation;
    private final int size;
    private final long seed;
    private final Location[][] location;
    private int noiseScale;
    private IslandConfig islandConfig;


    // TODO move Biomes and Surface generation to Generator
    public Island(SimulationConfig config) {
        this.islandConfig = config.getIslandConfig();
        this.size = islandConfig.getSize();
        this.seed = islandConfig.getSeed();
        this.noiseCorrelation = config.getIslandConfig().getNoiseCorrelation();

        parseSeed();

        location = new Location[size][size];
        initLocations();

        generateBiomesAndSurfaces();
    }

    private void generateBiomesAndSurfaces() {
        new BiomeGenerator(islandConfig).generate(location, size, noiseScale, seed);
        new SurfaceGenerator(islandConfig).generate(location, size, seed);
    }

    private void parseSeed() {
        noiseScale = (int)(seed % 10) * noiseCorrelation;
    }

    private void initLocations() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                location[x][y] = new Location(x, y);
            }
        }
    }


    public Location getLocation(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) return null;
        return location[x][y];
    }

    public BiomeType getBiome(int x, int y) {
        return location[x][y].getBiome();
    }

    public SurfaceType getSurface(int x, int y) {
        return location[x][y].getSurface();
    }

    public String getIslandMapCLI() {
        StringBuilder sb = new StringBuilder();

        String islandSize = "Island{" +
                "size=" + size + "x" + size +
                '}';

        sb.append(islandSize + "\n");

        //TODO move to separate method getIslandMapCLI()
        //TODO create a new statistic method
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                sb.append(getBiome(x, y) +" ");
//                sb.append("(x" + x + ", y" + y + ") b: " +
//                        getBiome(x, y) + ", s: " +
//                        getSurface(x, y) + " | ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
