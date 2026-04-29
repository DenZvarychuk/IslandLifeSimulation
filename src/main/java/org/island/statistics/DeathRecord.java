package org.island.statistics;

import org.island.engine.Simulation;
import org.island.entity.Entity;

public class DeathRecord {

    private Entity entity;
    private String EntityType;
    private DeathReason reason;
    private int simulationCycle;

    public DeathRecord(Entity entity, DeathReason reason) {
        this.entity = entity;
        this.EntityType = entity.getType();
        this.reason = reason;
        this.simulationCycle = Simulation.getSimulationCycle();
    }

    public DeathReason getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return String.format("%s died at cycle %d: %s",
                EntityType, simulationCycle, reason.getDescription());
    }
}