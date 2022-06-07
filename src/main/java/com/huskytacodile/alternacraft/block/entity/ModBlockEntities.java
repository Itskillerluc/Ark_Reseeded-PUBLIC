package com.huskytacodile.alternacraft.block.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Alternacraft.MOD_ID);

    public static final RegistryObject<BlockEntityType<FossilGrinderBlockEntity>> FOSSIL_GRINDER =
            BLOCK_ENTITIES.register("fossil_grinder", () ->
                    BlockEntityType.Builder.of(FossilGrinderBlockEntity::new,
                            ModBlocks.FOSSIL_GRINDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<DNAInsertionTableBlockEntity>> DNA_INSERTION_TABLE =
            BLOCK_ENTITIES.register("dna_inseration_table", () ->
                    BlockEntityType.Builder.of(DNAInsertionTableBlockEntity::new,
                            ModBlocks.DNA_INSERTION_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
