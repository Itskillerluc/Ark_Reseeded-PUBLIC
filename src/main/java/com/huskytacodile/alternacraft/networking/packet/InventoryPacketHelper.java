package com.huskytacodile.alternacraft.networking.packet;

import com.huskytacodile.alternacraft.menu.PlayerInventoryMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.fml.DistExecutor;

public class InventoryPacketHelper {
    public static DistExecutor.SafeCallable<Boolean> openInventory(){
        return new DistExecutor.SafeCallable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                LocalPlayer player = Minecraft.getInstance().player;
                player.inventoryMenu = new PlayerInventoryMenu(player.getInventory(), !player.getLevel().isClientSide(), player, player.inventoryMenu);
                return true;
            }
        };
    }
}
