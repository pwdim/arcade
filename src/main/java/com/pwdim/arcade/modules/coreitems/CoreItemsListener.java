package com.pwdim.arcade.modules.coreitems;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.coreitems.item.LobbyItem;
import com.pwdim.arcade.modules.coreitems.item.PlayAgainItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CoreItemsListener implements Listener {

    private final Arcade plugin;

    public CoreItemsListener(Arcade plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack item = e.getPlayer().getItemInHand();

        if (item.getType() == Material.BED){
            if (item.getItemMeta().equals(LobbyItem.lobbyItem().getItemMeta())) {
                plugin.getPlayerManager().sendToLobby(p);
            }
        }

        if (item.getType() == PlayAgainItem.playAgainItem().getType()){
            if (item.getItemMeta().equals(PlayAgainItem.playAgainItem().getItemMeta())){
                plugin.getPlayerManager().sendToArena(p);
            }
        }
    }
}
