package org.island.config.action;

public class RestConfig {
    private double baseEnergyRatio;
    private SleepConfig sleep;
    private IdleConfig idle;

    public double getBaseEnergyRatio() {
        return baseEnergyRatio;
    }

    public void setBaseEnergyRatio(double baseEnergyRatio) {
        this.baseEnergyRatio = baseEnergyRatio;
    }

    public SleepConfig getSleep() {
        return sleep;
    }

    public void setSleep(SleepConfig sleep) {
        this.sleep = sleep;
    }

    public IdleConfig getIdle() {
        return idle;
    }

    public void setIdle(IdleConfig idle) {
        this.idle = idle;
    }


    public static class SleepConfig {
        private double minEnergyRatio;
        private int cycleCount;

        public double getMinEnergyRatio() {
            return minEnergyRatio;
        }

        public void setMinEnergyRatio(double minEnergyRatio) {
            this.minEnergyRatio = minEnergyRatio;
        }

        public int getCycleCount() {
            return cycleCount;
        }

        public void setCycleCount(int cycleCount) {
            this.cycleCount = cycleCount;
        }
    }

    public static class IdleConfig {
        private double minEnergyRatio;
        private double chance;
        private double energyBoost;

        public double getMinEnergyRatio() {
            return minEnergyRatio;
        }

        public void setMinEnergyRatio(double minEnergyRatio) {
            this.minEnergyRatio = minEnergyRatio;
        }

        public double getChance() {
            return chance;
        }

        public void setChance(double chance) {
            this.chance = chance;
        }

        public double getEnergyBoost() {
            return energyBoost;
        }

        public void setEnergyBoost(double energyBoost) {
            this.energyBoost = energyBoost;
        }
    }
}