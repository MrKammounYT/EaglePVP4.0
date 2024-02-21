package eaglemc.Shop.Items;

import eaglemc.Utils.enums.DeathCry;
import eaglemc.Utils.enums.KillStreakEffect;
import eaglemc.Utils.enums.Perks;
import eaglemc.Utils.enums.Trails;
import eaglemc.Utils.others.GlowEnchantment;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Items {


    GlowEnchantment glowEnchantment = new GlowEnchantment(70);
    public ItemStack getShopPerk(UPlayer p,Perks perk){
        ItemStack item =perk.getPerkItem();
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(main.color("&7Selected: "+perk.getPerkName()));
        lore.add("  ");
        lore.addAll(perk.getLore());
        lore.add("      ");
        if(p.getSlots().containsValue(perk)){
            lore.add(main.color("&aAlready Selected"));
            
            meta.addEnchant(glowEnchantment,1,true);
        }
        else if(p.getPlayerPerks().contains(perk)){
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
    public ItemStack getDeathCry(DeathCry dc, UPlayer p) {
        ItemStack item = new ItemStack(dc.getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dc.getDisplayName());
        ArrayList<String> lore = dc.getLore();
        lore.add(" ");
        if(p.getSelectedDeathCry().equals(dc)){
            lore.add(main.color("&aAlready Selected"));
            
            meta.addEnchant(glowEnchantment,1,true);
        }
        else if(p.getDeathCries().contains(dc)){
            lore.add(main.color("&aClick To Select!"));
        }else {
            lore.add(main.color("&7Cost: &6" +dc.getPrice()));
            lore.add(main.color("&eClick To Purchase !"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getKillStreakEffects(KillStreakEffect killStreakEffect, UPlayer p) {
        ItemStack item = killStreakEffect.getKillstreakItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color(killStreakEffect.getDisplayName()));
        ArrayList<String> lore = killStreakEffect.getLore();
        lore.add(" ");
        if(p.getSelectedKillStreakEffect().equals(killStreakEffect)){
            lore.add(main.color("&aAlready Selected"));
            meta.addEnchant(glowEnchantment,1,true);
        }
        else if(p.getKillStreakEffects().contains(killStreakEffect)){
            lore.add(main.color("&aClick To Select!"));
        }else {
            lore.add(main.color("&7Cost: &6" +killStreakEffect.getPrice()));
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

    public ItemStack KillStreakEffect(){
        ItemStack item = new ItemStack(Material.REDSTONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&c&lKillStreak Effect"));
        ArrayList<String> lore = new ArrayList<>();
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
    public ItemStack BattleCry(){
        ItemStack item = new ItemStack(Material.NOTE_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&e&lDeath Cry"));
        ArrayList<String> lore = new ArrayList<>();
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
        lore.add(main.color("&7This will remove your "));
        lore.add(main.color("&7selected Trail"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack ClearDeathCry() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&c&lRemove Selected DeathCry"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(main.color("&7This will remove your "));
        lore.add(main.color("&7selected DeathCry "));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack ClearKillStreakEffect() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&c&lRemove Selected KillStreak effect"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(main.color("&7This will remove your "));
        lore.add(main.color("&7selected KillStreak effect "));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack SaveInventory() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&a&lSave Your Current Inventory &e(Coming soon..)"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(main.color("&7This will Save Your Current"));
        lore.add(main.color("&7Inventory Until you logout"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack Close() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&cClose"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(main.color("&7Right-Click To Close Shop Menu"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
