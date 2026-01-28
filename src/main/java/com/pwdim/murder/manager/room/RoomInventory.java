package com.pwdim.murder.manager.room;

import com.pwdim.murder.Murder;
import com.pwdim.murder.manager.arena.Arena;
import com.pwdim.murder.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomInventory {

    private static Murder plugin;
    private static final int itemPerPage = 28;

    public RoomInventory(Murder plugin){
        this.plugin = plugin;
    }

    public static Inventory roomMenuInventory(int page){
        Inventory inventory = Bukkit.createInventory(null, 54, ColorUtil.color("&bMenu de salas &e" + page + 1));
        List<Arena> arenaList = new ArrayList<>();
        plugin.getArenaManager().getActiveArenas().forEach((s, arena) -> arenaList.add(arena));

        if (arenaList.isEmpty()){
            inventory.setItem(22, RoomItem.nullItem());
            return inventory;
        }

        int startIndex = page * itemPerPage;
        int endIndex = Math.min(startIndex + itemPerPage, arenaList.size());

        int slot = 10;
        for (int i = startIndex; i <endIndex; i++){
            if ((slot + 1) % 9 == 0) slot += 2;

            inventory.setItem(slot, RoomItem.roomItem(arenaList.get(i)));
            slot++;
        }

        if (page > 0){
            inventory.setItem(45, RoomItem.backPageItem());
        }
        if (endIndex < arenaList.size()){
            inventory.setItem(53, RoomItem.nextPageItem());
        }
        return inventory;
    }

//    public static Inventory roomMenuInventory(){
//        Inventory inventory = Bukkit.createInventory(null, 54, ColorUtil.color("&eMenu de Salas"));
//        AtomicInteger arenaItemCount = new AtomicInteger(9);
//
//        if (plugin.getArenaManager().getActiveArenas().isEmpty()) {
//            inventory.setItem(21, RoomItem.nullItem());
//        } else {
//            plugin.getArenaManager().getActiveArenas()
//                    .forEach((s, arena) -> {
//                        arenaItemCount.getAndIncrement();
//                        inventory.setItem(arenaItemCount.get(), RoomItem.roomItem(arena));
//                    });
//        }
//
//        return inventory;
//    }
}
