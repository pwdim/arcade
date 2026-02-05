package com.pwdim.arcade.modules.coreitems.item;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.utils.ColorUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayAgainItem {

    public static Arcade plugin;

    public PlayAgainItem(Arcade plugin){
        LobbyItem.plugin = plugin;
    }

    public static ItemStack playAgainItem(){
        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ColorUtil.color("&aJogar Novamente"));
        List<String> lore = new ArrayList<>();
        lore.add(ColorUtil.color("&bClique para jogar novamente"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static void giveItem(Player p){
        p.getInventory().setItem(8, playAgainItem());
    }

    public static void removeItem(Player p){
        if (p.getInventory().contains(playAgainItem())){
            p.getInventory().removeItem(playAgainItem());
        }
    }

}
