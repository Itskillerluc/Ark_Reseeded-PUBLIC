package com.huskytacodile.alternacraft.networking;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.networking.packet.FlyPacket;
import com.huskytacodile.alternacraft.networking.packet.InventoryPacket;
import com.huskytacodile.alternacraft.networking.packet.LowerPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Alternacraft.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(FlyPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FlyPacket::new)
                .encoder(FlyPacket::toBytes)
                .consumerMainThread(FlyPacket::handle)
                .add();

        net.messageBuilder(LowerPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LowerPacket::new)
                .encoder(LowerPacket::toBytes)
                .consumerMainThread(LowerPacket::handle)
                .add();

        net.messageBuilder(InventoryPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(InventoryPacket::new)
                .encoder(InventoryPacket::toBytes)
                .consumerMainThread(InventoryPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
