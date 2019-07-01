// Copyright (c) 2019 Alexander Strada - MIT License (This header, with links, must not be removed)
//     https://github.com/astradamus/PracticalTools
//     https://curseforge.com/minecraft/mc-mods/practical-tools
//     https://twitch.tv/neurodr0me

package com.alexanderstrada.practicaltools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.Random;
import java.util.Set;

public class ToolFunctions {

    public static final Random random = new Random();

    /** Attempt to break blocks around the given pos in a 3x3x1 square relative to the targeted face.*/
    public static void attemptBreakNeighbors(World world, BlockPos pos, PlayerEntity player, Set<Block> effectiveOn, Set<Material> effectiveMaterials) {

        RayTraceResult trace = calcRayTrace(world, player, RayTraceContext.FluidMode.ANY);

        if (trace.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockTrace = (BlockRayTraceResult) trace;
            Direction face = blockTrace.getFace();

            int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
            int silkLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand());

            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    if (a == 0 && b == 0) continue;

                    BlockPos target = null;

                    if (face == Direction.UP    || face == Direction.DOWN)  target = pos.add(a, 0, b);
                    if (face == Direction.NORTH || face == Direction.SOUTH) target = pos.add(a, b, 0);
                    if (face == Direction.EAST  || face == Direction.WEST)  target = pos.add(0, a, b);

                    attemptBreak(world, target, player, effectiveOn, effectiveMaterials, fortuneLevel, silkLevel);
                }
            }
        }
    }

    /** To break, the given block must be contained in effectiveOn, or its material contained in effectiveMaterials, and
     * the block cannot have the wither-immune tag. Wither-immune seems to be the only comprehensive list of "blocks you
     * shouldn't be able to break" in the game. */
    public static void attemptBreak(World world, BlockPos pos, PlayerEntity player, Set<Block> effectiveOn, Set<Material> effectiveMaterials, int fortuneLevel, int silkLevel) {
        BlockState state = world.getBlockState(pos);

        boolean canHarvestBlock = player.getHeldItemMainhand().canHarvestBlock(state);
        boolean isEffective = effectiveOn.contains(state.getBlock()) || effectiveMaterials.contains(state.getMaterial());
        boolean witherImmune = BlockTags.WITHER_IMMUNE.contains(state.getBlock());

        if (canHarvestBlock && isEffective && !witherImmune) {
            world.destroyBlock(pos, false);
            Block.spawnDrops(state, world, pos, null, player, player.getHeldItemMainhand());

            int exp = state.getExpDrop(world, pos, fortuneLevel, silkLevel);
            if (exp > 0) {
                state.getBlock().dropXpOnBlockBreak(world, pos, exp);
            }
        }
    }

    /** Copy-pasted from "Item.rayTrace" which is protected static, making it unusable in my own static helper methods.*/
    public static RayTraceResult calcRayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vec3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue();;
        Vec3d vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }
}
