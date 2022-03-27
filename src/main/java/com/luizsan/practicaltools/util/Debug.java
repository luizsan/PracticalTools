package com.luizsan.practicaltools.util;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
public class Debug {
	
	// @Deprecated
	public static void Message(String message, boolean chat) {
		Minecraft mc = Minecraft.getInstance();
		if (mc != null && mc.player != null){
			mc.player.displayClientMessage(new TextComponent(message), true);
			if(chat){
				mc.player.sendMessage(new TextComponent(message), Util.NIL_UUID);
			}else{
				mc.player.displayClientMessage(new TextComponent(message), false);
			}
        }
	}

}
