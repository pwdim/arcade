package com.pwdim.arcade.modules.blocks;


import com.pwdim.arcade.modules.game.GameManager;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class BlockManager {

    private final GameManager gameManager;

    public BlockManager(GameManager gameManager) {
        this.gameManager =  gameManager;


        allowedToBreak.add(Material.BEDROCK);
        allowedToPlace.add(Material.BEDROCK);
    }

    private final Set<Material> allowedToBreak = new HashSet<>();
    private final Set<Material> allowedToPlace = new HashSet<>();

    public boolean canBreak(Block block) { return allowedToBreak.contains(block.getType());}

    public boolean canPLace(Block block) { return allowedToPlace.contains(block.getType());}

}
