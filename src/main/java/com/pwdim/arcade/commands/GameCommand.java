package com.pwdim.arcade.commands;


import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.modules.game.GameManager;
import com.pwdim.arcade.modules.game.GameState;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor {
    private final GameManager gameManager;
    private final Arcade plugin;

    public GameCommand(Arcade plugin, GameManager gameManager){
        this.plugin = plugin;
        this.gameManager = gameManager;
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
        Arena arenaAtual = plugin.getArenaManager().getActiveArenas().values().stream()
                .filter(a -> a.getWorld().getName().equals(p.getWorld().getName()))
                .findFirst()
                .orElse(null);
        if (arenaAtual == null){
            p.sendMessage(ColorUtil.color("&eVocẽ precisa estar em uma sala para executar esse comando!"));
            return true;
        }

        if (args.length == 0){
            p.sendMessage(ColorUtil.color("&cUse: /game <start/stop>"));
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand){
            case "start":
                if (!(arenaAtual.getState() == GameState.WAITING)){
                    p.sendMessage(ColorUtil.color("&cAguarde até o GameState ser WAITING &o" + arenaAtual.getState().toString()));
                } else {
                    gameManager.setGameState(arenaAtual, GameState.STARTING);
                    p.sendMessage(ColorUtil.color("&aForçando início da arena: &b" + arenaAtual.getId()));
                }
                break;
            case "stop":
                if (!(arenaAtual.getState() == GameState.PLAYING)){
                    p.sendMessage(ColorUtil.color("&cAguarde até o GameState ser PLAYING &o" + arenaAtual.getState().toString()));
                } else {
                    gameManager.setGameState(arenaAtual, GameState.ENDING);
                    p.sendMessage(ColorUtil.color("&cForçando encerramento da arena: &b" + arenaAtual.getId()));
                }
                break;
            default:
                p.sendMessage(ColorUtil.color("&cUse: /game <start/stop>"));
                break;
        }


        return true;
    }
}
