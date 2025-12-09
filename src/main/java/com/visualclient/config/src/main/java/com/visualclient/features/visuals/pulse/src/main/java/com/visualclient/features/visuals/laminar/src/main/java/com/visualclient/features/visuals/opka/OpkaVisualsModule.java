package com.visualclient.features.visuals.opka;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

/**
 * OpkaVisuals Module - Hit markers, weapon animations, and customizable crosshair
 */
public class OpkaVisualsModule {
    private static boolean enabled = true;
    private static boolean hitMarkerEnabled = true;
    private static int hitMarkerX = 0;
    private static int hitMarkerY = 0;
    private static int hitMarkerTimer = 0;
    private static int hitMarkerDuration = 200;
    
    private static String crosshairType = "CROSS";
    private static float crosshairSize = 1.0f;
    private static int crosshairColor = 0xFFFFFF;
    private static float crosshairSpread = 0f;
    private static boolean crosshairAnimateOnHit = true;
    
    private static String weaponAnimType = "SOFT_SWING";
    private static boolean weaponGlowEnabled = false;
    private static float weaponGlowAlpha = 0.5f;
    
    public OpkaVisualsModule() {
        registerEventListeners();
    }
    
    private void registerEventListeners() {
        // Register HUD render event for hit marker and crosshair
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
            if (enabled) {
                renderHudElements(matrices, tickDelta);
            }
        });
    }
    
    /**
     * Trigger hit marker on successful hit
     */
    public static void onHit() {
        if (!enabled || !hitMarkerEnabled) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() != null) {
            hitMarkerX = client.getWindow().getGuiScaledWidth() / 2;
            hitMarkerY = client.getWindow().getGuiScaledHeight() / 2;
            hitMarkerTimer = hitMarkerDuration;
        }
        
        // Animate crosshair on hit
        if (crosshairAnimateOnHit) {
            animateCrosshair();
        }
    }
    
    /**
     * Render hit marker and crosshair on HUD
     */
    private static void renderHudElements(net.minecraft.client.gui.GuiGraphics matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) return;
        
        int centerX = client.getWindow().getGuiScaledWidth() / 2;
        int centerY = client.getWindow().getGuiScaledHeight() / 2;
        
        // Render crosshair
        renderCrosshair(matrices, centerX, centerY);
        
        // Render hit marker
        if (hitMarkerTimer > 0) {
            renderHitMarker(matrices);
            hitMarkerTimer--;
        }
    }
    
    /**
     * Render customizable crosshair
     */
    private static void renderCrosshair(net.minecraft.client.gui.GuiGraphics matrices, int x, int y) {
        int size = (int) (8 * crosshairSize);
        int thickness = 2;
        
        switch (crosshairType) {
            case "CROSS":
                // Render cross (+) shape
                matrices.fill(x - size, y - thickness, x + size, y + thickness, crosshairColor);
                matrices.fill(x - thickness, y - size, x + thickness, y + size, crosshairColor);
                break;
            case "DOT":
                // Render dot
                matrices.fill(x - 2, y - 2, x + 2, y + 2, crosshairColor);
                break;
            case "CIRCLE":
                // Render circle outline
                int radius = size / 2;
                // Simple circle rendering
                for (int angle = 0; angle < 360; angle += 10) {
                    int px = (int) (x + radius * Math.cos(Math.toRadians(angle)));
                    int py = (int) (y + radius * Math.sin(Math.toRadians(angle)));
                    matrices.fill(px - 1, py - 1, px + 1, py + 1, crosshairColor);
                }
                break;
        }
    }
    
    /**
     * Render animated hit marker
     */
    private static void renderHitMarker(net.minecraft.client.gui.GuiGraphics matrices) {
        float progress = 1.0f - ((float) hitMarkerTimer / hitMarkerDuration);
        int size = (int) (16 * (1.0f - progress * 0.5f));
        int alpha = (int) (255 * (1.0f - progress));
        int color = (alpha << 24) | 0xFF0000;  // Red color
        
        // Draw hit marker corners
        int offset = 5;
        int thickness = 2;
        
        // Top-left
        matrices.fill(hitMarkerX - size - offset, hitMarkerY - size - offset, 
                     hitMarkerX - size, hitMarkerY - size + thickness, color);
        matrices.fill(hitMarkerX - size - offset, hitMarkerY - size - offset, 
                     hitMarkerX - size + thickness, hitMarkerY - size, color);
        
        // Top-right
        matrices.fill(hitMarkerX + size + offset, hitMarkerY - size - offset, 
                     hitMarkerX + size, hitMarkerY - size + thickness, color);
        matrices.fill(hitMarkerX + size + offset - thickness, hitMarkerY - size - offset, 
                     hitMarkerX + size + offset, hitMarkerY - size, color);
        
        // Bottom-left
        matrices.fill(hitMarkerX - size - offset, hitMarkerY + size + offset, 
                     hitMarkerX - size, hitMarkerY + size - thickness, color);
        matrices.fill(hitMarkerX - size - offset, hitMarkerY + size + offset - thickness, 
                     hitMarkerX - size + thickness, hitMarkerY + size + offset, color);
        
        // Bottom-right
        matrices.fill(hitMarkerX + size + offset, hitMarkerY + size + offset, 
                     hitMarkerX + size, hitMarkerY + size - thickness, color);
        matrices.fill(hitMarkerX + size + offset - thickness, hitMarkerY + size + offset - thickness, 
                     hitMarkerX + size + offset, hitMarkerY + size + offset, color);
    }
    
    /**
     * Animate crosshair on hit
     */
    private static void animateCrosshair() {
        crosshairSpread = 8.0f;
    }
    
    /**
     * Configuration methods
     */
    public static void setEnabled(boolean value) {
        enabled = value;
    }
    
    public static void setHitMarkerEnabled(boolean value) {
        hitMarkerEnabled = value;
    }
    
    public static void setCrosshairType(String type) {
        crosshairType = type;  // CROSS, DOT, CIRCLE
    }
    
    public static void setCrosshairSize(float size) {
        crosshairSize = Math.max(0.5f, Math.min(2.0f, size));
    }
    
    public static void setCrosshairColor(int color) {
        crosshairColor = color;
    }
    
    public static void setWeaponAnimType(String type) {
        weaponAnimType = type;  // SOFT_SWING, HARD_SWING, ANIME_SWING
    }
    
    public static void setWeaponGlowEnabled(boolean value) {
        weaponGlowEnabled = value;
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
    
    public static String getCrosshairType() {
        return crosshairType;
    }
    
    public static float getCrosshairSize() {
        return crosshairSize;
    }
}
