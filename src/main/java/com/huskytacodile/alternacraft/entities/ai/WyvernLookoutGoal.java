package com.huskytacodile.alternacraft.entities.ai;

import com.huskytacodile.alternacraft.entities.wyverns.WyvernEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class WyvernLookoutGoal extends Goal {
    private final WyvernEntity wyvern;
    private int risk;
    private int riskTime;
    private AABB area = null;
    private BlockPos origin;
    private int timer = 100;

    public WyvernLookoutGoal(WyvernEntity wyvern, int riskTime) {
        this.wyvern = wyvern;
        this.riskTime = riskTime;
    }

    @Override
    public boolean canUse() {
        this.risk = wyvern.getRisk();
        return risk > riskTime && !wyvern.isSleeping() && wyvern.getLevel().getBlockState(wyvern.blockPosition().below(10)) != Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canContinueToUse() {
        return wyvern.getRisk() > 0 && !wyvern.isSleeping();
    }

    @Override
    public void start() {
        var pos = wyvern.position();
        origin = wyvern.blockPosition();
        wyvern.getNavigation().moveTo(wyvern.getRandom().nextInt(((int) wyvern.getX())-8, ((int) wyvern.getX()) + 8), pos.y() + 30, wyvern.getRandom().nextInt(((int) wyvern.getZ())-8, ((int) wyvern.getZ()) + 8), 2);
    }

    public Path createPath(BlockPos pos, int parts){
        var ogPos = wyvern.position();
        var xDiff = pos.getX() - ogPos.x();
        var yDiff = pos.getY() - ogPos.y();
        var zDiff = pos.getZ() - ogPos.z();
        var xPart = 1/parts * xDiff;
        var yPart = 1/parts * yDiff;
        var zPart = 1/parts * zDiff;
        List<Vec3> list = IntStream.range(1, 11).mapToObj((i) -> new Vec3(ogPos.x() + i * xPart, ogPos.y() + i*yPart, ogPos.z() + i*zPart)).toList();
        return new Path(list.stream().map(i -> new Node(((int) i.x()), ((int) i.y()), ((int) i.z()))).toList(), pos, false);
    }

    @Override
    public void tick() {
        if (wyvern.level.isClientSide()){
            return;
        }
        wyvern.increaseRisk(-1);
        if (!wyvern.getNavigation().isDone()){
            return;
        }
        if (area == null) {
            var pos = wyvern.position();
            area = AABB.ofSize(pos, 20, 5, 20);
        }
        wyvern.setNoGravity(true);
        if (timer <= 0) {
            var block = getRandomBlock();
            wyvern.getNavigation().moveTo(block.getX(), block.getY(), block.getZ(), 3);
            timer = 100;
        }
        timer--;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void stop() {
        if (!wyvern.getLevel().isClientSide()) {
            wyvern.getNavigation().moveTo(origin.getX(), origin.getY(), origin.getZ(), 4);
            wyvern.setRisk(-500);
            wyvern.setNoGravity(false);
            super.stop();
        }
    }

    public BlockPos getRandomBlock(){
        Random random = new Random();
        return new BlockPos(
                random.nextInt(((int) area.minX), ((int) area.maxX)+1),
                random.nextInt(((int) area.minY), ((int) area.maxY)+1),
                random.nextInt(((int) area.minZ), ((int) area.maxZ)+1));
    }
}
