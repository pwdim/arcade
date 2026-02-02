package com.pwdim.arcade.manager.player;

import com.pwdim.arcade.Arcade;
import com.pwdim.arcade.itens.LobbyItem;
import com.pwdim.arcade.manager.arena.Arena;
import com.pwdim.arcade.manager.blocks.BlockManager;
import com.pwdim.arcade.manager.game.GameState;
import com.pwdim.arcade.tasks.GameStartCountdownTask;
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
                p.sendMessage(ColorUtil.color("&eEsperando"));
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

            default:
                setPlayerState(arcadePlayer, PlayerState.WAITING);
                break;
        }
    }
}
