package org.island.config.island;

public class IslandConfig {
    private int size;
    private long seed;
    private int noiseCorrelation;
    private BiomeConfig biomeConfig;
    private SurfaceConfig surfaceConfig;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getNoiseCorrelation() {
        return noiseCorrelation;
    }

    public void setNoiseCorrelation(int noiseCorrelation) {
        this.noiseCorrelation = noiseCorrelation;
    }

    public BiomeConfig getBiomeConfig() {
        return biomeConfig;
    }

    public void setBiomeConfig(BiomeConfig biomeConfig) {
        this.biomeConfig = biomeConfig;
    }

    public SurfaceConfig getSurfaceConfig() {
        return surfaceConfig;
    }

    public void setSurfaceConfig(SurfaceConfig surfaceConfig) {
        this.surfaceConfig = surfaceConfig;
    }
}
