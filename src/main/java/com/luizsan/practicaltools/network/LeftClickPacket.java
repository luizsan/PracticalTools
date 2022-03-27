package com.luizsan.practicaltools.network;

import java.util.function.Supplier;

import com.luizsan.practicaltools.items.GreatswordItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class LeftClickPacket {

    public LeftClickPacket() {}

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            // Debug.Message("Packet handled and player isn't null", false);
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof GreatswordItem) {
                GreatswordItem.onGreatswordSwing(stack, player);
                // Debug.Message("Item swing packet", false);
            }
        }
    }

    public static LeftClickPacket decode(FriendlyByteBuf buffer) {
        return new LeftClickPacket();
    }

    public void encode(FriendlyByteBuf buffer) {}
}