package org.island.config;

public class SimulationConfig {
    private IslandConfig islandConfig;
    private EntitiesConfig entitiesConfig;

    public EntitiesConfig getEntitiesConfig() {
        return entitiesConfig;
    }

    public void setEntitiesConfig(EntitiesConfig entitiesConfig) {
        this.entitiesConfig = entitiesConfig;
    }

    public IslandConfig getIslandConfig() {
        return islandConfig;
    }

    public void setIslandConfig(IslandConfig islandConfig) {
        this.islandConfig = islandConfig;
    }
}
