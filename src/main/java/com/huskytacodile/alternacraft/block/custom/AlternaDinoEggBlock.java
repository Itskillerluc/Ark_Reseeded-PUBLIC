package com.huskytacodile.alternacraft.block.custom;

import com.huskytacodile.alternacraft.block.ModBlocks;
import com.huskytacodile.alternacraft.block.entity.AlternaDinoEggBlockEntity;
import com.huskytacodile.alternacraft.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class AlternaDinoEggBlock <T extends LivingEntity> extends BaseEntityBlock {
    public static final BooleanProperty PLACEDBYPLAYER = BooleanProperty.create("placedbyplayer");
    private final int hatchTime;
    private final Function<Level, T> entity;

    public AlternaDinoEggBlock(Properties properties, int hatchTime, Function<Level, T> entity) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PLACEDBYPLAYER, false));
        this.hatchTime = hatchTime;
        this.entity = entity;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
       return this.stateDefinition.any().setValue(PLACEDBYPLAYER, true);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new AlternaDinoEggBlockEntity<>(pPos, pState, hatchTime, entity);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PLACEDBYPLAYER);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()){
            return InteractionResult.PASS;
        }
        var blockEntity = pLevel.getBlockEntity(pPos, ModBlockEntities.FIRE_WYVERN_EGG_BLOCK_ENTITY.get());

        if (blockEntity.isPresent()) {
            if (pPlayer.isShiftKeyDown()) {
                pPlayer.sendSystemMessage(Component.literal(blockEntity.get().getHatchTimer() + "/" + blockEntity.get().getMaxHatchTime()));
                return InteractionResult.SUCCESS;
            } else if (pPlayer.getItemInHand(pHand).isEmpty()) {
                var item = new ItemStack(this.asItem());
                blockEntity.get().saveToItem(item);
                pPlayer.setItemInHand(pHand, item);
                pLevel.destroyBlock(pPos, false);
            }
        }
        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : ((pLevel1, pPos, pState1, pBlockEntity) -> ((AlternaDinoEggBlockEntity<?>) pBlockEntity).tick());
    }
}
