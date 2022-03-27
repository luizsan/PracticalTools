package com.luizsan.practicaltools;

import net.minecraft.world.level.block.Blocks;

import com.luizsan.practicaltools.items.ExcavatorItem;
import com.luizsan.practicaltools.items.GreatswordItem;
import com.luizsan.practicaltools.items.HammerItem;
import com.luizsan.practicaltools.items.SawItem;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static Item IRON_EXCAVATOR;
    public static Item GOLDEN_EXCAVATOR;
    public static Item DIAMOND_EXCAVATOR;
    public static Item NETHERITE_EXCAVATOR;

    public static Item IRON_GREATSWORD;
    public static Item GOLDEN_GREATSWORD;
    public static Item DIAMOND_GREATSWORD;
    public static Item NETHERITE_GREATSWORD;

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

        IRON_EXCAVATOR = CreateExcavator(Tiers.IRON, ironBlock, "iron_excavator");
        GOLDEN_EXCAVATOR = CreateExcavator(Tiers.GOLD, goldBlock, "golden_excavator");
        DIAMOND_EXCAVATOR = CreateExcavator(Tiers.DIAMOND, diamondBlock, "diamond_excavator");
        NETHERITE_EXCAVATOR = CreateExcavator(Tiers.NETHERITE, netheriteBlock, "netherite_excavator");

        IRON_GREATSWORD = CreateGreatsword(Tiers.IRON, ironBlock, "iron_greatsword");
        GOLDEN_GREATSWORD = CreateGreatsword(Tiers.GOLD, goldBlock, "golden_greatsword");
        DIAMOND_GREATSWORD = CreateGreatsword(Tiers.DIAMOND, diamondBlock, "diamond_greatsword");
        NETHERITE_GREATSWORD = CreateGreatsword(Tiers.NETHERITE, netheriteBlock, "netherite_greatsword");

        IRON_HAMMER = CreateHammer(Tiers.IRON, ironBlock, "iron_hammer");
        GOLDEN_HAMMER = CreateHammer(Tiers.GOLD, goldBlock, "golden_hammer");
        DIAMOND_HAMMER = CreateHammer(Tiers.DIAMOND, diamondBlock, "diamond_hammer");
        NETHERITE_HAMMER = CreateHammer(Tiers.NETHERITE, netheriteBlock, "netherite_hammer");

        IRON_SAW = CreateSaw(Tiers.IRON, ironBlock, "iron_saw");
        GOLDEN_SAW = CreateSaw(Tiers.GOLD, goldBlock, "golden_saw");
        DIAMOND_SAW = CreateSaw(Tiers.DIAMOND, diamondBlock, "diamond_saw");
        NETHERITE_SAW = CreateSaw(Tiers.NETHERITE, netheriteBlock, "netherite_saw");

        GOLDEN_SHEARS = CreateShears(Tiers.GOLD, goldBlock, "golden_shears");
        DIAMOND_SHEARS = CreateShears(Tiers.DIAMOND, diamondBlock, "diamond_shears");
        NETHERITE_SHEARS = CreateShears(Tiers.NETHERITE, netheriteBlock, "netherite_shears");

        IForgeRegistry<Item> forge_registry = event.getRegistry();

        forge_registry.register(IRON_EXCAVATOR);
        forge_registry.register(GOLDEN_EXCAVATOR);
        forge_registry.register(DIAMOND_EXCAVATOR);
        forge_registry.register(NETHERITE_EXCAVATOR);

        forge_registry.register(IRON_GREATSWORD);
        forge_registry.register(GOLDEN_GREATSWORD);
        forge_registry.register(DIAMOND_GREATSWORD);
        forge_registry.register(NETHERITE_GREATSWORD);

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

    private static Item CreateExcavator(Tiers tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.of(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new ExcavatorItem(tier, 1.5f, -3.0f, props.tab(CreativeModeTab.TAB_TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item CreateGreatsword(Tiers tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.of(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new GreatswordItem(tier, 8, -3.0f, props.tab(CreativeModeTab.TAB_TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item CreateHammer(Tiers tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.of(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new HammerItem(tier, 1, -2.8f, props.tab(CreativeModeTab.TAB_TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item CreateSaw(Tiers tier, Item customRepair, String registryName) {
        Ingredient ingredient = Ingredient.of(customRepair);
        Item.Properties props = flagIfNetherite(new Item.Properties(), tier);
        return new SawItem(tier, 0f, 0f, props.tab(CreativeModeTab.TAB_TOOLS), ingredient).setRegistryName(registryName);
    }

    private static Item CreateShears(Tiers tier, Item customRepair, String registryName) {
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
