package com.catmods.fpsboost.client;

import com.catmods.fpsboost.config.FpsBoostConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.ParticleStatus;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Applies lightweight, reversible client option tweaks every second to keep
 * FPS high, instead of risky mixins into the renderer internals.
 */
@Mod.EventBusSubscriber(modid = "fpsboost", value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class ClientOptimizer {

    private static boolean appliedBaseline = false;
    private static int tickCounter = 0;
    private static Integer savedRenderDistance = null;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.options == null) return;

        Options options = mc.options;

        if (!appliedBaseline) {
            applyBaseline(mc, options);
            appliedBaseline = true;
        }

        tickCounter++;
        if (tickCounter % 20 != 0) return; // once per second is plenty

        handleFocus(mc, options);
        handleFastMovement(mc, options);
    }

    private static void applyBaseline(Minecraft mc, Options options) {
        if (FpsBoostConfig.REDUCE_PARTICLES.get()) {
            options.particles().set(ParticleStatus.MINIMAL);
        }
        if (FpsBoostConfig.DISABLE_FANCY_GRAPHICS.get()) {
            options.graphicsMode().set(net.minecraft.client.GraphicsStatus.FAST);
        }
        if (FpsBoostConfig.DISABLE_CLOUDS.get()) {
            options.cloudStatus().set(net.minecraft.client.CloudStatus.OFF);
        }
        if (FpsBoostConfig.LIMIT_ENTITY_RENDER_DISTANCE.get()) {
            options.entityDistanceScaling().set(FpsBoostConfig.ENTITY_RENDER_DISTANCE.get() / 100.0);
        }
        mc.options.save();
    }

    private static void handleFocus(Minecraft mc, Options options) {
        if (!FpsBoostConfig.CAP_FPS_WHEN_UNFOCUSED.get()) return;
        OptionInstance<Integer> fpsOption = options.framerateLimit();
        if (!mc.isWindowActive()) {
            if (fpsOption.get() != FpsBoostConfig.UNFOCUSED_FPS_LIMIT.get()) {
                fpsOption.set(FpsBoostConfig.UNFOCUSED_FPS_LIMIT.get());
            }
        }
    }

    private static void handleFastMovement(Minecraft mc, Options options) {
        if (!FpsBoostConfig.REDUCE_RENDER_DISTANCE_WHEN_MOVING_FAST.get()) return;
        LivingEntity player = mc.player;
        if (player == null) return;

        boolean movingFast = player.isFallFlying() || (player.getVehicle() != null && player.getVehicle().getDeltaMovement().horizontalDistanceSqr() > 0.3);

        OptionInstance<Integer> renderDistance = options.renderDistance();
        if (movingFast) {
            if (savedRenderDistance == null) {
                savedRenderDistance = renderDistance.get();
                renderDistance.set(Math.max(4, savedRenderDistance - 4));
            }
        } else if (savedRenderDistance != null) {
            renderDistance.set(savedRenderDistance);
            savedRenderDistance = null;
        }
    }
}
