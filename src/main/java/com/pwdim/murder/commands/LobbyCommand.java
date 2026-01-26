package com.pwdim.murder.commands;

import com.pwdim.murder.Murder;
import com.pwdim.murder.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {
    private final Murder plugin;
    public LobbyCommand(Murder plugin){
        this.plugin = plugin;
    }


        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)){
                sender.sendMessage(ConfigUtils.consoleError());
            }
            Player p = (Player) sender;

            p.teleport(ConfigUtils.getLobby());


        return true;
    }
}
