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

import java.util.UUID;

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

        /*

        ex:
        ArenaDelete_Confirm
        ArenaDelete_Cancel
        PlayerManager_menu
        PlayerManager_kill
        PlayerManager_confirm
        PlayerManager_goTo

         */
        if (action != null) {
            String manageArenaID = NMSUtils.getCustomNBT(item, "manageArenaID");
            Arena arena = plugin.getArenaManager().getArena(manageArenaID);

            String managePlayerUUIDStr = NMSUtils.getCustomNBT(item, "managePlayer");
            Player target = null;

            if (managePlayerUUIDStr != null && !managePlayerUUIDStr.isEmpty()) {
                try {
                    UUID uuid = UUID.fromString(managePlayerUUIDStr);
                    target = Bukkit.getPlayer(uuid);
                } catch (IllegalArgumentException ex) {
                    plugin.logger("&4&lERROR &c" + ex.getMessage());
                }
            }

            switch (action) {
                case "ArenaDelete_Confirm":
                    plugin.getArenaManager().getArena(manageArenaID).getPlayers().forEach(
                            uuid -> Bukkit.getPlayer(uuid).sendMessage(ColorUtil.color("&cA sala que você estava está reiniciando!")));
                    plugin.getArenaManager().finishArena(manageArenaID);
                    player.closeInventory();
                    player.sendMessage(ColorUtil.color("&bArena &c" + manageArenaID + " &bfinalizada com sucesso"));
                    player.playSound(player.getLocation(), Sound.ANVIL_USE, 2, player.getLocation().getPitch());
                    break;


                case "ArenaDelete_Cancel":
                    player.playSound(player.getLocation(), Sound.STEP_STONE, 2, player.getLocation().getPitch());
                    player.openInventory(
                            plugin.getRoomManager().getRoomManageInventory().manageInventory(arena, player)
                    );
                    break;


                case "ArenaDelete_Menu":
                    player.openInventory(
                            plugin.getRoomManager().getRoomManageInventory().deleteRoomInventory(arena)
                    );
                    break;


                case "PlayerList_Menu":
                    player.openInventory(plugin.getRoomManager().getRoomManageInventory().playersListInventory(arena));
                    break;


                case "ArenaDelete_Back":
                case "PlayerList_Back":
                case "ArenaManager_Menu":
                    player.openInventory(
                            plugin.getRoomManager().getRoomManageInventory().manageInventory(arena, player)
                    );
                    break;


                case "ArenaManager_Back":
                    player.openInventory(
                            plugin.getRoomManager().getRoomInventory().getInventory(extractPage(title))
                    );
                    break;


                case "Arenas_Menu":
                    player.openInventory(
                            plugin.getRoomManager().getRoomInventory().getInventory(0)
                    );
                    break;



                case "PlayerManager_Menu":
                    if (target != null) {
                        player.openInventory(plugin.getRoomManager().getRoomManageInventory().playerManageInventory(target));
                    } else {
                        player.sendMessage(ColorUtil.color("&cEste jogador não está mais online."));
                        player.closeInventory();
                    }
                    break;

                case "PlayerManager_GoTo":
                    if (target != null) {
                        player.teleport(target.getLocation());
                        player.sendMessage(ColorUtil.color("&aTeleportado para " + target.getName()));
                    } else {
                        player.sendMessage(ColorUtil.color("&cJogador offline."));
                    }
                    break;

                case "PlayerManager_Kill":
                    if (target != null) {
                        target.setHealth(0);
                        player.sendMessage(ColorUtil.color("&cJogador eliminado."));
                        player.openInventory(plugin.getRoomManager().getRoomManageInventory().playersListInventory(arena));
                    }
                    break;

                case "PlayerManager_SendLobby":
                    if (target != null) {
                        plugin.getPlayerManager().sendToLobby(target);
                        player.sendMessage(ColorUtil.color("&aJogador enviado ao lobby."));
                        player.openInventory(plugin.getRoomManager().getRoomManageInventory().playersListInventory(arena));
                    }
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
