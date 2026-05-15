package org.island.engine.actions;

public enum ActionResultStatus {
    NONE,
    SUCCESS,
    FAILED,                             // Generic one
    FAILED_DIED,
    FAILED_DIED_IN_PROCESS,
    FAILED_NO_TARGET_FOUND,
    FAILED_PROBABILITY_CHECK,
    FAILED_TARGET_GONE
}