package com.visualclient.features.visuals.pulse;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

/**
 * PulseVisuals Module - Screen pulse effects, HUD pulsing, and screen shake
 */
public class PulseVisualsModule {
    private static boolean enabled = true;
    private static float screenPulseAlpha = 0f;
    private static int screenPulseTimer = 0;
    private static float screenShakeIntensity = 0f;
    private static int screenShakeTimer = 0;
    private static float damageIntensity = 1.0f;
    private static int effectDuration = 300;
    private static boolean hudPulseEnabled = true;
    private static boolean screenShakeEnabled = true;
    private static float hudScaleFactor = 1.0f;
    
    public PulseVisualsModule() {
        // Register event listeners
        registerEventListeners();
    }
    
    private void registerEventListeners() {
        // Register screen render event for pulse overlay
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
            if (enabled && screenPulseAlpha > 0) {
                renderScreenPulse(matrices, tickDelta);
            }
        });
        
        // Register tick event for animation updates
        ClientTickEvents.END_CLIENT_TICK.register(client -> onClientTick());
    }
    
    /**
     * Called when the player takes damage
     */
    public static void onDamageTaken(float damage) {
        if (!enabled) return;
        
        // Trigger screen pulse
        startScreenPulse(damageIntensity, effectDuration);
        
        // Trigger HUD elements pulse
        if (hudPulseEnabled) {
            startHudPulse();
        }
        
        // Trigger screen shake
        if (screenShakeEnabled) {
            startScreenShake(damage);
        }
    }
    
    /**
     * Start screen pulse effect with configurable intensity and duration
     */
    public static void startScreenPulse(float intensity, int duration) {
        screenPulseAlpha = Math.min(1.0f, intensity);
        screenPulseTimer = duration;
    }
    
    /**
     * Start HUD elements pulsing animation
     */
    public static void startHudPulse() {
        hudScaleFactor = 1.15f;  // Scale factor for health bar, hotbar
    }
    
    /**
     * Start screen shake effect based on damage amount
     */
    public static void startScreenShake(float damageAmount) {
        screenShakeIntensity = Math.min(5.0f, damageAmount * 0.5f);
        screenShakeTimer = (int) (damageAmount * 20);
    }
    
    /**
     * Update animations each game tick
     */
    private static void onClientTick() {
        if (!enabled) return;
        
        // Update screen pulse
        if (screenPulseTimer > 0) {
            screenPulseTimer--;
            screenPulseAlpha = (float) screenPulseTimer / effectDuration;
        } else {
            screenPulseAlpha = 0f;
        }
        
        // Update HUD pulse
        if (hudScaleFactor > 1.0f) {
            hudScaleFactor = MathHelper.lerp(0.1f, hudScaleFactor, 1.0f);
        }
        
        // Update screen shake
        if (screenShakeTimer > 0) {
            screenShakeTimer--;
        } else {
            screenShakeIntensity = 0f;
        }
    }
    
    /**
     * Render the screen pulse overlay
     */
    private static void renderScreenPulse(net.minecraft.client.gui.GuiGraphics matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getGuiScaledWidth();
        int height = client.getWindow().getGuiScaledHeight();
        
        // Draw semi-transparent overlay
        int color = (int) (screenPulseAlpha * 255) << 24 | 0x000000;  // Black overlay
        matrices.fill(0, 0, width, height, color);
    }
    
    /**
     * Get current screen shake offset
     */
    public static float getScreenShakeOffset() {
        if (screenShakeIntensity <= 0) return 0f;
        return (float) (Math.random() - 0.5f) * screenShakeIntensity * 2;
    }
    
    /**
     * Get HUD pulse scale factor
     */
    public static float getHudPulseScale() {
        return hudScaleFactor;
    }
    
    /**
     * Enable/disable the module
     */
    public static void setEnabled(boolean value) {
        enabled = value;
        if (!enabled) {
            screenPulseAlpha = 0f;
            screenPulseTimer = 0;
            screenShakeIntensity = 0f;
            screenShakeTimer = 0;
        }
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
    
    /**
     * Configuration methods
     */
    public static void setDamageIntensity(float intensity) {
        damageIntensity = Math.max(0f, Math.min(2f, intensity));
    }
    
    public static void setEffectDuration(int duration) {
        effectDuration = Math.max(50, Math.min(1000, duration));
    }
    
    public static void setHudPulseEnabled(boolean value) {
        hudPulseEnabled = value;
    }
    
    public static void setScreenShakeEnabled(boolean value) {
        screenShakeEnabled = value;
    }
    
    public static float getDamageIntensity() {
        return damageIntensity;
    }
    
    public static int getEffectDuration() {
        return effectDuration;
    }
    
    public static boolean isHudPulseEnabled() {
        return hudPulseEnabled;
    }
    
    public static boolean isScreenShakeEnabled() {
        return screenShakeEnabled;
    }
}
