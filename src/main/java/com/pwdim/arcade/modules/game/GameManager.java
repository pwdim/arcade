package com.pwdim.arcade.modules.game;


import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.coreitems.item.LobbyItem;
import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.modules.blocks.BlockManager;
import com.pwdim.arcade.modules.game.tasks.GameStartCountdownTask;
import com.pwdim.arcade.modules.player.PlayerManager;
import org.bukkit.Bukkit;

public class GameManager {

    private final Arcade plugin;
    private final BlockManager blockManager;
    private final PlayerManager playerManager;
    protected GameStartCountdownTask gameStartCountdownTask;

    public GameManager(Arcade plugin) {
        this.plugin = plugin;

        this.blockManager = new BlockManager(this);
        this.playerManager = new PlayerManager(this, plugin);
    }

    public void setGameState(Arena arena, GameState arenaState){
        this.gameStartCountdownTask = new GameStartCountdownTask(this, arena);
        if (!arena.getState().canTransitionTo(arenaState)){
            return;
        }
        arena.setState(arenaState);

        switch (arenaState){
            case WAITING:
                arena.getPlayers().forEach(uuid -> LobbyItem.giveItem(Bukkit.getPlayer(uuid)));
                break;
            case STARTING:
                new GameStartCountdownTask(this, arena).runTaskTimer(plugin, 0, 19);
                break;
            case PLAYING:
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    arena.getPlayers().forEach(uuid -> Bukkit.getPlayer(uuid).getInventory().clear());
                    arena.broadcastArena("&aO jogo iniciou!");
                }, 25L);
                break;
            case ENDING:
                arena.titleArena("&c&lFIM DE JOGO!", "&eObrigado por jogar!", 1, 1000, 300);
                arena.getPlayers().forEach(uuid -> LobbyItem.giveItem(Bukkit.getPlayer(uuid)));

                Bukkit.getScheduler().runTaskLater(plugin, () -> plugin.getGameManager().setGameState(arena, GameState.RESTARTING), 20L*15);
                break;
            case RESTARTING:
                arena.getPlayers().forEach(uuid -> LobbyItem.removeItem(Bukkit.getPlayer(uuid)));
                plugin.getArenaManager().finishArena(arena.getId());
                break;
            default:
                setGameState(arena, GameState.WAITING);
                break;
        }
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
