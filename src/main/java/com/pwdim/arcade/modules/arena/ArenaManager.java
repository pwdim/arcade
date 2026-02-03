package com.pwdim.arcade.modules.arena;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arena.engine.ArenaEngine;
import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;

public class ArenaManager {

    private final Arcade plugin;
    private final ArenaEngine arenaEngine;
    private final Map<String, Arena> activeArenas = new HashMap<>();

    public ArenaManager(Arcade plugin){
        this.plugin = plugin;

        this.arenaEngine = new ArenaEngine(plugin);
    }

    public void setupNewArena(String mapName, Consumer<Arena> callback){
        String arenaID = mapName+"_" + System.currentTimeMillis();

        arenaEngine.createWorldInstace(mapName, arenaID, world -> {
            Arena arena = new Arena(arenaID, mapName, world);
            activeArenas.put(arenaID, arena);
            plugin.logger("&aNova sala criada para o &eMapa "+ mapName+" &b(" + arenaID + ") ");

            if (callback != null) {
                callback.accept(arena);
            }
        });
    }

    public void finishArena(String arenaID){
        Arena arena = activeArenas.get(arenaID);
        if (arena == null) return;

        String template = arena.getMapName();

        arena.getPlayers().forEach(uuid -> {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                plugin.getPlayerManager().sendToLobby(p);
            }
        });
        plugin.logger("&cFinalizando arena " + arena.getId());
        arenaEngine.deleteWorldInstace(arenaID);
        activeArenas.remove(arenaID);


        if (plugin.getArenaManager().getActiveArenas().isEmpty()){
            setupNewArena(template, null);
        }
    }

    public void finishArena(String arenaID, Consumer<Arena> callback) {
        Arena arena = activeArenas.get(arenaID);
        if (arena == null) return;

        String template = arena.getMapName();

        arena.getPlayers().forEach(uuid -> {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                p.teleport(ConfigUtils.getLobby());
            }
        });
        plugin.logger("&cFinalizando arena " + arena.getId());
        arenaEngine.deleteWorldInstace(arenaID);
        activeArenas.remove(arenaID);

        if (plugin.getArenaManager().getActiveArenas().isEmpty()){
            setupNewArena(template, null);
        }

        if (callback != null) {
            callback.accept(arena);
        }
    }

    public Map<String, Arena> getActiveArenas() {
        return activeArenas;
    }

    public boolean checkInGame(Player player){
        return getActiveArenas().values().stream().anyMatch(arena -> arena.getPlayers().contains(player.getUniqueId()));
    }

    public Arena getPlayerArena(Player p){
        return plugin.getArenaManager().getActiveArenas().values().stream()
                .filter(arena -> arena.getPlayers().contains(p.getUniqueId()))
                .findAny()
                .orElse(null);
    }

    public Arena getArena(String arenaID) {
        return plugin.getArenaManager().getActiveArenas().values().stream()
                .filter(arena -> arena.getId().equals(arenaID))
                .findAny()
                .orElse(null);

    }
}
