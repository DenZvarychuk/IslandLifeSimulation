package org.island.statistics;

import org.island.engine.Simulation;
import org.island.entity.Entity;
import org.island.entity.EntityType;

public class DeathRecord implements Event {

    private Entity entity;
    private EntityType entityType;
    private DeathReason reason;
    private int simulationCycle;

    public DeathRecord(Entity entity, DeathReason reason) {
        this.entity = entity;
        this.entityType = entity.getEntityType();
        this.reason = reason;
        this.simulationCycle = Simulation.getSimulationCycle();
    }

    public DeathReason getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return String.format("%s died at cycle %d: %s",
                entityType, simulationCycle, reason.getDescription());
    }
}