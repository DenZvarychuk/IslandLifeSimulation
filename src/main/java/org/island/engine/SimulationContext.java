package org.island.engine;

import org.island.config.SimulationConfig;
import org.island.statistics.EventBus;
import org.island.statistics.SimulationStatistics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationContext {
    private final SimulationStatistics statistics;
    private final EventBus eventbus;
    private final ExecutorService executorService;
    // TODO consider to add any other field valid for simulation (Logger, Random, etc)

    public SimulationContext(EventBus eventbus, SimulationStatistics statistics, SimulationConfig config) {
        this.eventbus = eventbus;
        this.statistics = statistics;
        // TODO read about ForkJoinPool for parallel tasks
        this.executorService = Executors.newFixedThreadPool(config.getThreadPoolSize());
    }

    public SimulationStatistics getStatistics() {
        return statistics;
    }

    public EventBus getEventbus() {
        return eventbus;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
