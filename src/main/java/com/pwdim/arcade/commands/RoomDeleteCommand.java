package com.pwdim.arcade.commands;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arena.ArenaManager;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoomDeleteCommand implements CommandExecutor {
    private final Arcade plugin;

    public RoomDeleteCommand(Arcade plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ConfigUtils.consoleError());
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0){
           p.sendMessage(ColorUtil.color("&cUse: /roomremove <arenaID>"));
            return true;
        }
        String arenaID = args[0];
        ArenaManager manager = plugin.getArenaManager();

        if (!plugin.getArenaManager().getActiveArenas().containsKey(arenaID)){
            p.sendMessage(ColorUtil.color("&cArena não encontrada"));
        }

        try{
            manager.finishArena(arenaID, (arena) -> {
                try {
                    p.sendMessage(ColorUtil.color("&bArena &c" + arena.getId() +" &bfinalizada com sucesso!"));
                } catch (Exception e) {
                    p.sendMessage(ColorUtil.color("&cErro ao processar a finalização da arena."));
                    p.sendMessage(ColorUtil.color("&4&o" + e.getMessage()));
                }
            });

        } catch (Exception e) {
            p.sendMessage(ColorUtil.color("&cErro ao finalizar a arena. "));
            p.sendMessage(ColorUtil.color("&4&o" + e.getMessage()));
        }





        return true;
    }
}
