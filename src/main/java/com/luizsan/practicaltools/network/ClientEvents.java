package com.luizsan.practicaltools.network;

import com.luizsan.practicaltools.ModPracticalTools;
import com.luizsan.practicaltools.items.GreatswordItem;
// import com.luizsan.practicaltools.util.Debug;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ModPracticalTools.MOD_ID)
public final class ClientEvents {
    private ClientEvents() {}

    @SubscribeEvent
    public static void onClick(InputEvent.ClickInputEvent event) {
        if (event.isAttack()) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            HitResult raytrace = mc.hitResult;

            if (player != null && (raytrace == null || raytrace.getType() == HitResult.Type.MISS)) {
                ItemStack stack = player.getMainHandItem();
                // Debug.Message("Click event", false);
                // Debug.Message(Network.channel.toString(), true);
                if (stack.getItem() instanceof GreatswordItem) {
                    Network.channel.sendToServer(new LeftClickPacket());
                    // Debug.Message("Sent packet to server", false);
                }
            }
        }
    }

}

