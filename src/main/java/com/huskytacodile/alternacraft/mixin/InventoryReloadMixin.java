package com.huskytacodile.alternacraft.mixin;

import com.huskytacodile.alternacraft.menu.PlayerInventoryMenu;
import com.huskytacodile.alternacraft.networking.ModMessages;
import com.huskytacodile.alternacraft.networking.packet.InventoryPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(ServerPlayerGameMode.class)
public class InventoryReloadMixin {

    @Inject(method = "Lnet/minecraft/server/level/ServerPlayerGameMode;changeGameModeForPlayer(Lnet/minecraft/world/level/GameType;)Z", at = @At("RETURN"))
    private void injectMethod(GameType gameType, CallbackInfoReturnable<Boolean> info){
        ServerPlayer player = ((ServerPlayerGameMode)(Object)this).player;
        if (player.inventoryMenu instanceof PlayerInventoryMenu){
            player.inventoryMenu = new PlayerInventoryMenu(player.getInventory(), !player.getLevel().isClientSide(), player, player.inventoryMenu);
            player.containerMenu = player.inventoryMenu;
            ModMessages.sendToPlayer(new InventoryPacket(), player);
        }
    }
}
