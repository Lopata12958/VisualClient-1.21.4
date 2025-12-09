package com.visualclient.features.visuals.topka;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

/**
 * TopkaVisuals Module - Kill combos, target HUD, and premium visual effects
 */
public class TopkaVisualsModule {
    private static boolean enabled = true;
    private static int comboCount = 0;
    private static int lastKillTime = 0;
    private static final int COMBO_RESET_TIME = 5000;  // 5 seconds
    
    private static boolean showCombo = true;
    private static boolean showTargetHud = true;
    private static boolean showSpeedIndicator = true;
    private static boolean smoothAnimations = true;
    private static float hudScale = 1.0f;
    
    public TopkaVisualsModule() {
        registerEventListeners();
    }
    
    private void registerEventListeners() {
        // Register HUD render for combo and target displays
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
            if (enabled) {
                renderTopkaHud(matrices, tickDelta);
            }
        });
        
        // Register tick event for animations
        ClientTickEvents.END_CLIENT_TICK.register(client -> onClientTick());
    }
    
    /**
     * Call when player kills another entity
     */
    public static void onKill() {
        if (!enabled) return;
        
        long currentTime = System.currentTimeMillis();
        
        // Check if combo should be reset
        if (currentTime - lastKillTime > COMBO_RESET_TIME) {
            comboCount = 1;
        } else {
            comboCount++;
        }
        
        lastKillTime = currentTime;
    }
    
    /**
     * Render all Topka HUD elements
     */
    private static void renderTopkaHud(net.minecraft.client.gui.GuiGraphics matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) return;
        
        int x = 10;
        int y = 20;
        
        // Render combo counter
        if (showCombo && comboCount > 0) {
            renderComboCounter(matrices, x, y);
        }
        
        // Render speed indicator
        if (showSpeedIndicator) {
            renderSpeedIndicator(matrices, client.getWindow().getGuiScaledWidth() - 100, 20);
        }
        
        // Render target HUD
        if (showTargetHud) {
            renderTargetHud(matrices, client.getWindow().getGuiScaledWidth() / 2 - 50, 
                          client.getWindow().getGuiScaledHeight() - 120);
        }
    }
    
    /**
     * Render kill combo counter with animations
     */
    private static void renderComboCounter(net.minecraft.client.gui.GuiGraphics matrices, int x, int y) {
        String comboText = "COMBO x" + comboCount;
        int color = getComboColor();
        
        // Render shadow
        matrices.drawString(MinecraftClient.getInstance().textRenderer, comboText, x + 2, y + 2, 0xFF000000, false);
        // Render text
        matrices.drawString(MinecraftClient.getInstance().textRenderer, comboText, x, y, color, false);
    }
    
    /**
     * Get color based on combo count (gradient from yellow to red)
     */
    private static int getComboColor() {
        if (comboCount < 5) return 0xFFFFFF00;  // Yellow
        if (comboCount < 10) return 0xFFFF8800;  // Orange
        return 0xFFFF0000;  // Red
    }
    
    /**
     * Render speed/strafe indicator
     */
    private static void renderSpeedIndicator(net.minecraft.client.gui.GuiGraphics matrices, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        double speed = Math.sqrt(
            client.player.getVelocity().x * client.player.getVelocity().x + 
            client.player.getVelocity().z * client.player.getVelocity().z
        );
        
        String speedText = "Speed: " + String.format("%.2f", speed);
        matrices.drawString(client.textRenderer, speedText, x, y, 0xFF00FF00, false);
    }
    
    /**
     * Render minimalist target HUD showing attacked entity info
     */
    private static void renderTargetHud(net.minecraft.client.gui.GuiGraphics matrices, int x, int y) {
        // This would display player info when targeting
        matrices.drawString(MinecraftClient.getInstance().textRenderer, "Target: [No Target]", x, y, 0xFF00FF00, false);
    }
    
    /**
     * Handle tick-based updates and resets
     */
    private static void onClientTick() {
        if (!enabled) return;
        
        long currentTime = System.currentTimeMillis();
        
        // Reset combo if timeout exceeded
        if (comboCount > 0 && currentTime - lastKillTime > COMBO_RESET_TIME) {
            comboCount = 0;
        }
    }
    
    /**
     * Configuration methods
     */
    public static void setEnabled(boolean value) {
        enabled = value;
        if (!enabled) {
            comboCount = 0;
        }
    }
    
    public static void setShowCombo(boolean value) {
        showCombo = value;
    }
    
    public static void setShowTargetHud(boolean value) {
        showTargetHud = value;
    }
    
    public static void setShowSpeedIndicator(boolean value) {
        showSpeedIndicator = value;
    }
    
    public static void setSmoothAnimations(boolean value) {
        smoothAnimations = value;
    }
    
    public static void setHudScale(float scale) {
        hudScale = Math.max(0.5f, Math.min(2.0f, scale));
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
    
    public static int getComboCount() {
        return comboCount;
    }
}
