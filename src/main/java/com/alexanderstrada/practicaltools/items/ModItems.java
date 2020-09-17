// Copyright (c) 2020 Alexander Strada - MIT License (This header, with links, must not be removed)
//     https://github.com/astradamus/PracticalTools
//     https://curseforge.com/minecraft/mc-mods/practical-tools
//     https://twitch.tv/neurodr0me

package com.alexanderstrada.practicaltools.items;

import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;

public class ModItems {

    public static Item IRON_EXCAVATOR;
    public static Item GOLDEN_EXCAVATOR;
    public static Item DIAMOND_EXCAVATOR;
    public static Item NETHERITE_EXCAVATOR;

    public static Item IRON_HAMMER;
    public static Item GOLDEN_HAMMER;
    public static Item DIAMOND_HAMMER;
    public static Item NETHERITE_HAMMER;

    public static Item IRON_GREATAXE;
    public static Item GOLDEN_GREATAXE;
    public static Item DIAMOND_GREATAXE;
    public static Item NETHERITE_GREATAXE;

    public static void init(RegistryEvent.Register<Item> event) {
        Item ironBlock = Item.BLOCK_TO_ITEM.get(Blocks.IRON_BLOCK);
        Item goldBlock = Item.BLOCK_TO_ITEM.get(Blocks.GOLD_BLOCK);
        Item diamondBlock = Item.BLOCK_TO_ITEM.get(Blocks.DIAMOND_BLOCK);
        Item netheriteBlock = Item.BLOCK_TO_ITEM.get(Blocks.field_235397_ng_);

        IRON_EXCAVATOR = mkExcavator(ItemTier.IRON, ironBlock, "iron_excavator");
        GOLDEN_EXCAVATOR = mkExcavator(ItemTier.GOLD, goldBlock, "golden_excavator");
        DIAMOND_EXCAVATOR = mkExcavator(ItemTier.DIAMOND, diamondBlock, "diamond_excavator");
        NETHERITE_EXCAVATOR = mkExcavator(ItemTier.NETHERITE, netheriteBlock, "netherite_excavator");

        IRON_HAMMER = mkHammer(ItemTier.IRON, ironBlock, "iron_hammer");
        GOLDEN_HAMMER = mkHammer(ItemTier.GOLD, goldBlock, "golden_hammer");
        DIAMOND_HAMMER = mkHammer(ItemTier.DIAMOND, diamondBlock, "diamond_hammer");
        NETHERITE_HAMMER = mkHammer(ItemTier.NETHERITE, netheriteBlock, "netherite_hammer");

        IRON_GREATAXE = mkGreataxe(ItemTier.IRON, ironBlock, "iron_greataxe");
        GOLDEN_GREATAXE = mkGreataxe(ItemTier.GOLD, goldBlock, "golden_greataxe");
        DIAMOND_GREATAXE = mkGreataxe(ItemTier.DIAMOND, diamondBlock, "diamond_greataxe");
        NETHERITE_GREATAXE = mkGreataxe(ItemTier.NETHERITE, netheriteBlock, "netherite_greataxe");

        event.getRegistry().register(IRON_EXCAVATOR);
        event.getRegistry().register(GOLDEN_EXCAVATOR);
        event.getRegistry().register(DIAMOND_EXCAVATOR);
        event.getRegistry().register(NETHERITE_EXCAVATOR);

        event.getRegistry().register(IRON_HAMMER);
        event.getRegistry().register(GOLDEN_HAMMER);
        event.getRegistry().register(DIAMOND_HAMMER);
        event.getRegistry().register(NETHERITE_HAMMER);

        event.getRegistry().register(IRON_GREATAXE);
        event.getRegistry().register(GOLDEN_GREATAXE);
        event.getRegistry().register(DIAMOND_GREATAXE);
        event.getRegistry().register(NETHERITE_GREATAXE);
    }

    private static Item mkExcavator(ItemTier tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.fromItems(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new ExcavatorItem(tier, 1.5F, -3.0F, props.group(ItemGroup.TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item mkHammer(ItemTier tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.fromItems(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new HammerItem(tier, 1, -2.8F, props.group(ItemGroup.TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item mkGreataxe(ItemTier tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.fromItems(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new GreataxeItem(tier, 6F, -3.4F, props.group(ItemGroup.TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item.Properties flagIfNetherite(Item.Properties props, ItemTier tier) {
        if (tier == ItemTier.NETHERITE) {
            props.func_234689_a_(); // Items with this flag are not destroyed by fire damage.
        }
        return props;
    }
}
