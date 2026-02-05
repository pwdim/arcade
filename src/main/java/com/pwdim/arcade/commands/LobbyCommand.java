package com.pwdim.arcade.commands;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.coreitems.item.LobbyItem;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {
    private final Arcade plugin;
    public LobbyCommand(Arcade plugin){
        this.plugin = plugin;
    }


        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)){
                sender.sendMessage(ConfigUtils.consoleError());
                return true;
            }
            Player p = (Player) sender;

            if (!ConfigUtils.inGame(p)){
                p.sendMessage(ConfigUtils.alreadyLobby());
                return true;
            }

            plugin.getPlayerManager().sendToLobby(p);
            if (p.getInventory().contains(LobbyItem.lobbyItem())) LobbyItem.removeItem(p);



        return true;
    }
}
