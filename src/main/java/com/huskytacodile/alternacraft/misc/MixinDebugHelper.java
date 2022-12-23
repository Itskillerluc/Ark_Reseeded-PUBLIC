package com.huskytacodile.alternacraft.misc;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;

public class MixinDebugHelper {
    public static Slot InventoryMenuSlotMixin(InventoryMenu instance, Slot pSlot, Inventory pPlayerInventory, boolean pActive, Player pOwner){
        int i1 = pSlot.getSlotIndex();
        return instance.addSlot(new Slot(pPlayerInventory, i1, 78 + i1 * 20, 184));
    }
}
