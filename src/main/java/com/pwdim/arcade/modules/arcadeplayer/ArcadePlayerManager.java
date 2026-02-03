package com.pwdim.arcade.modules.arcadeplayer;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.player.PlayerState;
import com.pwdim.arcade.modules.arcadeplayer.model.ArcadePlayer;
import com.pwdim.arcade.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArcadePlayerManager {

    private final Arcade plugin;

    public ArcadePlayerManager(Arcade plugin) {
        this.plugin = plugin;

    }

    public void setPlayerState(ArcadePlayer arcadePlayer, PlayerState playerState){
        arcadePlayer.setState(playerState);
        Player p = Bukkit.getPlayer(arcadePlayer.getUuid());

        switch (playerState){
            case WAITING:
                plugin.logger("aguardando em" + plugin.getArenaManager().getPlayerArena(p).getId());
                break;
            case DETECTIVE:
                p.sendMessage(ColorUtil.color("&b&lDETETIVE"));
                break;
            case INNOCENT:
                p.sendMessage(ColorUtil.color("&a&lINOCENTE"));
                break;
            case MURDERER:
                p.sendMessage(ColorUtil.color("&c&lASSASSINO"));
                break;
            case WINNER:
                p.sendMessage(ColorUtil.color("&6&lVENCEDOR"));
                break;
            case SPECTATOR:
                p.sendMessage(ColorUtil.color("&7&lESPECTADOR"));
                break;

            default:
                setPlayerState(arcadePlayer, PlayerState.WAITING);
                break;
        }
    }
}
