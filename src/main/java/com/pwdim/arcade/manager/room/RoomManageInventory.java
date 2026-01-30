package com.pwdim.arcade.manager.room;

import com.pwdim.arcade.manager.arena.Arena;
import com.pwdim.arcade.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RoomManageInventory implements Listener {

    public ItemStack removeRoomItem(){
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ColorUtil.color("&cCancelar partida e excluir mundo"));
        meta.setLore(lore);
        meta.setDisplayName(ColorUtil.color("&4&lREMOVER SALA"));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack confirmRemoveRoomItem(){
        ItemStack item = new ItemStack(Material.WOOL, 1, (short) 14);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ColorUtil.color("&aRemover sala"));
        meta.setLore(lore);
        meta.setDisplayName(ColorUtil.color("&a&lCONFIRMAR"));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack cancelRemoveRoomItem(){
        ItemStack item = new ItemStack(Material.WOOL, 1, (short) 5);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ColorUtil.color("&cNão remover sala"));
        meta.setLore(lore);
        meta.setDisplayName(ColorUtil.color("&c&lCANCELAR"));
        item.setItemMeta(meta);

        return item;
    }

//    public Inventory manageInventory(Arena arena){
//        Inventory inv = Bukkit.createInventory(null, 45, "&bGerenciar Arena " + arena.getId());
//
//
//    }


}
