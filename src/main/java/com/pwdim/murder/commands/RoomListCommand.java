package com.pwdim.murder.commands;

import com.pwdim.murder.manager.room.RoomInventory;
import com.pwdim.murder.manager.room.RoomItem;
import com.pwdim.murder.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoomListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command CMD, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage(ConfigUtils.consoleError());
            return true;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("arcade.manage"))){
            p.sendMessage(ConfigUtils.noPermMSG());
            return true;
        }

        p.openInventory(RoomInventory.roomMenuInventory(1));
        p.updateInventory();
        return false;
    }
}
