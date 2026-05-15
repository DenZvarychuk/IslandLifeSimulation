package org.island.playground;

import org.island.config.entity.EntityConfig;
import org.island.config.island.IslandConfig;
import org.island.config.SimulationConfig;
import org.island.entity.Entity;
import org.island.entity.animals.Animal;
import org.island.entity.animals.AnimalType;
import org.island.entity.factory.AnimalFactory;
import org.island.entity.factory.PlantFactory;
import org.island.entity.plants.PlantType;
import org.island.playground.generators.BiomeGenerator;
import org.island.playground.generators.SurfaceGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class Island {
    private final int noiseCorrelation;
    private final int size;
    private final long seed;
    private int noiseScale;

    private final Location[][] location;

    private IslandConfig islandConfig;
    private EntityConfig entityConfig;
    private AnimalFactory animalFactory;
    private PlantFactory plantFactory;

    public Island(SimulationConfig config) {

        System.out.println("Generating Island...");
        initConfig(config);

        // init Island fields and Locations
        this.size = islandConfig.getSize();
        this.seed = islandConfig.getSeed();
        this.noiseCorrelation = islandConfig.getNoiseCorrelation();
        this.animalFactory = new AnimalFactory(entityConfig.getAnimalConfig(), entityConfig.getDefaults());
        this.plantFactory = new PlantFactory(entityConfig.getPlantConfig(), seed);
        parseSeed();
        location = new Location[size][size];
        initLocations();
        System.out.println("Island is generated!");
    }

    private void initConfig(SimulationConfig config) {
        this.islandConfig = config.getIslandConfig();
        this.entityConfig = config.getEntityConfig();
    }

    public void generatePlants() {
        System.out.println("Generating Plants...");

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location loc = location[x][y];
                for (PlantType plantType : PlantType.values()) {
                    plantFactory.createPlant(plantType, loc);
                }
            }
        }
        System.out.println("Plants are generated!");
    }

    public void generateAnimals() {
        System.out.println("Generating Animals...");
        Random random = new Random(seed);

        // TODO create more animals based on biome and quantity correlation
        for (AnimalType animalType : AnimalType.values()) {
            Location loc = getRandomeLocation(random);
            for (int i = 0; i < 5; i++) {
                animalFactory.createAnimal(animalType, loc);
            }
        }
        System.out.println("Animals are generated!");
    }

    public Location getRandomeLocation(Random random) {
        return location[random.nextInt(size)][random.nextInt(size)];
    }

    public void generateBiomesAndSurfaces() {
        System.out.println("Generating Biomes...");
        new BiomeGenerator(islandConfig).generate(location, size, noiseScale, seed);
        System.out.println("Generating Surfaces...");
        new SurfaceGenerator(islandConfig).generate(location, size, seed);
        System.out.println("Biomes and Surfaces are generated! \n" + getIslandMapCLI());
    }

    private void parseSeed() {
        noiseScale = (int) (seed % 10) * noiseCorrelation;
    }

    private void initLocations() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                location[x][y] = new Location(x, y);
            }
        }
    }

    public Location getLocation(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) return null;
        return location[x][y];
    }


    public BiomeType getBiome(int x, int y) {
        return location[x][y].getBiome();
    }

    public SurfaceType getSurface(int x, int y) {
        return location[x][y].getSurface();
    }

    public int getSize() {
        return size;
    }

    public List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        // Iterate through all locations
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location location = getLocation(x, y);

                // Filter only animals
                for (Entity entity : location.getEntities()) {
                    if (entity instanceof Animal) {
                        animals.add((Animal) entity);
                    }
                }
            }
        }
        return animals;
    }

    public List<?> getEntityByLocation(int x, int y) {
        return location[x][y].getEntities();
    }


    // Statistics

    // TODO move all statistics to separate class

    public Map<String, List<?>> getEntitiesInAllLocByList() {
        Map<String, List<?>> entities = new LinkedHashMap<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location loc = location[x][y];
                String location = "Location: x" + x + ", y" + y;
                if (!loc.getEntities().isEmpty())
                    entities.put(location, loc.getEntities());
            }
        }
        return entities;
    }

    public String getEntitiesInAllLocByCount() {
        StringBuilder result = new StringBuilder();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location loc = location[x][y];
                List<Entity> entities = loc.getEntities();

                if (!entities.isEmpty()) {
                    Map<String, Long> counts = entities.stream()
                            .collect(Collectors.groupingBy(
                                    entity -> entity.getEntityType().toString(),
                                    Collectors.counting()
                            ));

                    result.append(String.format("Location: x=%d, y=%d, Biome: %s, Surface: %s, Entities: ", x, y, loc.getBiome().name(), loc.getSurface().name()));
                    counts.forEach((type, count) ->
                            result.append(type).append(" count: ").append(count).append(", ")
                    );
                    result.setLength(result.length() - 2);
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }

    public String getIslandMapCLI() {
        StringBuilder sb = new StringBuilder();

        String islandSize = "Island{" +
                "size=" + size + "x" + size +
                '}';

        sb.append(islandSize + "\n");

        //TODO move to separate method getIslandMapCLI()
        //TODO create a new statistic method
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                sb.append("(x" + x + ", y" + y + ") b: " +
                        getBiome(x, y) + ", s: " +
                        getSurface(x, y) + " | ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}