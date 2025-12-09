package com.visualclient;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the VisualClient mod.
 * Initializes all visual modules and systems.
 */
public class VisualClientMod implements ClientModInitializer {
    public static final String MOD_ID = "visualclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("VisualClient mod initialized!");
        // Module initialization will be called here
    }
}
