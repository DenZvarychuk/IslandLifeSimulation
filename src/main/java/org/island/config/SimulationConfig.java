package org.island.config;

import org.island.config.action.ActionConfig;
import org.island.config.entity.EntityConfig;
import org.island.config.island.IslandConfig;

public class SimulationConfig {
    private IslandConfig islandConfig;
    private EntityConfig entityConfig;
    private ActionConfig actionConfig;
    private int simulationCycleCount;
    private long cycleDelay;

    public EntityConfig getEntityConfig() {
        return entityConfig;
    }

    public void setEntityConfig(EntityConfig entityConfig) {
        this.entityConfig = entityConfig;
    }

    public IslandConfig getIslandConfig() {
        return islandConfig;
    }

    public void setIslandConfig(IslandConfig islandConfig) {
        this.islandConfig = islandConfig;
    }

    public int getSimulationCycleCount() {
        return simulationCycleCount;
    }

    public void setSimulationCycleCount(int simulationCycleCount) {
        this.simulationCycleCount = simulationCycleCount;
    }

    public ActionConfig getActionConfig() {
        return actionConfig;
    }

    public void setActionConfig(ActionConfig actionConfig) {
        this.actionConfig = actionConfig;
    }

    public long getCycleDelay() {
        return cycleDelay;
    }

    public void setCycleDelay(long cycleDelay) {
        this.cycleDelay = cycleDelay;
    }
}