package com.pwdim.arcade.modules.room;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.room.inventories.RoomInventory;
import com.pwdim.arcade.modules.room.inventories.RoomManageInventory;
import com.pwdim.arcade.modules.room.inventories.RoomItem;

public class RoomManager {

    private final Arcade plugin;
    private final RoomInventory roomInventory;
    private final RoomManageInventory roomManageInventory;
    private final RoomItem roomItem;

    public RoomManager(Arcade plugin) {
        this.plugin = plugin;

        this.roomInventory = new RoomInventory(plugin);
        this.roomManageInventory = new RoomManageInventory(plugin);
        this.roomItem = new RoomItem();
    }


    public RoomInventory getRoomInventory() { return roomInventory; }
    public RoomManageInventory getRoomManageInventory() { return roomManageInventory; }
    public RoomItem getRoomItem() {
        return roomItem;
    }


}