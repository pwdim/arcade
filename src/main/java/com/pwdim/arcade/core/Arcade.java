package com.pwdim.arcade.core;

import com.pwdim.arcade.commands.*;
import com.pwdim.arcade.modules.lobby.LobbyItem;
import com.pwdim.arcade.modules.arena.listeners.ArenaListener;
import com.pwdim.arcade.modules.arcadeplayer.ArcadePlayerManager;
import com.pwdim.arcade.modules.room.RoomManager;
import com.pwdim.arcade.modules.arena.listeners.BuildListeners;
import com.pwdim.arcade.modules.arena.engine.ArenaEngine;
import com.pwdim.arcade.modules.arena.ArenaManager;
import com.pwdim.arcade.modules.game.GameManager;
import com.pwdim.arcade.modules.player.PlayerManager;
import com.pwdim.arcade.modules.room.listeners.RoomListener;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Arcade extends JavaPlugin {

    private GameManager gameManager;
    private ArenaManager arenaManager;
    private PlayerManager playerManager;
    private ArcadePlayerManager arcadePlayerManager;
    private RoomManager roomManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new ConfigUtils(this);

        this.arenaManager = new ArenaManager(this);

        this.gameManager = new GameManager(this);

        this.playerManager = gameManager.getPlayerManager();
        this.arcadePlayerManager = new ArcadePlayerManager(this);
        this.roomManager = new RoomManager(this);


        getServer().getPluginManager().registerEvents(new BuildListeners(gameManager), this);
        getServer().getPluginManager().registerEvents(new ArenaListener(this), this);
        getServer().getPluginManager().registerEvents(new ArenaEngine(this), this);
        getServer().getPluginManager().registerEvents(new LobbyItem(this), this);
        getServer().getPluginManager().registerEvents(new RoomListener(this), this);

        getCommand("game").setExecutor(new GameCommand(this, gameManager));
        getCommand("murder").setExecutor(new PlayCommand(this));
        getCommand("lobby").setExecutor(new LobbyCommand(this));
        getCommand("rooms").setExecutor(new RoomListCommand(this));
        getCommand("roomcreate").setExecutor(new RoomCreateCommand(this));
        getCommand("roomremove").setExecutor(new RoomDeleteCommand(this));
        getCommand("DEBUG").setExecutor(new DebugCommand(this));

        logger("&bO plugin foi iniciado com sucesso");
    }

    public String getPrefix() {
        String prefixConfig = getConfig().getString("plugin.prefix", "&b[Arcade]");
        return ColorUtil.color(prefixConfig + " &r");
    }

    public void logger(String log){

        log = ColorUtil.color(log);

        Bukkit.getConsoleSender().sendMessage(getPrefix() + ColorUtil.color(log));
    }

    @Override
    public void onDisable() {
        if (arenaManager != null) {
            logger("&eDescarregando arenas ativas...");
            arenaManager.getActiveArenas().keySet().forEach(id -> {
                Bukkit.unloadWorld(id, false);
            });
        }
        logger("&cO plugin foi desligado com sucesso");
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ArcadePlayerManager getArcadePlayerManager() {
        return arcadePlayerManager;
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }
}
