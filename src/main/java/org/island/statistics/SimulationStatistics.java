package org.island.statistics;

import org.island.engine.actions.ActionResult;
import org.island.engine.actions.ActionResultStatus;
import org.island.engine.actions.ActionType;
import org.island.engine.actions.eating.EatResult;
import org.island.engine.actions.movements.MoveResult;
import org.island.engine.actions.resting.RestResult;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationStatistics {
    // TODO add more reports
    private final List<DeathRecord> deathRecords = new ArrayList<>();

    public SimulationStatistics(EventBus eventBus) {
        eventBus.subscribe(DeathRecord.class, this::registerDeath);
        // Add any other metrics
    }

    // Death statistics
    public void registerDeath(DeathRecord record) {
        System.out.println("Registered death: " + record);
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


    // action reports
    public void printActionStats(List<ActionResult> results) {
        System.out.println("\n----- Action results -----");

        int totalMoved = 0;
        int totalSteps = 0;
        int totalEaten = 0;
        int totalRest = 0;

        List<MoveResult> moveResult = results.stream()
                .filter(MoveResult.class::isInstance)
                .map(MoveResult.class::cast)
                .toList();

        List<EatResult> eatResults = results.stream()
                .filter(EatResult.class::isInstance)
                .map(EatResult.class::cast)
                .toList();

        List<RestResult> restResults = results.stream()
                .filter(RestResult.class::isInstance)
                .map(RestResult.class::cast)
                .toList();

        for (MoveResult result : moveResult) {
            if (result.getActionType() != ActionType.NONE) {
                System.out.println(result.getAnimal() +
                        " moved from " + result.getBaseActionLocation().toString() +
                        " to " + result.getEndLocation() +
                        "\n in " + result.getStepsTaken() + " steps" +
                        "\n status: " + result.getStatus() +
                        "\n energy: " + result.getAnimal().getEnergy() +
                        "\n satiety " + result.getAnimal().getSatiety());
            }

            if (result.getStatus() == ActionResultStatus.SUCCESS) {
                totalMoved++;
                totalSteps += result.getStepsTaken();
            }
        }

        for (EatResult result : eatResults) {
            if (result.getActionType() != ActionType.NONE) {
                System.out.println(result.getAnimal() +
                        " ate " + result.getFood() +
                        "\n in " + result.getBaseActionLocation() +
                        "\n status: " + result.getStatus() +
                        "\n energy: " + result.getAnimal().getEnergy() +
                        "\n satiety: " + result.getAnimal().getSatiety());
            }

            if (result.getStatus() == ActionResultStatus.SUCCESS) {
                totalEaten++;
            }
        }

        for (RestResult result : restResults) {
            if (result.getActionType() != ActionType.NONE) {
                System.out.println(result.getAnimal() +
                        " is " + result.getActionType() +
                        " in: " + result.getBaseActionLocation() +
                        "\n energy before: " + result.getEnergyBefore() +
                        "\n energy after: " + result.getEnergyAfter() +
                        "\n status: " + result.getStatus() +
                        "\n energy: " + result.getAnimal().getEnergy() +
                        "\n satiety:  " + result.getAnimal().getSatiety());
            }

            if (result.getStatus() == ActionResultStatus.SUCCESS) {
                totalRest++;
            }
        }

        System.out.println("Animals moved: " + totalMoved + "/" + moveResult.size());
        System.out.println("Total steps taken: " + totalSteps);
        System.out.println("Animals or Plants eaten: " + totalEaten);
        System.out.println("Animals rested: " + totalRest + "/" + restResults.size());
        System.out.println();
    }

    // total report
    public void printFullReport() {
        System.out.println("========== SIMULATION STATISTICS ==========");

        System.out.println("\n--- DEATHS ---");
        System.out.println("Total deaths: " + getTotalDeaths());
        deathRecords.forEach(System.out::println);

        Map<DeathReason, Long> map = deathRecords.stream()
                .collect(Collectors.groupingBy(
                        DeathRecord::getReason,
                        () -> new EnumMap<>(DeathReason.class),
                        Collectors.counting()
                ));
        map.forEach((reason, count) -> System.out.println(reason + ": " + count));

        System.out.println("\n===========================================");
    }
}