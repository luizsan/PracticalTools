package com.luizsan.practicaltools.util;

import com.luizsan.practicaltools.ModPracticalTools;

import net.minecraft.resources.ResourceLocation;

public class ModResourceLocation extends ResourceLocation {
    public ModResourceLocation(String resourceName) {
        super(addModNamespace(resourceName));
    }

    private static String addModNamespace(String resourceName) {
        if (resourceName.contains(":")) {
            return resourceName;
        }
        return ModPracticalTools.MOD_ID + ":" + resourceName;
    }
}