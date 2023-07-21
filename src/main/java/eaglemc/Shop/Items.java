package eaglemc.Shop;

import eaglemc.Perks;
import eaglemc.Trails;
import eaglemc.Utils.GlowEnchantment;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Items {



    public ItemStack getShopPerk(UPlayer p,int id){
        Perks perk = Perks.fromId(id);
        ItemStack item =perk.getPerkItem();
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(main.color("&7Selected: "+perk.getPerkName()));
        lore.add("  ");
        lore.addAll(perk.getLore());
        lore.add("      ");
        if(p.getSlots().containsValue(perk)){
            lore.add(main.color("&aAlready Selected"));
            GlowEnchantment glowEnchantment = new GlowEnchantment(70);
            meta.addEnchant(glowEnchantment,1,true);
        }
        else if(p.getPlayerPerks().contains(id)){
            lore.add(main.color("&aClick To Select!"));
        }else {
            lore.add(main.color("&7Cost: &6" +perk.getPrice()));
            lore.add(main.color("&eClick To Purchase !"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack Kits(){
        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&3&lKits"));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack Trails(){
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&c&lArrow Trails"));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getTrail(Trails trail,UPlayer p) {
        ItemStack item = new ItemStack(trail.getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(trail.getDisplayName());
        ArrayList<String> lore = trail.getLore();
        lore.add(" ");
        if(p.getSelectedTrail().equals(trail)){
            lore.add(main.color("&aAlready Selected"));
            GlowEnchantment glowEnchantment = new GlowEnchantment(70);
            meta.addEnchant(glowEnchantment,1,true);
        }
        else if(p.getTrails().contains(trail)){
            lore.add(main.color("&aClick To Select!"));
        }else {
            lore.add(main.color("&7Cost: &6" +trail.getPrice()));
            lore.add(main.color("&eClick To Purchase !"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }


    public ItemStack getPerkInSlot(UPlayer p,int slot){
        Perks perks = p.getPerkInSlot(slot);
        int id = perks.getId();
        if(id == 0)return emptySlot(p,slot);
        ItemStack item = perks.getPerkItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&e&lPerk Slot &a#"+slot));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(main.color("&7Selected: "+perks.getPerkName()));
        lore.add("  ");
        lore.addAll(perks.getLore());
        lore.add("      ");
        lore.add(main.color("&eClick to choose perk!"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;

    }


    public ItemStack BattleCry(){
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&e&lGo Back ⬅"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(main.color("&7Go back to the main menu"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack GoBack(){
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&e&lGo Back ⬅"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(main.color("&7Go back to the main menu"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack Clear(){
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&c&lRemove Selected Perk"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(main.color("&7This will remove selected"));
        lore.add(main.color("&7in the current slot"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    ItemStack emptySlot(UPlayer p,int slot){
        ItemStack item = new ItemStack(Material.BEDROCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&e&lPerk Slot &a#"+slot));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("  ");
        if(slot == 1){
            if(p.getLevel() >= 10){
                item = new ItemStack(Material.MINECART);
                lore.add(main.color("&a✔ Left-click to select a Perk"));
            }else {
                lore.add(main.color("&c✖ You need To be level &7[&810&7]"));
                lore.add(main.color("&cTo use this slot"));
            }
        }
        else if(slot == 2){
            if(p.getLevel() >= 30){
                item = new ItemStack(Material.MINECART);
                lore.add(main.color("&a✔ Left-click to select a Perk"));
            }else {
                lore.add(main.color("&c✖ You need To be level &7[&f30&7]"));
                lore.add(main.color("&cTo use this slot"));
            }
        }
        else if(slot == 3){
            if(p.getLevel() >= 50){
                item = new ItemStack(Material.MINECART);
                lore.add(main.color("&a✔ Left-click to select a Perk"));
            }else {
                lore.add(main.color("&c✖ You need To be level &7[&350&7]"));
                lore.add(main.color("&cTo use this slot"));
            }
        }else if(slot == 4){
            if(p.getPlayer().hasPermission("pvp.eagle")){
                item = new ItemStack(Material.MINECART);
                lore.add(main.color("&a✔ Left-click to select a Perk"));
            }else {
                lore.add(main.color("&c✖ You need To Have an &9Eagle &cRank"));
                lore.add(main.color("&cTo use this slot"));
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }


    public ItemStack ClearTrail() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&c&lRemove Selected Trail"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(main.color("&7This will remove selected"));
        lore.add(main.color("&7in the current slot"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
