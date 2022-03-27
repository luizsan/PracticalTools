package com.luizsan.practicaltools.util;

import java.util.function.Predicate;

import javax.annotation.Nullable;

// import com.luizsan.practicaltools.ModConfig;
// import com.luizsan.practicaltools.util.Debug;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

public class ToolHelper {
    
    @Nullable
    public static EntityHitResult getEntityHitResult(Entity shooter, Vec3 start, Vec3 end, AABB boundingBox, Predicate<Entity> entityFilter, double distance){
        return ProjectileUtil.getEntityHitResult(shooter, start, end, boundingBox, entityFilter, distance);
    }

    @Nullable
    public static Entity tryAttackWithExtraReach(Player player) {
        // Attempt to attack something if wielding a weapon with increased melee range
        double range = getAttackRange(player);
        Vec3 vector3d = player.getEyePosition(0f);
        double rangeSquared = range * range;

        Vec3 vector3d1 = player.getViewVector(1.0F);
        Vec3 vector3d2 = vector3d.add(vector3d1.x * range, vector3d1.y * range, vector3d1.z * range);
        AABB axisalignedbb = player.getBoundingBox().expandTowards(vector3d1.scale(range)).inflate(1.0D, 1.0D, 1.0D);

        EntityHitResult rayTrace = getEntityHitResult(player, vector3d, vector3d2, axisalignedbb, (entity) -> {
            return !entity.isSpectator() && entity.isPickable();
        }, rangeSquared);

        if (rayTrace != null) {
            Entity entity = rayTrace.getEntity();
            player.attack(entity);
            return entity;
        }

        return null;
    }

    public static double getAttackRange(LivingEntity entity) {
        AttributeInstance attribute = entity.getAttribute(ForgeMod.REACH_DISTANCE.get());
        if (attribute != null) {

            // Debug.Message("Reach base: " + attribute.getBaseValue(), false);
            // Debug.Message("Reach bonus: " + ModConfig.COMMON.greatswordBonusRange.get(), false);
            // Debug.Message("Reach final: " + attribute.getValue(), false);

            return attribute.getBaseValue();
        }

        return entity.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
    }



}
