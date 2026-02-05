package com.pwdim.arcade.modules.arcadeplayer;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.modules.coreitems.item.LobbyItem;
import com.pwdim.arcade.modules.player.PlayerState;
import com.pwdim.arcade.modules.arcadeplayer.model.ArcadePlayer;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class ArcadePlayerManager {

    private final Arcade plugin;

    public ArcadePlayerManager(Arcade plugin) {
        this.plugin = plugin;

    }

    public void setPlayerState(ArcadePlayer arcadePlayer, PlayerState playerState){
        arcadePlayer.setState(playerState);
        Player p = Bukkit.getPlayer(arcadePlayer.getUuid());
        Arena arena = plugin.getArenaManager().getPlayerArena(p);

        switch (playerState){
            case LOBBY:
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
                LobbyItem.removeItem(p);

                for (UUID uuid :    arena.getPlayers()){

                    if (!arena.getPlayers().isEmpty()){
                        Player target = Bukkit.getPlayer(uuid);
                        ArcadePlayer arcadeTarget = new ArcadePlayer(target.getUniqueId(), arena);

                        if (arcadeTarget.getState() == PlayerState.SPECTATOR){
                            p.showPlayer(p);
                            target.showPlayer(p);
                        } else {
                            target.hidePlayer(p);
                        }
                    }

                }

                break;
            case WAITING:
                break;
            case DETECTIVE:
                ConfigUtils.sendTitle(p, "&b&lDETETIVE", null, 20, 20, 20);
                break;
            case INNOCENT:
                ConfigUtils.sendTitle(p, "&a&lINOCENTE", null, 20, 20, 20);
                break;
            case MURDERER:
                ConfigUtils.sendTitle(p, "&c&lASSASSINO", null, 20, 20, 20);
                break;
            case WINNER:
                ConfigUtils.sendTitle(p, "&b&lVITORIA!", null, 20, 20, 20);
                LobbyItem.giveItem(p);
                break;
            case SPECTATOR:
                ConfigUtils.sendTitle(p, "&c&lVOCÊ MORREU!", null, 20, 20, 20);
                p.setGameMode(GameMode.SURVIVAL);
                p.setAllowFlight(true);
                p.setFlying(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1, false, false));
                LobbyItem.giveItem(p);
                for (UUID uuid :    arena.getPlayers()){

                    if (!arena.getPlayers().isEmpty()){
                        Player target = Bukkit.getPlayer(uuid);
                        ArcadePlayer arcadeTarget = new ArcadePlayer(target.getUniqueId(), arena);

                        if (arcadeTarget.getState() == PlayerState.SPECTATOR){
                            p.showPlayer(p);
                            target.showPlayer(p);
                        } else {
                            target.hidePlayer(p);
                        }
                    }

                }
                break;
        }
    }
}
