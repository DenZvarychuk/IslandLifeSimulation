package org.island.engine;

import org.island.statistics.EventBus;
import org.island.statistics.SimulationStatistics;

public class SimulationContext {
    private final SimulationStatistics statistics;
    private final EventBus eventbus;
    // TODO consider to add any other field valid for simulation (Logger, Random, etc)

    public SimulationContext(EventBus eventbus, SimulationStatistics statistics) {
        this.eventbus = eventbus;
        this.statistics = statistics;
    }

    public SimulationStatistics getStatistics() {
        return statistics;
    }

    public EventBus getEventbus() {
        return eventbus;
    }
}