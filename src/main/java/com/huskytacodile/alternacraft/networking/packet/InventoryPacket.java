package com.huskytacodile.alternacraft.networking.packet;

import com.huskytacodile.alternacraft.entities.wyverns.PlayerRideableFlying;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class InventoryPacket {
    private static final String MESSAGGE_LOWER = "message.tutorialmod.inventory";

    public InventoryPacket() {
    }

    public InventoryPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.safeCallWhenOn(Dist.CLIENT, InventoryPacketHelper::openInventory);
        });
        return true;
    }
}
