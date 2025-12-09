package com.visualclient.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages configuration loading and saving for VisualClient.
 * Handles JSON serialization and file I/O.
 */
public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_DIR = "config/visualclient";
    private static final String CONFIG_FILE = "config.json";
    
    private ModConfig config;
    
    public ConfigManager() {
        loadConfig();
    }
    
    /**
     * Loads configuration from file, or creates default if not exists
     */
    public void loadConfig() {
        File file = getConfigFile();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                config = GSON.fromJson(reader, ModConfig.class);
            } catch (IOException e) {
                createDefaultConfig();
            }
        } else {
            createDefaultConfig();
        }
    }
    
    /**
     * Saves current configuration to file
     */
    public void saveConfig() {
        File file = getConfigFile();
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void createDefaultConfig() {
        config = new ModConfig();
        saveConfig();
    }
    
    private File getConfigFile() {
        File dir = new File(CONFIG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, CONFIG_FILE);
    }
    
    public ModConfig getConfig() {
        return config;
    }
}
