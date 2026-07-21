package com.catmods.fpsboost;

import com.catmods.fpsboost.config.FpsBoostConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod("fpsboost")
public class FpsBoostMod {
    public FpsBoostMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FpsBoostConfig.SPEC);
    }
}
