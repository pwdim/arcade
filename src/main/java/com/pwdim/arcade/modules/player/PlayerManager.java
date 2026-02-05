package com.pwdim.arcade.modules.player;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arcadeplayer.model.ArcadePlayer;
import com.pwdim.arcade.modules.coreitems.item.LobbyItem;
import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.modules.arena.ArenaManager;
import com.pwdim.arcade.modules.game.GameManager;
import com.pwdim.arcade.modules.game.GameState;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


import java.util.Comparator;


public class PlayerManager {

    private final GameManager gameManager;

    private final Arcade plugin;

    public PlayerManager(GameManager gameManager, Arcade plugin){
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    public void sendToArena(Player player) {
        ArenaManager manager = plugin.getArenaManager();

        Arena bestArena = manager.getActiveArenas().values().stream()
                .filter(arena -> arena.getState() == GameState.WAITING)
                .filter(arena -> arena.getPlayers().size() < ConfigUtils.getMaxPLayers())
                .max(Comparator.comparingInt(arena -> arena.getPlayers().size()))
                .orElse(null);

        player.sendMessage(ColorUtil.color("&aConectando..."));
        if (bestArena != null) {
            ArcadePlayer arcadePlayer = new ArcadePlayer(player.getUniqueId(), bestArena);
            arcadePlayer.setState(PlayerState.WAITING);
            teleportToArena(player, bestArena);
            LobbyItem.giveItem(player);
            checkState(bestArena);
        } else {


            manager.setupNewArena(ConfigUtils.getGameMap(), (newArena) -> {
                teleportToArena(player, newArena);
                LobbyItem.giveItem(player);
                checkState(newArena);
                ArcadePlayer arcadePlayer = new ArcadePlayer(player.getUniqueId(), newArena);
                arcadePlayer.setState(PlayerState.WAITING);
            });
        }
    }

    public void sendToLobby(Player player) {
        Arena arena = plugin.getArenaManager().getPlayerArena(player);
        ArcadePlayer arcadePlayer = new ArcadePlayer(player.getUniqueId(), arena);

        if (arena == null) {
            if (player.isOnline()){
                player.teleport(ConfigUtils.getLobby());
            }
            player.getInventory().clear();
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {

            arena.getPlayers().remove(player.getUniqueId());
            LobbyItem.removeItem(player);
            if (player.isOnline()){
                player.teleport(ConfigUtils.getLobby());
            }


            if (arena.getState() == GameState.WAITING || arena.getState() == GameState.STARTING) {
                if (player.getCustomName() == null){
                    String msg = ColorUtil.color("&7" + player.getName() + " &esaiu da partida&e(&b" +
                            arena.getPlayers().size() + "&e/&b" + ConfigUtils.getMaxPLayers() + "&e)");
                    arena.broadcastArena(msg);
                } else {
                    String msg = ColorUtil.color("&7" + player.getCustomName() + " &esaiu da partida&e(&b" +
                            arena.getPlayers().size() + "&e/&b" + ConfigUtils.getMaxPLayers() + "&e)");
                    arena.broadcastArena(msg);
                }
            }

            arcadePlayer.setState(PlayerState.LOBBY);


            checkState(arena);


            if (arena.getPlayers().isEmpty() && arena.getState() == GameState.PLAYING) {
                plugin.getGameManager().setGameState(arena, GameState.RESTARTING);
            }
        });
    }

    private void teleportToArena(Player player, Arena arena){
        Bukkit.getScheduler().runTask(plugin, () -> {
            player.teleport(arena.getWorld().getSpawnLocation());
            arena.getPlayers().add(player.getUniqueId());
            if (arena.getState() == GameState.WAITING || arena.getState() == GameState.STARTING){
                if (player.getCustomName() == null){
                    arena.broadcastArena("&7" + player.getName() + " &eentrou na partida (&b" + arena.getPlayers().size() + "&e/&b"+ConfigUtils.getMaxPLayers()+"&e)");
                } else {
                    String msg = ColorUtil.color("&7" + player.getCustomName() + " &eentrou na partida&e(&b" +
                            arena.getPlayers().size() + "&e/&b" + ConfigUtils.getMaxPLayers() + "&e)");
                    arena.broadcastArena(msg);
                }
            } else {
                ArcadePlayer arcadePlayer = new ArcadePlayer(player.getUniqueId(), arena);
                plugin.getArcadePlayerManager().setPlayerState(arcadePlayer, PlayerState.SPECTATOR);
            }
            checkState(arena);
        });
    }

    private void checkState(Arena arena) {
        int playersIn = arena.getPlayers().size();
        int minNeeded = ConfigUtils.getMinPlayers();


        if (playersIn >= minNeeded && arena.getState() == GameState.WAITING) {
            if (playersIn < ConfigUtils.getMinPlayers()) {
                arena.broadcastArena("§eAguardando mais jogadores para iniciar...");
                return;
            }
            plugin.getGameManager().setGameState(arena, GameState.STARTING);
        }

        if ((arena.getState() == GameState.PLAYING) && (playersIn <= 1)){
           arena.setState(GameState.ENDING);
        }
    }
}
