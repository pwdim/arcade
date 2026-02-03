package com.pwdim.arcade.modules.lobby;


import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.utils.ColorUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LobbyItem implements Listener {

    public static Arcade plugin;

    public LobbyItem(Arcade plugin){
        LobbyItem.plugin = plugin;
    }

    public static ItemStack lobbyItem(){
        ItemStack itemStack = new ItemStack(Material.BED, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ColorUtil.color("&cVoltar ao lobby"));
        List<String> lore = new ArrayList<>();
        lore.add(ColorUtil.color("&eClique para voltar ao Lobby"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static void giveItem(Player p){
        p.getInventory().setItem(8, lobbyItem());
    }

    public static void removeItem(Player p){
        if (p.getInventory().contains(lobbyItem())){
            p.getInventory().removeItem(lobbyItem());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack item = e.getPlayer().getItemInHand();

        if (item.getType() == Material.BED){
            if (item.getItemMeta().equals(lobbyItem().getItemMeta())) {
                plugin.getPlayerManager().sendToLobby(p);
            }

        }
    }
}
