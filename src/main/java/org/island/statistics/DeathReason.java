package org.island.statistics;

public enum DeathReason {
    STARVATION("Ran out of energy and satiety"),
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