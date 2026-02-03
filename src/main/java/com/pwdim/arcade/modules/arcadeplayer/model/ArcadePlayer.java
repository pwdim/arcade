package com.pwdim.arcade.modules.arcadeplayer.model;

import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.modules.player.PlayerState;

import java.util.UUID;

public class ArcadePlayer {

    private UUID uuid;
    private Arena arena;
    private PlayerState state;

    public ArcadePlayer(UUID uuid, Arena arena){
        this.uuid = uuid;
        this.arena = arena;
        this.state = PlayerState.WAITING;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }
}
