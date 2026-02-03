package com.pwdim.arcade.modules.room.inventories;

import com.pwdim.arcade.core.Arcade;
import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.GeneralUtils;
import com.pwdim.arcade.utils.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomManageInventory {

    private final Arcade plugin;

    public RoomManageInventory(Arcade plugin){
        this.plugin = plugin;
    }


    public ItemStack deleteRoomItem(){
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ColorUtil.color("&cCancelar partida e excluir mundo"));
        meta.setLore(lore);
        meta.setDisplayName(ColorUtil.color("&4&lREMOVER SALA"));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack playersRoomItem(Player p){
        ItemStack item = GeneralUtils.getHead(p);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ColorUtil.color("&eVerifique os jogadores da partida"));
        meta.setLore(lore);
        meta.setDisplayName(ColorUtil.color("&a&lLISTA DE JOGADORES"));
        item.setItemMeta(meta);

        return item;

    }

    public ItemStack confirmRemoveRoomItem(){
        ItemStack item = new ItemStack(Material.WOOL, 1, (short) 5);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ColorUtil.color("&aRemover sala"));
        meta.setLore(lore);
        meta.setDisplayName(ColorUtil.color("&a&lCONFIRMAR"));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack cancelRemoveRoomItem(){
        ItemStack item = new ItemStack(Material.WOOL, 1, (short) 14);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ColorUtil.color("&cNão remover sala"));
        meta.setLore(lore);
        meta.setDisplayName(ColorUtil.color("&c&lCANCELAR"));
        item.setItemMeta(meta);

        return item;
    }


    public Inventory deleteRoomInventory(Arena arena) {
        Inventory inv = Bukkit.createInventory(null, 45, ColorUtil.color("&eRemover Sala? "));

        for (int i = 9; i < 18; i++) {
            inv.setItem(i, plugin.getRoomManager().getRoomItem().fillItem());
        }

        inv.setItem(4, plugin.getRoomManager().getRoomItem().roomItem(arena));

        ItemStack confirm = confirmRemoveRoomItem();
        confirm = NMSUtils.setCustomNBT(confirm, "manageArenaID", arena.getId());
        confirm = NMSUtils.setCustomNBT(confirm, "action", "confirm_delete");

        ItemStack cancel = cancelRemoveRoomItem();
        cancel = NMSUtils.setCustomNBT(cancel, "manageArenaID", arena.getId());
        cancel = NMSUtils.setCustomNBT(cancel, "action", "cancel_delete");

        ItemStack backItem = plugin.getRoomManager().getRoomItem().backItem();
        backItem = NMSUtils.setCustomNBT(backItem, "manageArenaID", arena.getId());
        backItem = NMSUtils.setCustomNBT(backItem, "action", "back_delete");
        inv.setItem(36, backItem);

        ItemStack reloadItem = plugin.getRoomManager().getRoomItem().reloadItem();
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "manageArenaID", arena.getId());
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "action", "reload_delete");
        inv.setItem(40, reloadItem);

        inv.setItem(21, cancel);
        inv.setItem(23, confirm);

        return inv;
    }

    public Inventory playersListInventory(Arena arena) {
        Inventory inv = Bukkit.createInventory(null, 45, ColorUtil.color("&eLista de Jogadores "));

        for (int i = 9; i < 18; i++) {
            inv.setItem(i, plugin.getRoomManager().getRoomItem().fillItem());
        }
        inv.setItem(4, plugin.getRoomManager().getRoomItem().roomItem(arena));
        ItemStack backItem = plugin.getRoomManager().getRoomItem().backItem();
        backItem = NMSUtils.setCustomNBT(backItem, "manageArenaID", arena.getId());
        backItem = NMSUtils.setCustomNBT(backItem, "action", "back_playerlist");
        inv.setItem(36, backItem);
        ItemStack reloadItem = plugin.getRoomManager().getRoomItem().reloadItem();
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "manageArenaID", arena.getId());
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "action", "reload_playerlist");
        inv.setItem(40, reloadItem);


        int i = 18;

        if (!(arena.getPlayers().isEmpty())){
            for (UUID uuid : arena.getPlayers()){
                Player p = Bukkit.getPlayer(uuid);
                ItemStack head = GeneralUtils.getHead(p);
                head = NMSUtils.setCustomNBT(head, "manageArenaID", arena.getId());
                head = NMSUtils.setCustomNBT(head, "managePlayer", p.getUniqueId().toString());
                head = NMSUtils.setCustomNBT(head,"action", "arena_players_manage");

                inv.setItem(i, head);
                i++;
            }
        } else {
            inv.setItem(31, plugin.getRoomManager().getRoomItem().nullItem());
        }


        return inv;
    }

    public Inventory manageInventory(Arena arena, @Nullable Player p){
        Inventory inv = Bukkit.createInventory(null, 45, ColorUtil.color("&eGerenciar Sala"));

        for (int i = 9; i < 18; i++) {
            inv.setItem(i, plugin.getRoomManager().getRoomItem().fillItem());
        }

        inv.setItem(4, plugin.getRoomManager().getRoomItem().roomItem(arena));

        ItemStack delete = deleteRoomItem();
        delete = NMSUtils.setCustomNBT(delete, "manageArenaID", arena.getId());
        delete = NMSUtils.setCustomNBT(delete,"action", "arena_delete");

        ItemStack backItem = plugin.getRoomManager().getRoomItem().backItem();
        backItem = NMSUtils.setCustomNBT(backItem, "manageArenaID", arena.getId());
        backItem = NMSUtils.setCustomNBT(backItem, "action", "back_manage");
        inv.setItem(36, backItem);

        ItemStack reloadItem = plugin.getRoomManager().getRoomItem().reloadItem();
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "manageArenaID", arena.getId());
        reloadItem = NMSUtils.setCustomNBT(reloadItem, "action", "reload_manage");
        inv.setItem(40, reloadItem);

        if (p == null){
            Player player = Bukkit.getPlayer(UUID.fromString("b842a9d2-9548-434b-8a09-4ad3ecca119b"));
            ItemStack list = playersRoomItem(player);
            list = NMSUtils.setCustomNBT(list, "manageArenaID", arena.getId());
            list = NMSUtils.setCustomNBT(list, "action", "arena_players");

            inv.setItem(30, list);

        } else {
            ItemStack list = playersRoomItem(p);
            list = NMSUtils.setCustomNBT(list, "manageArenaID", arena.getId());
            list = NMSUtils.setCustomNBT(list, "action", "arena_players");
            inv.setItem(30, list);
        }

        inv.setItem(29, delete);

        return inv;
    }


}
