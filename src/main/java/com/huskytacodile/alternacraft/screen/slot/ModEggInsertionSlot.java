package com.huskytacodile.alternacraft.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModEggInsertionSlot extends SlotItemHandler {
    public ModEggInsertionSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() == Items.TURTLE_EGG || stack.getItem() == Items.EGG;
    }
}
