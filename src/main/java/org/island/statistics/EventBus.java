package org.island.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

// ToBeNoted Publisher-Subscriber pattern
public class EventBus {

    private Map<Class<?>, List<Consumer<?>>> listeners = new HashMap<>();

    <T extends Event> void subscribe(Class<T> type, Consumer<T> handler) {
        listeners.computeIfAbsent(type, k -> new ArrayList<>()).add(handler);
    }

    public void publish(Event event) {
        List<Consumer<?>> handlers = listeners.get(event.getClass());
        if (handlers == null) return;
        for (Consumer<?> handler : handlers) {
            ((Consumer<Event>) handler).accept(event);
        }
    }

}
