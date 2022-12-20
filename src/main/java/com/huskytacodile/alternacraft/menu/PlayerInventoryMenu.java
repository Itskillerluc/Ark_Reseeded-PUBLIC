package com.huskytacodile.alternacraft.menu;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

public class PlayerInventoryMenu extends InventoryMenu {
    public PlayerInventoryMenu(Inventory pPlayerInventory, boolean pActive, Player pOwner, @Nullable InventoryMenu menu) {
        super(pPlayerInventory, pActive, pOwner);
        try {
            if (!pOwner.isCreative()) {
                clearSlots();
                setupSlots(pPlayerInventory);
            }
            if (menu != null){
                for (int i = 0; i < menu.lastSlots.size(); i++) {
                    this.lastSlots.set(i, menu.lastSlots.get(i));
                }
                for (int i = 0; i < menu.remoteSlots.size(); i++){
                    this.remoteSlots.set(i, menu.remoteSlots.get(i));
                }
            }
        }catch (NullPointerException | IndexOutOfBoundsException ignore){}
    }

    public void setupSlots(Inventory inv){
        for (int i = 0; i < 6; i++) {
            this.addSlot(new Slot(inv, i, 7 + i*21, 61));
        }
    }

    private void clearSlots(){
        this.slots.clear();
        this.remoteSlots.clear();
        this.lastSlots.clear();
    }
}
