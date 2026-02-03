package com.pwdim.arcade.modules.room.listeners;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.NMSUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RoomListener implements Listener {

    private final Arcade plugin;

    public RoomListener(Arcade plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;


        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        String title = e.getInventory().getTitle();

        if (title.contains("Menu de salas")) {
            if (e.getSlot() == 53 && item.getType() == Material.ARROW) {
                int currentPage = extractPage(title);
                player.openInventory(plugin.getRoomManager().getRoomInventory().getInventory( (currentPage + 1)));
            } else if (e.getSlot() == 45 && item.getType() == Material.ARROW) {
                int currentPage = extractPage(title);
                if (currentPage > 0) {
                    player.openInventory(plugin.getRoomManager().getRoomInventory().getInventory( (currentPage - 1)));
                }
            }

            String arenaID = NMSUtils.getCustomNBT(item, "arenaID");
            if (arenaID != null) {
                Arena arena = plugin.getArenaManager().getArena(arenaID);
                if (arena != null) {
                    player.openInventory(plugin.getRoomManager().getRoomManageInventory().manageInventory(arena, player));
                }
            }
        }


        String action = NMSUtils.getCustomNBT(item, "action");
        if (action != null) {
            String manageArenaID = NMSUtils.getCustomNBT(item, "manageArenaID");
            Arena arena = plugin.getArenaManager().getArena(manageArenaID);

            switch (action) {
                case "confirm_delete":
                    plugin.getArenaManager().finishArena(manageArenaID);
                    player.closeInventory();
                    player.sendMessage(ColorUtil.color("&bArena &c" + manageArenaID + " &bfinalizada com sucesso"));
                    player.playSound(player.getLocation(), Sound.ANVIL_USE, 2, player.getLocation().getPitch());
                    plugin.getArenaManager().getArena(manageArenaID).getPlayers().forEach(
                            uuid -> Bukkit.getPlayer(uuid).sendMessage(ColorUtil.color("&cA sala que você estava foi interrompida!")));
                    break;
                case "cancel_delete":
                    player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 2, player.getLocation().getPitch());
                    player.openInventory(
                            plugin.getRoomManager().getRoomManageInventory().manageInventory(arena, player)
                    );
                    break;
                case "arena_delete":
                    player.openInventory(plugin.getRoomManager().getRoomManageInventory().deleteRoomInventory(plugin.getArenaManager().getArena(manageArenaID)));
                    break;
                case "arena_players":
                    player.openInventory(plugin.getRoomManager().getRoomManageInventory().playersListInventory(arena));
                    break;
                case "arena_players_manage":

                    break;
                case "back_item_playerlist":
                    player.closeInventory();
                    player.openInventory(
                            plugin.getRoomManager().getRoomManageInventory().manageInventory(arena, player)
                    );

                    break;
                case "reload_item":
                    player.updateInventory();
                    break;
            }
        }
    }

    private int extractPage(String title) {
        try {
            String parts = title.substring(title.lastIndexOf("(") + 1, title.lastIndexOf(")"));
            return Integer.parseInt(parts) - 1;
        } catch (Exception ex) {
            return 0;
        }
    }
}
