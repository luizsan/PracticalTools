// Copyright (c) 2020 Alexander Strada - MIT License (This header, with links, must not be removed)
//     https://github.com/astradamus/PracticalTools
//     https://curseforge.com/minecraft/mc-mods/practical-tools
//     https://twitch.tv/neurodr0me

package com.luizsan.practicaltools;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Random;

public class ModConfig {

    public static final Random random = new Random();
    public static Common COMMON;

    public static ForgeConfigSpec init(ForgeConfigSpec.Builder builder) {
        COMMON = new Common(builder);
        return builder.build();
    }

    public static class Common {

        public final ForgeConfigSpec.IntValue excavatorUsageMultiplier;
        public final ForgeConfigSpec.IntValue hammerUsageMultiplier;
        public final ForgeConfigSpec.IntValue sawUsageMultiplier;

        public final ForgeConfigSpec.IntValue greatswordBonusRange;

        public Common(ForgeConfigSpec.Builder builder) {

            builder.comment(" Practical Tools config \n These multipliers affect the amount of damage tools take per use.").push("common");
            excavatorUsageMultiplier = builder.defineInRange("excavator_usage_multiplier", 1, 1, Integer.MAX_VALUE);
            hammerUsageMultiplier = builder.defineInRange("hammer_usage_multiplier", 1, 1, Integer.MAX_VALUE);
            sawUsageMultiplier = builder.defineInRange("saw_usage_multiplier", 1, 1, Integer.MAX_VALUE);
            
            builder.comment(" Greatsword extra reach measured in blocks.");
            greatswordBonusRange = builder.defineInRange("greatsword_bonus_range", 2, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }
}
