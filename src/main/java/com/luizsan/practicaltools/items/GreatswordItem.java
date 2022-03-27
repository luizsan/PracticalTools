// Copyright (c) 2020 Alexander Strada - MIT License (This header, with links, must not be removed)
//     https://github.com/astradamus/PracticalTools
//     https://curseforge.com/minecraft/mc-mods/practical-tools
//     https://twitch.tv/neurodr0me

package com.luizsan.practicaltools.items;

import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.luizsan.practicaltools.ModConfig;
import com.luizsan.practicaltools.interfaces.IAoeTool;
// import com.luizsan.practicaltools.util.Debug;
import com.luizsan.practicaltools.util.ToolHelper;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class GreatswordItem extends SwordItem implements IAoeTool {

    public final Ingredient customRepair;
    
    // private static final Set<ToolAction> TOOL_ACTIONS =  Stream.of(ToolActions.AXE_DIG, ToolActions.PICKAXE_DIG, SHOVEL_DIG).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    protected static final Set<ToolAction> TOOL_ACTIONS =  ToolActions.DEFAULT_SWORD_ACTIONS;
    protected static final UUID REACH_MODIFIER_UUID = UUID.fromString("4ccbf2c3-b98e-4901-86f4-393fa195101b");

    public GreatswordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties, Ingredient customRepair) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
        this.customRepair = customRepair;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack){
        int range = ModConfig.COMMON.greatswordBonusRange.get();
        Multimap<Attribute, AttributeModifier> map = LinkedHashMultimap.create(getDefaultAttributeModifiers(slot));

        if(slot == EquipmentSlot.MAINHAND){
            ForgeMod.REACH_DISTANCE.ifPresent(attr -> {
                AttributeModifier mod = new AttributeModifier(REACH_MODIFIER_UUID, "Reach modifier", range, AttributeModifier.Operation.ADDITION);
                map.put(attr, mod);
            });
        }

        return map;
    }

    public BlockHitResult rayTraceBlocks(Level world, Player player) {
        return getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return customRepair.test(repair);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity a, LivingEntity b) {
        super.hurtEnemy(stack, a, b);
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction){
        return TOOL_ACTIONS.contains(toolAction);
    }

    @Override
    public AABB getSweepHitBox(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull Entity target){
        // Debug.Message("Retrieve sweep hitbox", false);
        return target.getBoundingBox().inflate(6.0d, 1d, 6.0d);
    }

    public static void onGreatswordSwing(ItemStack stack, LivingEntity wielder) {
        // if (wielder instanceof Player && stack.getItem() instanceof GreatswordItem) {
            ToolHelper.tryAttackWithExtraReach((Player) wielder);
            // Entity entity = tryAttackWithExtraReach((Player) wielder, false);
            // if(entity != null){
            //     Debug.Message("HIT: " + entity.getName().getString(), false);
            // }else{
            //     Debug.Message("MISS!", false);
            // }
        // }
    }

}
