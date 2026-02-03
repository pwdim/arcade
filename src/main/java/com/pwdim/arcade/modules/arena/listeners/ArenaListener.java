package com.pwdim.arcade.modules.arena.listeners;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arena.model.Arena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ArenaListener implements Listener {

    public final Arcade plugin;

    public ArenaListener(Arcade plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        Arena playerArena = plugin.getArenaManager().getPlayerArena(p);

        if (playerArena != null) {
            e.setCancelled(true);
            playerArena.broadcastArena(p.getDisplayName() + ": &r" + e.getMessage());
        } else {
            e.setCancelled(false);
            e.setFormat(p.getDisplayName() + ": "+ ChatColor.RESET + e.getMessage());
        }

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        Arena arena = plugin.getArenaManager().getPlayerArena(p);

        if (arena != null) {
            arena.getPlayers().remove(p.getUniqueId());
        }
    }
}
