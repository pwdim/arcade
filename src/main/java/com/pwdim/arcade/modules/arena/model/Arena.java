package com.pwdim.arcade.modules.arena.model;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.game.GameState;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private final Arcade plugin;
    private String id;
    private String mapName;
    private World world;
    private GameState state;
    private List<UUID> players;

    public Arena(String id, String mapName, World world) {
        this.plugin = Arcade.getPlugin(Arcade.class);
        this.id = id;
        this.mapName = mapName;
        this.world = world;
        this.state = GameState.WAITING;
        this.players = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public List<UUID> getPlayers() {
        if (this.players == null) this.players = new ArrayList<>();
        return players;
    }

    public GameState getState() {
        return state;
    }

    public void addPlayer(Player player) {
        if (!getPlayers().contains(player.getUniqueId())) {
            getPlayers().add(player.getUniqueId());
        }
    }

    public void removePlayer(Player player) {
        getPlayers().remove(player.getUniqueId());
    }

    public void broadcastArena(String msg) {
        String formattedMsg = ColorUtil.color(msg);
        getPlayers().forEach(uuid -> {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                p.sendMessage(formattedMsg);
            }
        });
    }

    public void titleArena(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        getPlayers().forEach(uuid -> {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                ConfigUtils.sendTitle(p, title, subtitle, fadeIn, stay, fadeOut);
            }
        });
    }

    public boolean isFull(int maxPlayers) {
        return getPlayers().size() >= maxPlayers;
    }
}