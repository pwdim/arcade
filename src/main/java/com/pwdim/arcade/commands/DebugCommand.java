package com.pwdim.arcade.commands;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arcadeplayer.model.ArcadePlayer;
import com.pwdim.arcade.modules.player.PlayerState;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {

    private final Arcade plugin;

    public DebugCommand(Arcade plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ConfigUtils.consoleError());
            return true;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("arcade.manage"))){
            p.sendMessage(ConfigUtils.noPermMSG());
            return true;
        }
        ArcadePlayer arcadePlayer = new ArcadePlayer(p.getUniqueId(), plugin.getArenaManager().getPlayerArena(p));

        if (args.length == 0){
            p.sendMessage(arcadePlayer.getState().toString());
            return true;
        }

        String stateType = args[0];

        switch (stateType){
            case "1":
                plugin.getArcadePlayerManager().setPlayerState(arcadePlayer, PlayerState.WAITING);
                p.sendMessage(ColorUtil.color("&b&lDEBUG: &9" + arcadePlayer.getState().toString()));
            case "2":
                plugin.getArcadePlayerManager().setPlayerState(arcadePlayer, PlayerState.DETECTIVE);
                p.sendMessage(ColorUtil.color("&b&lDEBUG: &9" + arcadePlayer.getState().toString()));
                break;
            case "3":
                plugin.getArcadePlayerManager().setPlayerState(arcadePlayer, PlayerState.INNOCENT);
                p.sendMessage(ColorUtil.color("&b&lDEBUG: &9" + arcadePlayer.getState().toString()));
                break;
            case "4":
                plugin.getArcadePlayerManager().setPlayerState(arcadePlayer, PlayerState.MURDERER);
                p.sendMessage(ColorUtil.color("&b&lDEBUG: &9" + arcadePlayer.getState().toString()));
                break;
            case "5":
                plugin.getArcadePlayerManager().setPlayerState(arcadePlayer, PlayerState.WINNER);
                p.sendMessage(ColorUtil.color("&b&lDEBUG: &9" + arcadePlayer.getState().toString()));
                break;
            case "6":
                plugin.getArcadePlayerManager().setPlayerState(arcadePlayer, PlayerState.SPECTATOR);
                p.sendMessage(ColorUtil.color("&b&lDEBUG: &9" + arcadePlayer.getState().toString()));
                break;

        }


        return true;
    }
}
