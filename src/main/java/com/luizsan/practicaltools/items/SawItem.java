// Copyright (c) 2020 Alexander Strada - MIT License (This header, with links, must not be removed)
//     https://github.com/astradamus/PracticalTools
//     https://curseforge.com/minecraft/mc-mods/practical-tools
//     https://twitch.tv/neurodr0me

package com.luizsan.practicaltools.items;

import java.util.ArrayList;

import com.luizsan.practicaltools.util.AreaBreak;
import com.luizsan.practicaltools.ModConfig;
import com.luizsan.practicaltools.interfaces.IAoeTool;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class SawItem extends AxeItem implements IAoeTool {

    public static final int LOG_BREAK_DELAY = 1;
    public final Ingredient customRepair;

    public SawItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder, Ingredient customRepair) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.customRepair = customRepair;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        stack.hurt(ModConfig.COMMON.sawUsageMultiplier.get()-1, ModConfig.random, null);
        if (entityLiving instanceof Player) {
            Player player = (Player) entityLiving;
            if (!attemptFellTree(world, pos, player)) {
                AreaBreak.areaAttempt(this, world, pos, player, BlockTags.MINEABLE_WITH_AXE, false);
            }
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

    /** Determines if the destroyed block was part of a tree and, if so, breaks all connected logs and returns true.*/
    private boolean attemptFellTree(Level world, BlockPos pos, Player player) {

        ArrayList<BlockPos> logs = new ArrayList<>();
        ArrayList<BlockPos> candidates = new ArrayList<>();
        candidates.add(pos);

        int leaves = 0;

        // Find all connected logs and count all connected leaves
        for (int i = 0; i < candidates.size(); i++) {

            // Whatever this is, it's too big! I don't want to know what happens if I let you use this in an all-log RFTDim.
            if (logs.size() > 200) return false; 

            BlockPos candidate = candidates.get(i);
            Block block = world.getBlockState(candidate).getBlock();

            if (BlockTags.LEAVES.contains(block)) {
                leaves++;
            }
            else if (logs.size() == 0 || BlockTags.LOGS.contains(block)) {
                logs.add(candidate);

                // We found a log, check for neighboring logs
                for (int x = -1; x <= 1; x++) {
                    for (int y = 0; y <= 1; y++) { // No good reason to check downwards, cuts 1/3 off this loop
                        for (int z = -1; z <= 1; z++) {
                            BlockPos neighbor = candidate.offset(x, y, z);
                            if (candidates.contains(neighbor)){
                                // Don't check positions twice
                                continue; 
                            }
                            candidates.add(neighbor);
                        }
                    }
                }
            }
        }

        // No logs? No tree.
        if (logs.size() == 0) return false; 

        // Trees have leaves. Since we only count leaves adjacent to logs, we favor leaves a bit.
        if (leaves >= logs.size() / 6.0) { 

            // Break the tree. Spread across several ticks because doing all at once causes the game to stutter, even for small trees.
            MinecraftForge.EVENT_BUS.register(new Object() {
                int delay = LOG_BREAK_DELAY;
                int i = 0;

                @SubscribeEvent
                public void onTick(TickEvent.WorldTickEvent event) {
                    if (delay-- > 0) return;
                    delay = LOG_BREAK_DELAY;
                    if (i < logs.size()) {
                        BlockPos log = logs.get(i);
                        AreaBreak.attemptBreak(world, log, player, BlockTags.MINEABLE_WITH_AXE, false);
                        i++;
                    }
                    else {
                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                }
            });

            return true;
        }

        return false;
    }
}
