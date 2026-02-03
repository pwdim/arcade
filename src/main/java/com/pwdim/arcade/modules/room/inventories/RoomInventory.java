package com.pwdim.arcade.modules.room.inventories;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RoomInventory {

    private final Arcade plugin;

    public RoomInventory(Arcade plugin){
        this.plugin = plugin;
    }

    public Inventory getInventory(int page){
        Inventory inventory = Bukkit.createInventory(null, 54, ColorUtil.color("Menu de salas  (" + (page + 1) +")"));

        List<Arena> arenaList = new ArrayList<>(plugin.getArenaManager().getActiveArenas().values());

        if (arenaList.isEmpty()){
            inventory.setItem(22, plugin.getRoomManager().getRoomItem().nullItem());
            return inventory;
        }

        int ITEMS_PER_PAGE = 28;
        int startIndex = page * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, arenaList.size());

        int slot = 10;
        for (int i = startIndex; i < endIndex; i++){
            if ((slot + 1) % 9 == 0) slot += 2;
            Arena checkArena = arenaList.get(i);
            ItemStack item = plugin.getRoomManager().getRoomItem().roomItem(checkArena);
            item = NMSUtils.setCustomNBT(item, "arenaID", checkArena.getId());
            inventory.setItem(slot, item);

            slot++;
        }

        if (page > 0){
            inventory.setItem(45, plugin.getRoomManager().getRoomItem().backPageItem());
        }
        if (endIndex < arenaList.size()){
            inventory.setItem(53, plugin.getRoomManager().getRoomItem().nextPageItem());
        }

        return inventory;
    }

}