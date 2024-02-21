package eaglemc.Shop.Menus;

import eaglemc.Utils.enums.*;
import eaglemc.GameManager.GameManager;
import eaglemc.Shop.Items.Items;
import eaglemc.Utils.others.GlowEnchantment;
import eaglemc.Utils.Holders.Kit;
import eaglemc.Utils.Inventory.SelectionInventory;
import eaglemc.Utils.Holders.UPlayer;
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
        Inventory inv = Bukkit.createInventory(null, 54, main.color("&6&l⚔ PvP &e&l4.0 &e&lShop"));
        for(int i=0;i<inv.getSize();i++){
            inv.setItem(i,getGlass());
        }
        inv.setItem(10,items.getPerkInSlot(up,1));
        inv.setItem(12,items.getPerkInSlot(up,2));
        inv.setItem(14,items.getPerkInSlot(up,3));
        inv.setItem(16,items.getPerkInSlot(up,4));

        inv.setItem(28,items.Trails());
        inv.setItem(30,items.BattleCry());
        inv.setItem(32,items.Kits());
        inv.setItem(34,items.KillStreakEffect());

        inv.setItem(45,items.Close());
        inv.setItem(53,items.SaveInventory());

        p.openInventory(inv);
    }
    public static void OpenPerksMenu(Player p, UPlayer up,int slot){
        Inventory inv = Bukkit.createInventory(new SelectionInventory(Type.Perks),36,"Choose a Perk");
        for(int i=0;i<inv.getSize();i++){
            inv.setItem(i,getGlass());
        }
        inv.setItem(11,items.getShopPerk(up, Perks.TNT));
        inv.setItem(12,items.getShopPerk(up,Perks.GOLDEN_HEAD));
        inv.setItem(13,items.getShopPerk(up,Perks.STRENGTH));
        inv.setItem(14,items.getShopPerk(up,Perks.Vampire));
        inv.setItem(15,items.getShopPerk(up,Perks.GRAPPLER));

        inv.setItem(21,items.getShopPerk(up,Perks.BERSERKER));
        inv.setItem(22,items.getShopPerk(up,Perks.Endless_Quiver));
        inv.setItem(23,items.getShopPerk(up,Perks.DOUBLE_XP));


        inv.setItem(27,items.GoBack());
        inv.setItem(35,items.Clear());
        p.openInventory(inv);

    }

    public static void OpenTrailsMenu(Player p , UPlayer up){
        Inventory inv = Bukkit.createInventory(new SelectionInventory(Type.Trails),27,"Arrow Trails");
        for(int i=0;i<inv.getSize();i++){
            inv.setItem(i,getGlass());
        }
        inv.setItem(11,items.getTrail(Trails.HEARTS,up));
        inv.setItem(12,items.getTrail(Trails.SMOKE,up));
        inv.setItem(13,items.getTrail(Trails.MAGIC,up));
        inv.setItem(14,items.getTrail(Trails.LAVA,up));
        inv.setItem(15,items.getTrail(Trails.RAINBOW,up));
        inv.setItem(26,items.ClearTrail());
        inv.setItem(18,items.GoBack());
        p.openInventory(inv);
    }


    public static void openDeathCryMenu(Player p,UPlayer up){
        Inventory inv = Bukkit.createInventory(new SelectionInventory(Type.DeathCry),27,"Death Cry");
        for(int i=0;i<inv.getSize();i++){
            inv.setItem(i,getGlass());
        }
        inv.setItem(11,items.getDeathCry(DeathCry.CAT,up));
        inv.setItem(12,items.getDeathCry(DeathCry.ENDER_MAN,up));
        inv.setItem(13,items.getDeathCry(DeathCry.GHAST,up));
        inv.setItem(14,items.getDeathCry(DeathCry.Guardian,up));
        inv.setItem(15,items.getDeathCry(DeathCry.wolf,up));

        inv.setItem(26,items.ClearDeathCry());
        inv.setItem(18,items.GoBack());
        p.openInventory(inv);
    }
    public static void openKillStreakEffectsMenu(Player p,UPlayer up){
        Inventory inv = Bukkit.createInventory(new SelectionInventory(Type.KillStreakEffect),27,"KillStreak Effects");
        for(int i=0;i<inv.getSize();i++){
            inv.setItem(i,getGlass());
        }
        for(KillStreakEffect killStreakEffect : KillStreakEffect.values()){
            if(killStreakEffect.equals(KillStreakEffect.none))continue;
            inv.setItem(killStreakEffect.getSlot(),items.getKillStreakEffects(killStreakEffect,up));
        }
        inv.setItem(26,items.ClearKillStreakEffect());
        inv.setItem(18,items.GoBack());
        p.openInventory(inv);
    }

    public static void OpenKitMenu(Player p, GameManager gm){
        Inventory inv = Bukkit.createInventory(null,27,"Kits");
        for(int i=0;i<inv.getSize();i++){
            inv.setItem(i,getGlass());
        }
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

    private static ItemStack getGlass(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color(" "));
        item.setItemMeta(meta);
        return item;
    }

}
