package com.gavin101.ConfigHelper;

import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.utilities.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class ConfigHelper<T> {
    private final Map<String, String> parsedArgs;

    private static final Gson gson = new Gson();

    private static File configFile;
    private final Class<T> configClass;
    private T config;

    private static final File clientBasePath = new File(System.getProperty("user.home"), "EternalClient");
    private static final File clientScriptsPath = new File(clientBasePath, "Scripts");
    private static final File scriptPath = new File(clientScriptsPath, AbstractScript.getScriptName());


    public void initialize() {
        createConfigDirs();
        setConfig();
    }

    private void createConfigDirs() {
        if (!scriptPath.exists()) {
            try {
                Files.createDirectories(scriptPath.toPath());
            }
            catch (IOException e) {
                Log.error("Failed to create script path");
                Log.exception(e);
                System.exit(1);
            }
        }
    }

    private void setConfig() {
        configFile = new File(scriptPath, parsedArgs.getOrDefault("config", "payload.json"));

        if (!configFile.exists()) {
            try {
                config = configClass.getDeclaredConstructor().newInstance();
                saveConfig();
            } catch (Exception e) {
                Log.error("Failed to create new config instance");
                Log.exception(e);
                System.exit(1);
            }
        }
        else {
            config = loadConfig();
        }
    }


    private void saveConfig() {
        try {
            Files.writeString(configFile.toPath(), gson.toJson(config));
        }
        catch (Exception e) {
            Log.error("Failed to save config");
            Log.exception(e);
        }
    }

    private T loadConfig() {
        try {
            return gson.fromJson(Files.readString(configFile.toPath()), configClass);
        }
        catch (Exception e) {
            Log.error("Failed to load config");
            Log.exception(e);
            try {
                return configClass.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                Log.error("Failed to create new config instance");
                Log.exception(ex);
            }
        }
        return null;
    }
}
