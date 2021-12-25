// Copyright (c) 2020 Alexander Strada - MIT License (This header, with links, must not be removed)
//     https://github.com/astradamus/PracticalTools
//     https://curseforge.com/minecraft/mc-mods/practical-tools
//     https://twitch.tv/neurodr0me

package com.luizsan.practicaltools;

import com.luizsan.practicaltools.items.IAoeTool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;

public class AreaBreak {

    /** Attempt to break blocks around the given pos in a 3x3x1 square relative to the targeted face.*/
    public static void areaAttempt(IAoeTool tool, Level world, BlockPos pos, Player player, Tag<Block> effectiveOn, boolean checkHarvestLevel) {
        BlockHitResult block = tool.rayTraceBlocks(world, player);

        if(block == null) return;
        Direction face = block.getDirection();

        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (a == 0 && b == 0) continue;

                BlockPos target = null;

                if (face == Direction.UP    || face == Direction.DOWN)  target = pos.offset(a, 0, b);
                // if (face == Direction.UP    || face == Direction.DOWN)  target = pos.add(a, 0, b);
                if (face == Direction.NORTH || face == Direction.SOUTH) target = pos.offset(a, b, 0);
                // if (face == Direction.NORTH || face == Direction.SOUTH) target = pos.add(a, b, 0);
                if (face == Direction.EAST  || face == Direction.WEST)  target = pos.offset(0, a, b);
                // if (face == Direction.EAST  || face == Direction.WEST)  target = pos.add(0, a, b);

                attemptBreak(world, target, pos, player, effectiveOn, checkHarvestLevel);
            }
        }
    }

    /** Attempt to break a block. Fails if the tool is not effective on the given block, or if the origin broken block
     * is significantly easier to break than the target block (i.e. don't let players use sandstone to quickly area
     * break obsidian).*/
    public static void attemptBreak(Level world, BlockPos target, BlockPos origin, Player player, Tag<Block> effectiveOn, boolean checkHarvestLevel) {

        BlockState targetState = world.getBlockState(target);

        if (checkHarvestLevel && !ForgeHooks.isCorrectToolForDrops(targetState, player)) {
        // if (checkHarvestLevel && !ForgeHooks.canHarvestBlock(state, player, world, target)) {
            return; // We are checking harvest level and this tool doesn't qualify.
        }

        if (!effectiveOn.contains(targetState.getBlock())) {
            return; // This tool is not effective on this block.
        }

        // Prevent players from using low-hardness blocks to quickly break adjacent high-hardness blocks.
        if (origin != null) {
            BlockState originState = world.getBlockState(origin);
            float originHard = originState.getBlock().defaultDestroyTime();
            // float originHard = originState.getPlayerRelativeBlockHardness(player, world, origin);
            float targetHard = targetState.getBlock().defaultDestroyTime();
            // float targetHard = targetState.getPlayerRelativeBlockHardness(player, world, target);
            if (targetHard <= 0f || originHard / targetHard < 0.5f) {
                return;
            }
        }

        Block block = targetState.getBlock();
        if (block.onDestroyedByPlayer(targetState, world, target, player, true, world.getFluidState(target))) {

            block.playerWillDestroy(world, target, targetState, player);
            // block.onPlayerDestroy(world, target, state);

            block.playerDestroy(world, player, target, targetState, null, player.getMainHandItem());
            // block.harvestBlock(world, player, target, targetState, world.getTileEntity(target), player.getHeldItemMainhand());

            int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player);
            int silkLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player);
            int expDropped = targetState.getExpDrop(world, target, fortuneLevel, silkLevel); 

            block.popExperience((ServerLevel)world, target, expDropped);
            //block.dropXpOnBlockBreak((ServerLevel) world, target, expDropped);
        }
    }

    /** Attempt to break a block. Fails if the tool is not effective on the given block.*/
    public static void attemptBreak(Level world, BlockPos target, Player player, Tag<Block> effectiveOn, boolean checkHarvestLevel) {
        attemptBreak(world, target, null, player, effectiveOn, checkHarvestLevel);
    }
}
