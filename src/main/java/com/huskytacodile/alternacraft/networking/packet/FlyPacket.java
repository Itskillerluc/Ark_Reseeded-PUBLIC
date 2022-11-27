package com.huskytacodile.alternacraft.networking.packet;

import com.huskytacodile.alternacraft.entities.wyverns.PlayerRideableFlying;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FlyPacket {
    private static final String MESSAGE_FLY = "message.tutorialmod.fly";
    public FlyPacket() {
    }

    public FlyPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ((PlayerRideableFlying) context.getSender().getVehicle()).onJumpHold();
        });
        return true;
    }
}
