package com.huskytacodile.alternacraft.mixin;

import com.huskytacodile.alternacraft.menu.PlayerInventoryMenu;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
@Mixin(Player.class)
public class InventoryMenuMixin {
    @Redirect(method = "Lnet/minecraft/world/entity/player/Player;<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;FLcom/mojang/authlib/GameProfile;Lnet/minecraft/world/entity/player/ProfilePublicKey;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;inventoryMenu:Lnet/minecraft/world/inventory/InventoryMenu;", opcode = Opcodes.PUTFIELD))
    private void inventoryMenu(Player player, InventoryMenu menu){
        player.inventoryMenu = new PlayerInventoryMenu(player.getInventory(), !player.getLevel().isClientSide, player);
    }
}
