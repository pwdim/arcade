package com.pwdim.arcade.modules.player;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.lobby.LobbyItem;
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

        if (bestArena != null) {
            player.sendMessage(ColorUtil.color("&aConectando..."));
            teleportToArena(player, bestArena);
            LobbyItem.giveItem(player);
            checkStart(bestArena);
        } else {
            player.sendMessage(ColorUtil.color("&aConectando..."));


            manager.setupNewArena(ConfigUtils.getGameMap(), (newArena) -> {
                teleportToArena(player, newArena);
                LobbyItem.giveItem(player);
                checkStart(newArena);
            });
        }
    }

    public void sendToLobby(Player player) {
        Arena arena = plugin.getArenaManager().getPlayerArena(player);


        if (arena == null) {
            player.teleport(ConfigUtils.getLobby());
            LobbyItem.removeItem(player);
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            arena.getPlayers().remove(player.getUniqueId());
            player.teleport(ConfigUtils.getLobby());
            LobbyItem.removeItem(player);


            if (arena.getState() == GameState.WAITING || arena.getState() == GameState.STARTING) {
                String msg = ColorUtil.color("&b" + player.getName() + " &esaiu &7(&a" +
                        arena.getPlayers().size() + "/" + ConfigUtils.getMaxPLayers() + "&7)");
                arena.broadcastArena(msg);
            }


            checkStart(arena);


            if (arena.getPlayers().isEmpty() && arena.getState() == GameState.PLAYING) {
                plugin.getGameManager().setGameState(arena, GameState.RESTARTING);
            }
        });
    }

    private void teleportToArena(Player player, Arena arena){
        Bukkit.getScheduler().runTask(plugin, () -> {
            player.teleport(arena.getWorld().getSpawnLocation());
            arena.getPlayers().add(player.getUniqueId());

            arena.broadcastArena("&b" + player.getName() + " &eentrou na partida &7(&a" + arena.getPlayers().size() + "/"+ConfigUtils.getMaxPLayers()+"&7)");
            checkStart(arena);
        });
    }

    private void checkStart(Arena arena) {
        int playersIn = arena.getPlayers().size();
        int minNeeded = ConfigUtils.getMinPlayers();


        if (playersIn >= minNeeded && arena.getState() == GameState.WAITING) {
            if (playersIn < ConfigUtils.getMinPlayers()) {
                arena.broadcastArena("§eAguardando mais jogadores para iniciar...");
                return;
            }
            plugin.getGameManager().setGameState(arena, GameState.STARTING);
        }
    }
}
