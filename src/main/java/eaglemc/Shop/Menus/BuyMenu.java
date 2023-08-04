package eaglemc.Shop.Menus;

import eaglemc.enums.DeathCry;
import eaglemc.enums.Perks;
import eaglemc.enums.Trails;
import eaglemc.Utils.Inventory.BuyInventory;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BuyMenu {

    public static void OpenPurchaseMenu(Player p, Object ToBuy){
        Inventory inv = Bukkit.createInventory(new BuyInventory(ToBuy),27,"Are you sure ?");
        if(ToBuy instanceof Perks){
            inv.setItem(11,Purchase(((Perks)ToBuy).getPerkName(),((Perks)ToBuy).getPrice()));
        }
        if(ToBuy instanceof Trails){
            inv.setItem(11,Purchase(((Trails)ToBuy).getDisplayName(),((Trails)ToBuy).getPrice()));
        }
        if(ToBuy instanceof DeathCry){
            inv.setItem(11,Purchase(((DeathCry)ToBuy).getDisplayName(),((DeathCry)ToBuy).getPrice()));
        }
        inv.setItem(15,Decline());
        p.openInventory(inv);
    }

    private  static ItemStack Purchase(String name, int price){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 5);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&aPurchase"));
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(main.color("&7Purchasing: "+name));
        lore.add(main.color("&7Price: &6"+price));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    private static ItemStack Decline(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 14);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&cCancel"));
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(main.color("&7Cancel and return"));
        lore.add(main.color("&7To the main menu"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
