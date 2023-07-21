package eaglemc.Shop;

import eaglemc.GameManager.GameManager;
import eaglemc.Perks;
import eaglemc.Trails;
import eaglemc.Utils.GlowEnchantment;
import eaglemc.Utils.Kit;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Menu {


    private static final Items items = new Items();
    public static void OpenMainShop(Player p, UPlayer up){
        p.closeInventory();
        Inventory inv = Bukkit.createInventory(null, 45, main.color("&6&l⚔ PvP &e&l4.0 &e&lShop"));
        inv.setItem(10,items.getPerkInSlot(up,1));
        inv.setItem(12,items.getPerkInSlot(up,2));
        inv.setItem(14,items.getPerkInSlot(up,3));
        inv.setItem(16,items.getPerkInSlot(up,4));

        inv.setItem(30,items.Trails());
        inv.setItem(32,items.Kits());
        p.openInventory(inv);
    }
    public static void OpenPerksMenu(Player p, UPlayer up,int slot){
        Inventory inv = Bukkit.createInventory(null,36,"Choose a Perk");
        inv.setItem(11,items.getShopPerk(up,1));
        inv.setItem(12,items.getShopPerk(up,2));
        inv.setItem(13,items.getShopPerk(up,3));
        inv.setItem(14,items.getShopPerk(up,4));
        inv.setItem(15,items.getShopPerk(up,5));

        inv.setItem(21,items.getShopPerk(up,6));
        inv.setItem(22,items.getShopPerk(up,7));
        inv.setItem(23,items.getShopPerk(up,8));


        inv.setItem(27,items.GoBack());
        inv.setItem(35,items.Clear());
        p.openInventory(inv);

    }

    public static void OpenTrailsMenu(Player p , UPlayer up){
        Inventory inv = Bukkit.createInventory(null,27,"Arrow Trails");
        inv.setItem(11,items.getTrail(Trails.HEARTS,up));
        inv.setItem(12,items.getTrail(Trails.SMOKE,up));
        inv.setItem(13,items.getTrail(Trails.MAGIC,up));
        inv.setItem(14,items.getTrail(Trails.LAVA,up));
        inv.setItem(15,items.getTrail(Trails.RAINBOW,up));
        inv.setItem(26,items.ClearTrail());
        inv.setItem(18,items.GoBack());
        p.openInventory(inv);
    }

    public static void OpenPurchaseMenuPerks(Player p,Perks perk){
        Inventory inv = Bukkit.createInventory(null,27,"Are you sure ?");
        inv.setItem(11,Purchase(perk.getPerkName(),perk.getPrice()));
        inv.setItem(15,Decline());
        p.openInventory(inv);
    }
    public static void OpenPurchaseMenuTrails(Player p,Trails trails){
        Inventory inv = Bukkit.createInventory(null,27,"Are you sure ?.");
        inv.setItem(11,Purchase(trails.getDisplayName(),trails.getPrice()));
        inv.setItem(15,Decline());
        p.openInventory(inv);
    }

    public static void OpenKitMenu(Player p, GameManager gm){
        Inventory inv = Bukkit.createInventory(null,27,"Kits");
        UPlayer up = gm.getPlayerManager().getPlayer(p);
        for(Kit kits:gm.getKitManager().getKits().values()){
            inv.setItem(kits.getSlot(),getKitItem(p,kits,up));
        }
        inv.setItem(18,new Items().GoBack());
        p.openInventory(inv);

    }






    private static ItemStack getKitItem(Player p, Kit kit,UPlayer up){
        ItemStack item = new ItemStack(kit.getIcon());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color(kit.getDisplayName()));
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(main.color("&7Right click to view"));
        lore.add("  ");
        if(up.getKit().equals(kit)){
            lore.add(main.color("&aSelected!"));
            GlowEnchantment glowEnchantment = new GlowEnchantment(70);
            meta.addEnchant(glowEnchantment,1,true);

        }else{
            if(p.hasPermission(kit.getPermission()) || kit.getPermission().equals("noperm")){
                lore.add(main.color("&aLeft Click To select!"));
            }else if(kit.isBuyable()){
                lore.add(main.color("&7Cost: &6"+kit.getPrice()));
                lore.add(main.color("&eLeft Click To purchase this kit"));
            }
            else{
                lore.add(main.color("&cYou can't purchase this kit"));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
        private  static  ItemStack Purchase(String name,int price){
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
