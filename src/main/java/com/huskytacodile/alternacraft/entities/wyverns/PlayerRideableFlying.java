package com.huskytacodile.alternacraft.entities.wyverns;

import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public interface PlayerRideableFlying extends PlayerRideableJumping {

    @Nullable
    static PlayerRideableFlying getEntity(Player player){
        if(player != null && player.getVehicle() instanceof PlayerRideableFlying playerRideableFlying) {
            return playerRideableFlying;
        } else return null;
    }

    boolean canLower();

    void onPlayerLower();

    void onJumpHold();

    Player getPassenger();
}
