package com.huskytacodile.alternacraft.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;

public class PlayerInventoryMenu extends InventoryMenu {
    public PlayerInventoryMenu(Inventory pPlayerInventory, boolean pActive, Player pOwner) {
        super(pPlayerInventory, pActive, pOwner);
        System.out.println("test");
        this.slots.clear();
    }
}
