package org.island.config.action;

public class ActionConfig {
    private RestConfig restConfig;
    private MoveConfig moveConfig;
    private EatConfig eatConfig;
    public RestConfig getRestConfig() {
        return restConfig;
    }

    public void setRestConfig(RestConfig restConfig) {
        this.restConfig = restConfig;
    }

    public MoveConfig getMoveConfig() {
        return moveConfig;
    }

    public void setMoveConfig(MoveConfig moveConfig) {
        this.moveConfig = moveConfig;
    }

    public EatConfig getEatConfig() {
        return eatConfig;
    }

    public void setEatConfig(EatConfig eatConfig) {
        this.eatConfig = eatConfig;
    }
}