package com.pwdim.arcade.modules.room.inventories;

import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.ConfigUtils;
import com.pwdim.arcade.utils.GeneralUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RoomItem {


    public ItemStack roomItem(Arena arena){
        if (arena == null){
            return nullItem();
        }
        ItemStack item = null;
        List<String> lore = new ArrayList<>();
        String state = "";
        switch (arena.getState()){
            case WAITING:
                state = "&eEsperando";
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
                break;
            case STARTING:
                state = "&aIniciando";
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                break;
            case PLAYING:
                state = "&2Jogando";
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
                break;
            case ENDING:
                state = "&6Finalizando";
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
                break;
            case RESTARTING:
                state = "&cReiniciando";
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
                break;
        }
        lore.add(ColorUtil.color("&eMapa: &a" + arena.getMapName()));
        lore.add(ColorUtil.color("&eJogadores: &a" + arena.getPlayers().size() + "&e/&a" + ConfigUtils.getMaxPLayers()));
        lore.add(ColorUtil.color("&eStatus: " + state));

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColorUtil.color("&b&o" + arena.getId()));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack fillItem(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColorUtil.color("&r"));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack backItem(){
        ItemStack item = new ItemStack(Material.EMPTY_MAP, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.color("&cVoltar"));
        item.setItemMeta(meta);

        return item;

    }

    public ItemStack reloadItem(){
        ItemStack item = new ItemStack(Material.WATCH, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.color("&aRecarregar"));
        item.setItemMeta(meta);

        return item;

    }

    public ItemStack nullItem(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.color("&cNada encontrado!"));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack nextPageItem(){
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.color("&aPróxima Página"));
        item.setItemMeta(meta);

        return item;

    }

    public ItemStack backPageItem(){
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.color("&ePágina Anterior"));
        item.setItemMeta(meta);

        return item;

    }

    public ItemStack sendToLobby(){
        ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.color("&a&lENVIAR PARA O LOBBY"));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack killPlayer(){
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.color("&4&lELIMINAR JOGADOR"));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack goToPlayer(){
        ItemStack item = new ItemStack(Material.COMPASS, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.color("&d&lIR PARA O JOGADOR"));
        item.setItemMeta(meta);
        return item;
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


}
