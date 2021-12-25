package com.luizsan.practicaltools.items;

import javax.annotation.Nullable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public interface IAoeTool {
    /**
     * Call {@link net.minecraft.world.item.Item}'s {@code rayTrace} method inside this.
     *
     * @param world  The world
     * @param player The player
     * @return The ray trace result
     */
    @Nullable
    BlockHitResult rayTraceBlocks(Level world, Player player);
}
