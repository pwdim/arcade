package com.pwdim.arcade.modules.arena.listeners;

import com.pwdim.arcade.modules.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildListeners implements Listener {

    private final GameManager gameManager;

    public BuildListeners(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent  e){
        if (!gameManager.getBlockManager().canBreak(e.getBlock())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if (!gameManager.getBlockManager().canPLace(e.getBlock())){
            e.setCancelled(true);
        }
    }



}
