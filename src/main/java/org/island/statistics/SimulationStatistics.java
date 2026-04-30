package org.island.statistics;

import java.util.ArrayList;
import java.util.List;

public class SimulationStatistics {
    // TODO add more reports
    private List<DeathRecord> deathRecords;

    public SimulationStatistics() {
        deathRecords = new ArrayList<>();
    }

    // Death statistics
    public void registerDeath(DeathRecord record) {
        deathRecords.add(record);
    }

    public List<DeathRecord> getDeathRecords() {
        return deathRecords;
    }
    public List<DeathRecord> getDeathsByReason(DeathReason reason) {
        return deathRecords.stream()
                .filter(record -> record.getReason() == reason)
                .toList();
    }

    public int getTotalDeaths() {
        return deathRecords.size();
    }



    // total report
    public void printFullReport() {
        System.out.println("========== SIMULATION STATISTICS ==========");

        System.out.println("\n--- DEATHS ---");
        System.out.println("Total deaths: " + getTotalDeaths());
        deathRecords.forEach(System.out::println);


        System.out.println("\n===========================================");
    }
}
