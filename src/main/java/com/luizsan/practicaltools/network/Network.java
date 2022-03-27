package com.luizsan.practicaltools.network;

import java.util.Objects;

import com.luizsan.practicaltools.ModPracticalTools;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Network {

    public static SimpleChannel channel;
    public static final String VERSION = "practicaltools-net-0-1";

    static { 
        channel = NetworkRegistry.ChannelBuilder.named(ModPracticalTools.getId("network"))
                .networkProtocolVersion(() -> VERSION)
                .clientAcceptedVersions(s -> Objects.equals(s, VERSION))
                .serverAcceptedVersions(s -> Objects.equals(s, VERSION))
                .simpleChannel();

        channel.messageBuilder(LeftClickPacket.class, 12, NetworkDirection.PLAY_TO_SERVER)
        .decoder(LeftClickPacket::decode)
        .encoder(LeftClickPacket::encode)
        .consumer(LeftClickPacket::handle)
        .add();
    }

    private Network(){}
    public static void init() {}
    
}
