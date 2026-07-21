# FPS Boost (Forge 1.20.1)

Client-side mod that automatically applies safe, reversible video-setting
tweaks to keep FPS high: minimal particles, fast graphics, clouds off,
capped entity render distance, a heavy FPS cap while the window isn't
focused, and a temporary render-distance drop while flying/riding fast.

## Build
1. Install JDK 17 and have internet access (Gradle needs to download Forge).
2. From this folder: `./gradlew build` (or `gradlew.bat build` on Windows —
   run `gradle wrapper` once first if the `gradlew` script is missing).
3. Output jar: `build/libs/fpsboost-1.0.0.jar`.
4. Drop it in your Forge 1.20.1 `mods` folder.

## Configure
After first launch, edit `config/fpsboost-client.toml` to toggle each
optimization individually.
