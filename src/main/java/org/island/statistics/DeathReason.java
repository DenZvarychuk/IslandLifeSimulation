package org.island.statistics;

public enum DeathReason {
    STARVATION("Starved to death"),
    EATEN("Eaten"),
    UNKNOWN("Unknown reason");

    private String description;

    DeathReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}