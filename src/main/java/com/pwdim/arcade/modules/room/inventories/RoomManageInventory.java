package com.pwdim.arcade.modules.room.inventories;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.GeneralUtils;
import com.pwdim.arcade.utils.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.UUID;

public class RoomManageInventory {

    private final Arcade plugin;

    public RoomManageInventory(Arcade plugin){
        this.plugin = plugin;
    }

    private RoomItem roomItem(){

        return plugin.getRoomManager().getRoomItem();
    }

    public Inventory deleteRoomInventory(Arena arena) {
        Inventory inv = Bukkit.createInventory(null, 45, ColorUtil.color("&eRemover Sala? "));

        for (int i = 9; i < 18; i++) {
            inv.setItem(i, roomItem().fillItem());
        }

        inv.setItem(4, roomItem().roomItem(arena));

        ItemStack confirm = roomItem().confirmRemoveRoomItem();
        confirm = NMSUtils.setCustomNBT(confirm, "manageArenaID", arena.getId());
        confirm = NMSUtils.setCustomNBT(confirm, "action", "ArenaDelete_Confirm");

        ItemStack cancel = roomItem().cancelRemoveRoomItem();
        cancel = NMSUtils.setCustomNBT(cancel, "manageArenaID", arena.getId());
        cancel = NMSUtils.setCustomNBT(cancel, "action", "ArenaDelete_Cancel");

        ItemStack backItem = roomItem().backItem();
        backItem = NMSUtils.setCustomNBT(backItem, "manageArenaID", arena.getId());
        backItem = NMSUtils.setCustomNBT(backItem, "action", "ArenaDelete_Back");
        inv.setItem(36, backItem);

        ItemStack reloadItem = roomItem().reloadItem();
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "manageArenaID", arena.getId());
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "action", "ArenaDelete_Menu");
        inv.setItem(40, reloadItem);

        inv.setItem(21, cancel);
        inv.setItem(23, confirm);

        return inv;
    }

    public Inventory playersListInventory(Arena arena) {
        Inventory inv = Bukkit.createInventory(null, 45, ColorUtil.color("&eLista de Jogadores "));

        for (int i = 9; i < 18; i++) {
            inv.setItem(i, roomItem().fillItem());
        }
        inv.setItem(4, roomItem().roomItem(arena));
        ItemStack backItem = roomItem().backItem();
        backItem = NMSUtils.setCustomNBT(backItem, "manageArenaID", arena.getId());
        backItem = NMSUtils.setCustomNBT(backItem, "action", "PlayerList_Back");
        inv.setItem(36, backItem);
        ItemStack reloadItem = roomItem().reloadItem();
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "manageArenaID", arena.getId());
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "action", "PlayerList_Menu");
        inv.setItem(40, reloadItem);


        int i = 18;

        if (!(arena.getPlayers().isEmpty())){
            for (UUID uuid : arena.getPlayers()){
                Player p = Bukkit.getPlayer(uuid);
                ItemStack head = GeneralUtils.getHead(p);
                head = NMSUtils.setCustomNBT(head, "manageArenaID", arena.getId());
                head = NMSUtils.setCustomNBT(head, "managePlayer", p.getUniqueId().toString());
                head = NMSUtils.setCustomNBT(head,"action", "PlayerManager_Menu");

                inv.setItem(i, head);
                i++;
            }
        } else {
            inv.setItem(31, roomItem().nullItem());
        }


        return inv;
    }

    public Inventory playerManageInventory(Player p){
        Arena arena = plugin.getArenaManager().getPlayerArena(p);

        Inventory inv = Bukkit.createInventory(null, 36, ColorUtil.color("&eGerenciar Jogador "));

        ItemStack head = GeneralUtils.getHead(p);
        head = NMSUtils.setCustomNBT(head, "manageArenaID", arena.getId());
        head = NMSUtils.setCustomNBT(head, "managePlayer", p.getUniqueId().toString());
        head = NMSUtils.setCustomNBT(head,"action", "PlayerManager_Menu");
        inv.setItem(4, head);

        ItemStack backItem = roomItem().backItem();
        backItem = NMSUtils.setCustomNBT(backItem, "manageArenaID", arena.getId());
        backItem = NMSUtils.setCustomNBT(backItem, "action", "PlayerManager_Back");
        inv.setItem(27, backItem);

        ItemStack goToPlayer = roomItem().goToPlayer();
        goToPlayer = NMSUtils.setCustomNBT(goToPlayer, "manageArenaID", arena.getId());
        goToPlayer = NMSUtils.setCustomNBT(goToPlayer, "action", "PlayerManager_GoTo");
        inv.setItem(22, goToPlayer);

        ItemStack killPlayer = roomItem().killPlayer();
        killPlayer = NMSUtils.setCustomNBT(killPlayer, "manageArenaID", arena.getId());
        killPlayer = NMSUtils.setCustomNBT(killPlayer, "action", "PlayerManager_Kill");
        inv.setItem(21, killPlayer);

        ItemStack sendLobby = roomItem().sendToLobby();
        sendLobby = NMSUtils.setCustomNBT(sendLobby, "manageArenaID", arena.getId());
        sendLobby = NMSUtils.setCustomNBT(sendLobby, "action", "PlayerManager_SendLobby");
        inv.setItem(23, sendLobby);




        return inv;

    }

    public Inventory manageInventory(Arena arena, @Nullable Player p){
        Inventory inv = Bukkit.createInventory(null, 45, ColorUtil.color("&eGerenciar Sala"));

        for (int i = 9; i < 18; i++) {
            inv.setItem(i, roomItem().fillItem());
        }

        inv.setItem(4, roomItem().roomItem(arena));

        ItemStack delete = roomItem().deleteRoomItem();
        delete = NMSUtils.setCustomNBT(delete, "manageArenaID", arena.getId());
        delete = NMSUtils.setCustomNBT(delete,"action", "ArenaDelete_Menu");

        ItemStack backItem = roomItem().backItem();
        backItem = NMSUtils.setCustomNBT(backItem, "manageArenaID", arena.getId());
        backItem = NMSUtils.setCustomNBT(backItem, "action", "Arenas_Menu");
        inv.setItem(36, backItem);

        ItemStack reloadItem = roomItem().reloadItem();
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "manageArenaID", arena.getId());
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "action", "ArenaDelete_Menu");
        inv.setItem(40, reloadItem);

        if (p == null){
            Player player = Bukkit.getPlayer(UUID.fromString("b842a9d2-9548-434b-8a09-4ad3ecca119b"));
            ItemStack list = roomItem().playersRoomItem(player);
            list = NMSUtils.setCustomNBT(list, "manageArenaID", arena.getId());
            list = NMSUtils.setCustomNBT(list, "action", "PlayerList_Menu");

            inv.setItem(30, list);

        } else {
            ItemStack list = roomItem().playersRoomItem(p);
            list = NMSUtils.setCustomNBT(list, "manageArenaID", arena.getId());
            list = NMSUtils.setCustomNBT(list, "action", "PlayerList_Menu");
            inv.setItem(30, list);
        }

        inv.setItem(29, delete);

        return inv;
    }


}
