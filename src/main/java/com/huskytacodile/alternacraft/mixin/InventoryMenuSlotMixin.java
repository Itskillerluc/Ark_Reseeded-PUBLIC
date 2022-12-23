package com.huskytacodile.alternacraft.mixin;

import com.huskytacodile.alternacraft.misc.MixinDebugHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Debug(export = true)
@Mixin(InventoryMenu.class)
public abstract class InventoryMenuSlotMixin{
    @Redirect(method = "Lnet/minecraft/world/inventory/InventoryMenu;<init>(Lnet/minecraft/world/entity/player/Inventory;ZLnet/minecraft/world/entity/player/Player;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/inventory/InventoryMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;", ordinal = 4))
    private Slot addSlot(InventoryMenu instance, Slot pSlot, Inventory pPlayerInventory, boolean pActive, Player pOwner) {
        return MixinDebugHelper.InventoryMenuSlotMixin(instance, pSlot, pPlayerInventory, pActive, pOwner);
    }
}