package com.huskytacodile.alternacraft.block.entity;

import com.huskytacodile.alternacraft.item.ModItems;
import com.huskytacodile.alternacraft.item.custom.DNASyringeItem;
import com.huskytacodile.alternacraft.recipe.FossilGrinderRecipe;
import com.huskytacodile.alternacraft.screen.DNAInsertionTableMenu;
import com.huskytacodile.alternacraft.screen.FossilGrinderMenu;
import com.huskytacodile.alternacraft.util.DNATier;
import com.huskytacodile.alternacraft.util.Dino;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class DNAInsertionTableBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 90;

    public DNAInsertionTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.DNA_INSERTION_TABLE.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return DNAInsertionTableBlockEntity.this.progress;
                    case 1: return DNAInsertionTableBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: DNAInsertionTableBlockEntity.this.progress = value; break;
                    case 1: DNAInsertionTableBlockEntity.this.maxProgress = value; break;
                }
            }

            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("DNA Insertion Table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new DNAInsertionTableMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("dna_insertion.progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("dna_insertion.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, DNAInsertionTableBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(DNAInsertionTableBlockEntity entity) {
        boolean hasEgg = entity.itemHandler.getStackInSlot(0).getItem() == Items.EGG ||
                entity.itemHandler.getStackInSlot(0).getItem() == Items.TURTLE_EGG;

        boolean hasSyringe = entity.itemHandler.getStackInSlot(1).getItem() instanceof DNASyringeItem;
        boolean isNotEmpty = entity.itemHandler.getStackInSlot(1).getItem() != ModItems.EMPTY_SYRINGE.get();

        return hasEgg & hasSyringe && isNotEmpty;
    }

    private static void craftItem(DNAInsertionTableBlockEntity entity) {
        entity.itemHandler.extractItem(0,1, false);
        entity.itemHandler.extractItem(1,1, false);

        entity.itemHandler.setStackInSlot(0, new ItemStack(ModItems.ACRO_SPAWN_EGG.get()));

        entity.resetProgress();
    }

    private static int getNextFreeOutputSlot(SimpleContainer inventory) {
        int toReturn = -1;
        for(int i = 5; i >= 2; i--) {
            toReturn = inventory.getItem(i).isEmpty() || (inventory.getItem(i).getMaxStackSize() < inventory.getItem(i).getCount() + 1)
                    ? i : toReturn;
        }

        return toReturn;
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
