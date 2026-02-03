package com.pwdim.arcade.modules.game.tasks;

import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.modules.game.GameManager;
import com.pwdim.arcade.modules.game.GameState;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {

    private final GameManager gameManager;
    private final Arena arena;

    public GameStartCountdownTask(GameManager gameManager, Arena arena){
        this.gameManager = gameManager;
        this.arena = arena;
    }


    private int timeLeft = ConfigUtils.timeLeft();
    @Override
    public void run() {
        timeLeft--;
        if (arena.getState() != GameState.STARTING) {
            cancel();
        }
        if (timeLeft <= 3 && timeLeft >= 1){
            arena.titleArena("&c" + timeLeft, null, 1, 20, 1);
        }

        if (timeLeft < 1){
            cancel();
            gameManager.setGameState(arena, GameState.PLAYING);
        }
        int btimeLeft = timeLeft + 1;
        arena.broadcastArena("&b" + btimeLeft + " &esegundos para o jogo iniciar!");

    }
}
