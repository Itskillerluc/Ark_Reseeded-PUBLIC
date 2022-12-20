package com.huskytacodile.alternacraft.mixin;

import com.huskytacodile.alternacraft.menu.PlayerInventoryMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class InventoryMenuMixin {
    @Inject(method = "Lnet/minecraft/world/entity/player/Player;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    private void inventoryMenu(CompoundTag tag, CallbackInfo info){
        Player player = (Player)(Object)this;
        player.inventoryMenu = new PlayerInventoryMenu(player.getInventory(), !player.getLevel().isClientSide, player, player.inventoryMenu);
    }
}
