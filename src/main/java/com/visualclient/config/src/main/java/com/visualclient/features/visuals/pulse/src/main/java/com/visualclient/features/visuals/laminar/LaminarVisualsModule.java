package com.visualclient.features.visuals.laminar;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

/**
 * LaminarVisuals Module - Custom particles, trails, and color effects
 */
public class LaminarVisualsModule {
    private static boolean enabled = true;
    private static String particleType = "HEART";
    private static int particleCount = 8;
    private static float particleSize = 0.5f;
    private static String trailType = "LINEAR";
    private static boolean trailsEnabled = true;
    private static String colorGradingType = "NORMAL";
    private static float fogDensity = 0f;
    
    private static final List<Particle> particles = new ArrayList<>();
    private static final List<TrailPoint> trails = new ArrayList<>();
    
    public LaminarVisualsModule() {
        registerEventListeners();
    }
    
    private void registerEventListeners() {
        // Register world render event for particle rendering
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            if (enabled) {
                updateAndRenderParticles(context);
            }
        });
        
        // Register tick event for animation updates
        ClientTickEvents.END_CLIENT_TICK.register(client -> onClientTick());
    }
    
    /**
     * Spawn hit particles at specified position
     */
    public static void spawnHitParticles(Vec3d pos) {
        if (!enabled) return;
        
        for (int i = 0; i < particleCount; i++) {
            double angleRad = (2 * Math.PI * i) / particleCount;
            double vx = Math.cos(angleRad) * 0.5;
            double vy = Math.random() * 0.3 + 0.2;
            double vz = Math.sin(angleRad) * 0.5;
            
            Particle particle = new Particle(
                pos,
                new Vec3d(vx, vy, vz),
                particleType,
                particleSize,
                300  // lifetime ticks
            );
            particles.add(particle);
        }
    }
    
    /**
     * Create trail effect following a path
     */
    public static void createTrail(Vec3d startPos, Vec3d endPos) {
        if (!enabled || !trailsEnabled) return;
        
        TrailPoint point = new TrailPoint(startPos, trailType, 400);
        trails.add(point);
    }
    
    /**
     * Update particle animations and remove expired ones
     */
    private static void updateAndRenderParticles(WorldRenderEvents.AfterTranslucent context) {
        particles.removeIf(p -> p.isExpired());
        particles.forEach(p -> p.update());
        
        // Remove expired trails
        trails.removeIf(t -> t.isExpired());
        trails.forEach(t -> t.update());
    }
    
    /**
     * Client tick for color grading and fog effects
     */
    private static void onClientTick() {
        if (!enabled) return;
        
        // Apply color grading effects
        applyColorGrading();
        
        // Apply fog effects
        if (fogDensity > 0) {
            applyFogEffect();
        }
    }
    
    /**
     * Apply color grading to the world
     */
    private static void applyColorGrading() {
        switch (colorGradingType) {
            case "WARM":
                // Warm tone: increase red/yellow
                break;
            case "COOL":
                // Cool tone: increase blue/cyan
                break;
            case "MONOCHROME":
                // Grayscale effect
                break;
            default:
                // Normal (no effect)
                break;
        }
    }
    
    /**
     * Apply atmospheric fog effect
     */
    private static void applyFogEffect() {
        // Adjust fog based on fogDensity parameter
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.worldRenderer != null) {
            // Fog will be applied through rendering
        }
    }
    
    /**
     * Configuration methods
     */
    public static void setEnabled(boolean value) {
        enabled = value;
        if (!enabled) {
            particles.clear();
            trails.clear();
        }
    }
    
    public static void setParticleType(String type) {
        particleType = type;  // HEART, SQUARE, CIRCLE, PIXEL, TEXT
    }
    
    public static void setParticleCount(int count) {
        particleCount = Math.max(1, Math.min(30, count));
    }
    
    public static void setParticleSize(float size) {
        particleSize = Math.max(0.1f, Math.min(2.0f, size));
    }
    
    public static void setTrailType(String type) {
        trailType = type;  // LINEAR, RIBBON, PARTICLE
    }
    
    public static void setTrailsEnabled(boolean value) {
        trailsEnabled = value;
    }
    
    public static void setColorGradingType(String type) {
        colorGradingType = type;  // NORMAL, WARM, COOL, MONOCHROME
    }
    
    public static void setFogDensity(float density) {
        fogDensity = Math.max(0f, Math.min(1.0f, density));
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
    
    /**
     * Inner class for particle representation
     */
    public static class Particle {
        private Vec3d pos;
        private Vec3d vel;
        private String type;
        private float size;
        private int lifetime;
        private int age;
        
        public Particle(Vec3d pos, Vec3d vel, String type, float size, int lifetime) {
            this.pos = pos;
            this.vel = vel;
            this.type = type;
            this.size = size;
            this.lifetime = lifetime;
            this.age = 0;
        }
        
        public void update() {
            age++;
            pos = pos.add(vel);
            vel = vel.multiply(0.98);  // Damping
            vel = vel.add(0, -0.02, 0);  // Gravity
        }
        
        public boolean isExpired() {
            return age >= lifetime;
        }
    }
    
    /**
     * Inner class for trail representation
     */
    public static class TrailPoint {
        private Vec3d pos;
        private String type;
        private int lifetime;
        private int age;
        
        public TrailPoint(Vec3d pos, String type, int lifetime) {
            this.pos = pos;
            this.type = type;
            this.lifetime = lifetime;
            this.age = 0;
        }
        
        public void update() {
            age++;
        }
        
        public boolean isExpired() {
            return age >= lifetime;
        }
    }
}
