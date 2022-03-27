// Copyright (c) 2020 Alexander Strada - MIT License (This header, with links, must not be removed)
//     https://github.com/astradamus/PracticalTools
//     https://curseforge.com/minecraft/mc-mods/practical-tools
//     https://twitch.tv/neurodr0me

package com.luizsan.practicaltools.items;

import com.luizsan.practicaltools.util.AreaBreak;
import com.luizsan.practicaltools.ModConfig;
import com.luizsan.practicaltools.interfaces.IAoeTool;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;


public class HammerItem extends PickaxeItem implements IAoeTool {

    public final Ingredient customRepair;

    public HammerItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder, Ingredient customRepair) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.customRepair = customRepair;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        stack.hurt(ModConfig.COMMON.hammerUsageMultiplier.get()-1, ModConfig.random, null);
        if (entityLiving instanceof Player){
            AreaBreak.areaAttempt(this, world, pos, (Player) entityLiving, BlockTags.MINEABLE_WITH_PICKAXE, true);
        }
        return super.mineBlock(stack, world, state, pos, entityLiving);
    }

    public BlockHitResult rayTraceBlocks(Level world, Player player) {
        return getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return customRepair.test(repair);
    }
}
