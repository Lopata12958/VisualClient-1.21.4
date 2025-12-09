# VisualClient 1.21.4

A modern Minecraft 1.21.4 Fabric client-side visual mod featuring multiple visual effect modules with a complete ClickGUI interface.

## Features

### PulseVisuals Module
- Screen pulse effects on damage
- HUD element pulsing (health bar, hotbar, crosshair)
- Smooth screen shake on hits
- Customizable intensity, speed, and duration
- Individual toggle controls for each effect type

### LaminarVisuals Module
- Custom hit particles (hearts, squares, circles, pixels, text)
- Trail effects for players and targets
- Multiple trail types (linear, ribbon, particle)
- Color grading and world fog effects
- Full customization panel for particles and trails

### OpkaVisuals Module
- Hit markers with smooth animations
- Custom weapon swing animations
- Weapon glow/outline effects
- Customizable crosshair with animations
- Adjustable hit marker styling

### TopkaVisuals Module
- Kill and combo visualization system
- Combat statistics display
- Target HUD with player info
- Speed and strafe indicators
- Smooth UI animations with easing

### ClickGUI
- Modern drag-and-drop interface
- Right Shift to toggle (customizable)
- Smooth animations and gradient themes
- Multiple tabs: Pulse, Laminar, Opka, Topka, Misc, Config
- Color picker support (RGB/HSV)
- Keybind configuration
- Save/load preset system

## Installation

### Requirements
- Minecraft 1.21.4
- Fabric Loader 0.15.11+
- Fabric API 0.104.0+
- Java 21+

### Setup
1. Clone this repository
   ```bash
   git clone https://github.com/Lopata12958/VisualClient-1.21.4.git
   cd VisualClient-1.21.4
   ```

2. Build the mod
   ```bash
   ./gradlew build
   ```

3. Install to Minecraft
   - Copy `build/libs/visualclient-1.0.0.jar` to `.minecraft/mods/`

## Project Structure

```
VisualClient-1.21.4/
├── src/main/java/com/visualclient/
│   ├── VisualClientMod.java
│   ├── features/
│   │   ├── visuals/
│   │   │   ├── pulse/
│   │   │   │   └── PulseVisualsModule.java
│   │   │   ├── laminar/
│   │   │   │   └── LaminarVisualsModule.java
│   │   │   ├── opka/
│   │   │   │   └── OpkaVisualsModule.java
│   │   │   └── topka/
│   │   │       └── TopkaVisualsModule.java
│   ├── gui/
│   │   ├── ClickGUI.java
│   │   └── screens/
│   ├── config/
│   │   ├── ConfigManager.java
│   │   └── ModConfig.java
│   ├── render/
│   │   ├── RenderUtils.java
│   │   ├── particles/
│   │   └── effects/
│   └── util/
│       ├── ColorUtils.java
│       ├── AnimationUtils.java
│       └── MathUtils.java
├── src/main/resources/
│   └── fabric.mod.json
├── build.gradle.kts
├── gradle.properties
├── LICENSE
├── .gitignore
└── README.md
```

## Configuration

Configurations are saved in `.minecraft/config/visualclient/config.json`.

Example configuration structure:
```json
{
  "pulse": {
    "enabled": true,
    "intensity": 0.8,
    "duration": 300
  },
  "laminar": {
    "enabled": true,
    "particleType": "HEART"
  },
  "opka": {
    "enabled": true,
    "hitMarker": true
  },
  "topka": {
    "enabled": true,
    "showCombo": true
  }
}
```

## Code Examples

### PulseVisuals Module
```java
public class PulseVisualsModule extends VisualModule {
    private float damageIntensity = 1.0f;
    private int effectDuration = 300;
    
    @Override
    public void onDamageReceived(DamageEvent event) {
        // Trigger pulse animation
        startScreenPulse(damageIntensity, effectDuration);
    }
}
```

### Custom Particles
```java
public class ParticleRenderer {
    public void spawnHitParticle(Vec3d pos, Vec3d vel) {
        // Create custom particle effect
        Particle particle = new CustomParticle(pos, vel);
        particle.setColor(ColorUtils.getRandomColor());
        particle.setLifetime(300);
    }
}
```

### ClickGUI Usage
Press **Right Shift** to toggle the ClickGUI. Use the interface to:
- Enable/disable individual modules
- Adjust sliders for effect parameters
- Configure colors with the color picker
- Set custom keybinds
- Save/load configuration presets

## Building from Source

```bash
# Clone repository
git clone https://github.com/Lopata12958/VisualClient-1.21.4.git
cd VisualClient-1.21.4

# Build JAR
./gradlew build

# Output location
# build/libs/visualclient-1.0.0.jar
```

## Contributing

Contributions are welcome! Please ensure:
1. Code follows existing style conventions
2. All modules are properly documented
3. Features don't copy existing paid mod implementations
4. Test builds pass with `./gradlew build`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Disclaimer

This mod is intended for single-player use and for educational purposes. Use in multiplayer servers at your own discretion and in accordance with server rules. This mod does not provide any unfair advantages that could be considered cheating on competitive servers.

## Support

For issues, feature requests, or questions, please open an issue on GitHub.

---

**Status**: Development
**Version**: 1.0.0
**Minecraft Version**: 1.21.4
**Fabric Loader**: 0.15.11+
