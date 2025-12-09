package com.visualclient.config;

/**
 * Data class for storing VisualClient configuration.
 * All fields are automatically serialized to JSON.
 */
public class ModConfig {
    // PulseVisuals settings
    public PulseConfig pulse = new PulseConfig();
    
    // LaminarVisuals settings  
    public LaminarConfig laminar = new LaminarConfig();
    
    // OpkaVisuals settings
    public OpkaConfig opka = new OpkaConfig();
    
    // TopkaVisuals settings
    public TopkaConfig topka = new TopkaConfig();
    
    public static class PulseConfig {
        public boolean enabled = true;
        public float intensity = 1.0f;
        public int duration = 300;
        public int updateRate = 1;
    }
    
    public static class LaminarConfig {
        public boolean enabled = true;
        public String particleType = "HEART";
        public int particleCount = 8;
        public float particleSize = 0.5f;
        public String trailType = "LINEAR";
    }
    
    public static class OpkaConfig {
        public boolean enabled = true;
        public boolean hitMarker = true;
        public String crosshairType = "CROSS";
        public float crosshairSize = 1.0f;
    }
    
    public static class TopkaConfig {
        public boolean enabled = true;
        public boolean showCombo = true;
        public boolean showTarget = true;
        public float hudScale = 1.0f;
    }
}
