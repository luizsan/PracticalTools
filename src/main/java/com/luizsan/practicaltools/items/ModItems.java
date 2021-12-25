// Copyright (c) 2020 Alexander Strada - MIT License (This header, with links, must not be removed)
//     https://github.com/astradamus/PracticalTools
//     https://curseforge.com/minecraft/mc-mods/practical-tools
//     https://twitch.tv/neurodr0me

package com.luizsan.practicaltools.items;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static Item IRON_EXCAVATOR;
    public static Item GOLDEN_EXCAVATOR;
    public static Item DIAMOND_EXCAVATOR;
    public static Item NETHERITE_EXCAVATOR;

    public static Item IRON_HAMMER;
    public static Item GOLDEN_HAMMER;
    public static Item DIAMOND_HAMMER;
    public static Item NETHERITE_HAMMER;

    public static Item IRON_SAW;
    public static Item GOLDEN_SAW;
    public static Item DIAMOND_SAW;
    public static Item NETHERITE_SAW;

    public static Item GOLDEN_SHEARS;
    public static Item DIAMOND_SHEARS;
    public static Item NETHERITE_SHEARS;

    public static void init(RegistryEvent.Register<Item> event) {
        Item ironBlock = Item.BY_BLOCK.get(Blocks.IRON_BLOCK);
        Item goldBlock = Item.BY_BLOCK.get(Blocks.GOLD_BLOCK);
        Item diamondBlock = Item.BY_BLOCK.get(Blocks.DIAMOND_BLOCK);
        Item netheriteBlock = Item.BY_BLOCK.get(Blocks.NETHERITE_BLOCK);

        IRON_EXCAVATOR = mkExcavator(Tiers.IRON, ironBlock, "iron_excavator");
        GOLDEN_EXCAVATOR = mkExcavator(Tiers.GOLD, goldBlock, "golden_excavator");
        DIAMOND_EXCAVATOR = mkExcavator(Tiers.DIAMOND, diamondBlock, "diamond_excavator");
        NETHERITE_EXCAVATOR = mkExcavator(Tiers.NETHERITE, netheriteBlock, "netherite_excavator");

        IRON_HAMMER = mkHammer(Tiers.IRON, ironBlock, "iron_hammer");
        GOLDEN_HAMMER = mkHammer(Tiers.GOLD, goldBlock, "golden_hammer");
        DIAMOND_HAMMER = mkHammer(Tiers.DIAMOND, diamondBlock, "diamond_hammer");
        NETHERITE_HAMMER = mkHammer(Tiers.NETHERITE, netheriteBlock, "netherite_hammer");

        IRON_SAW = mkSaw(Tiers.IRON, ironBlock, "iron_saw");
        GOLDEN_SAW = mkSaw(Tiers.GOLD, goldBlock, "golden_saw");
        DIAMOND_SAW = mkSaw(Tiers.DIAMOND, diamondBlock, "diamond_saw");
        NETHERITE_SAW = mkSaw(Tiers.NETHERITE, netheriteBlock, "netherite_saw");

        GOLDEN_SHEARS = mkShears(Tiers.GOLD, goldBlock, "golden_shears");
        DIAMOND_SHEARS = mkShears(Tiers.DIAMOND, diamondBlock, "diamond_shears");
        NETHERITE_SHEARS = mkShears(Tiers.NETHERITE, netheriteBlock, "netherite_shears");

        IForgeRegistry<Item> forge_registry = event.getRegistry();

        forge_registry.register(IRON_EXCAVATOR);
        forge_registry.register(GOLDEN_EXCAVATOR);
        forge_registry.register(DIAMOND_EXCAVATOR);
        forge_registry.register(NETHERITE_EXCAVATOR);

        forge_registry.register(IRON_HAMMER);
        forge_registry.register(GOLDEN_HAMMER);
        forge_registry.register(DIAMOND_HAMMER);
        forge_registry.register(NETHERITE_HAMMER);

        forge_registry.register(IRON_SAW);
        forge_registry.register(GOLDEN_SAW);
        forge_registry.register(DIAMOND_SAW);
        forge_registry.register(NETHERITE_SAW);

        forge_registry.register(GOLDEN_SHEARS);
        forge_registry.register(DIAMOND_SHEARS);
        forge_registry.register(NETHERITE_SHEARS);
    }

    private static Item mkExcavator(Tiers tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.of(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new ExcavatorItem(tier, 1.5f, -3.0f, props.tab(CreativeModeTab.TAB_TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item mkHammer(Tiers tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.of(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new HammerItem(tier, 1, -2.8f, props.tab(CreativeModeTab.TAB_TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item mkSaw(Tiers tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.of(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new SawItem(tier, 0f, 0f, props.tab(CreativeModeTab.TAB_TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item mkShears(Tiers tier, Item customRepair, String registryName) {
        // Ingredient ingredient = Ingredient.of(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        int durability = tier.getUses();
        return new ShearsItem(props.tab(CreativeModeTab.TAB_TOOLS).defaultDurability(durability)).setRegistryName(registryName);
    }


    private static Item.Properties flagIfNetherite(Item.Properties props, Tiers tier) {
        // Items with this flag are not destroyed by fire damage.
        if (tier == Tiers.NETHERITE) {
            props.fireResistant(); 
        }
        return props;
    }
}
