package org.island.config;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ConfigLoader {

    private static final String CONFIG_FILE_PATH = "/config.yaml";

    public static SimulationConfig load() {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(new Constructor(SimulationConfig.class, options));

        try (InputStream input = ConfigLoader.class.getResourceAsStream(CONFIG_FILE_PATH)) {
            if (input == null) {
                throw new RuntimeException("Config file not found: " + CONFIG_FILE_PATH);
            }
            return yaml.load(input);
        } catch (Exception e){
            throw new RuntimeException("Failed to load config file: " + CONFIG_FILE_PATH, e);
        }
    }
}
