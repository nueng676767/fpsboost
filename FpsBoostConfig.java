package com.catmods.fpsboost.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class FpsBoostConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue REDUCE_PARTICLES;
    public static final ForgeConfigSpec.BooleanValue DISABLE_CLOUDS;
    public static final ForgeConfigSpec.BooleanValue LIMIT_ENTITY_RENDER_DISTANCE;
    public static final ForgeConfigSpec.IntValue ENTITY_RENDER_DISTANCE;
    public static final ForgeConfigSpec.BooleanValue DISABLE_FANCY_GRAPHICS;
    public static final ForgeConfigSpec.BooleanValue CAP_FPS_WHEN_UNFOCUSED;
    public static final ForgeConfigSpec.IntValue UNFOCUSED_FPS_LIMIT;
    public static final ForgeConfigSpec.BooleanValue REDUCE_RENDER_DISTANCE_WHEN_MOVING_FAST;

    static {
        BUILDER.push("FPS Boost Settings");

        REDUCE_PARTICLES = BUILDER
                .comment("Automatically set particle count to Minimal")
                .define("reduceParticles", true);

        DISABLE_CLOUDS = BUILDER
                .comment("Disable cloud rendering (large FPS gain on weak GPUs)")
                .define("disableClouds", true);

        DISABLE_FANCY_GRAPHICS = BUILDER
                .comment("Force Fast graphics instead of Fancy/Fabulous")
                .define("disableFancyGraphics", true);

        LIMIT_ENTITY_RENDER_DISTANCE = BUILDER
                .comment("Cap the entity distance scaling option")
                .define("limitEntityRenderDistance", true);

        ENTITY_RENDER_DISTANCE = BUILDER
                .comment("Entity distance percentage (25-100)")
                .defineInRange("entityRenderDistance", 60, 25, 100);

        CAP_FPS_WHEN_UNFOCUSED = BUILDER
                .comment("Limit FPS heavily when the game window is not focused")
                .define("capFpsWhenUnfocused", true);

        UNFOCUSED_FPS_LIMIT = BUILDER
                .comment("FPS cap to use while unfocused")
                .defineInRange("unfocusedFpsLimit", 15, 1, 60);

        REDUCE_RENDER_DISTANCE_WHEN_MOVING_FAST = BUILDER
                .comment("Temporarily lower render distance while flying/sprinting on a fast mount (elytra, horse)")
                .define("reduceRenderDistanceWhenMovingFast", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
