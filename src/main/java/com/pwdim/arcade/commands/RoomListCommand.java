package com.pwdim.arcade.commands;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.room.inventories.RoomInventory;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class RoomListCommand implements CommandExecutor {

    private final Arcade plugin;

    public RoomListCommand(Arcade plugin){
        this.plugin = plugin;
    }
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

        RoomInventory gui = new RoomInventory(plugin);
        p.openInventory(gui.getInventory(0));

        p.updateInventory();
        return false;
    }
}
