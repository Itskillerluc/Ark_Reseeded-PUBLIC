package com.huskytacodile.alternacraft.block.entity;

import com.huskytacodile.alternacraft.block.custom.AlternaDinoEggBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;
import java.util.function.Function;

public class AlternaDinoEggBlockEntity <T extends LivingEntity> extends BlockEntity {
    protected int maxHatchTime;
    int hatchTimer;
    private Function<Level, T> entity;

    public AlternaDinoEggBlockEntity(BlockPos pPos, BlockState pBlockState, int maxHatchTime, Function<Level, T> entity) {
        super(ModBlockEntities.FIRE_WYVERN_EGG_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.maxHatchTime = maxHatchTime;
        this.entity = entity;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("hatchTime" ,hatchTimer);
        pTag.putInt("maxHatchTime", maxHatchTime);
    }

    public int getHatchTimer() {
        return hatchTimer;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        hatchTimer = pTag.getInt("hatchTime");
        maxHatchTime = pTag.getInt("maxHatchTime");
    }

    public int getMaxHatchTime() {
        return maxHatchTime;
    }

    public void tick(UUID owner){
        if (level.isClientSide() || !this.getBlockState().getValue(AlternaDinoEggBlock.PLACEDBYPLAYER)){
            return;
        }
        hatchTimer++;
        if (hatchTimer > maxHatchTime){
            var entityInstance = entity.apply(this.getLevel());
            entityInstance.setPos(new Vec3(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()));
            entityInstance.getPersistentData().putUUID("owner", owner);
            level.addFreshEntity(entityInstance);
            this.getLevel().destroyBlock(this.getBlockPos(), false);
        }
    }
}
