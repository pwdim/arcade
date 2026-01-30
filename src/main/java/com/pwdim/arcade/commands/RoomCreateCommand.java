package com.pwdim.arcade.commands;

import com.pwdim.arcade.Arcade;
import com.pwdim.arcade.manager.arena.ArenaManager;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoomCreateCommand implements CommandExecutor {

    private final Arcade plugin;
    public RoomCreateCommand(Arcade plugin){
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
            ArenaManager manager = plugin.getArenaManager();
            manager.setupNewArena(ConfigUtils.getGameMap(), (newArena) ->{
                p.sendMessage(ColorUtil.color("&bArena &a" + newArena.getId()+" &bcriada com sucesso!"));
            });

            return true;
        }
        String mapName = args[0];

        ArenaManager manager = plugin.getArenaManager();
        try{
            manager.setupNewArena(mapName, (newArena) -> {
                try {
                    p.sendMessage(ColorUtil.color("&bArena &a" + newArena.getId()+" &bcriada com sucesso!"));
                } catch (Exception e) {
                    p.sendMessage(ColorUtil.color("&cErro ao criar a arena. "));
                    p.sendMessage(ColorUtil.color("&4&o" + e.getMessage()));
                }
            });

        } catch (Exception e) {
            p.sendMessage(ColorUtil.color("&cErro ao criar a arena. "));
            p.sendMessage(ColorUtil.color("&4&o" + e.getMessage()));
        }



        return true;
    }
}
