package com.huskytacodile.alternacraft.screen.slot;

import com.huskytacodile.alternacraft.item.custom.DNASyringeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModSyringeInsertionSlot extends SlotItemHandler {
    public ModSyringeInsertionSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof DNASyringeItem;
    }
}
