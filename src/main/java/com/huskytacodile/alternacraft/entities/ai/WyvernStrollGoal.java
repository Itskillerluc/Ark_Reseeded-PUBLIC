package com.huskytacodile.alternacraft.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class WyvernStrollGoal extends RandomStrollGoal {
    public WyvernStrollGoal(PathfinderMob pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
    }

    @Override
    public boolean canUse() {
        if (this.mob.isVehicle() || this.mob.getTarget() != null) {
            return false;
        } else {
            if (!this.forceTrigger) {
                if (this.mob.getNoActionTime() >= 100) {
                    return false;
                }

                if (this.mob.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
                    return false;
                }
            }
            var block = getRandomBlock(AABB.ofSize(mob.position(), 10, 1, 10));

            Vec3 vec3 = new Vec3(block.getX(), block.getY(), block.getZ());
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    public BlockPos getRandomBlock(AABB area){
        Random random = new Random();
        return new BlockPos(
                random.nextInt(((int) area.minX), ((int) area.maxX)+1),
                random.nextInt(((int) area.minY), ((int) area.maxY)+1),
                random.nextInt(((int) area.minZ), ((int) area.maxZ)+1));
    }
}
