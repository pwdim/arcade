package com.pwdim.arcade.modules.room.inventories;

import com.pwdim.arcade.modules.arena.model.Arena;
import com.pwdim.arcade.utils.ColorUtil;
import com.pwdim.arcade.utils.ConfigUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RoomItem {


    public ItemStack roomItem(Arena arena){
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


}
