package eaglemc.enums;

import java.util.ArrayList;
import java.util.List;

import eaglemc.Utils.others.Heads;
import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Perks {

    NO_PERK(0),
    TNT(1),
    GOLDEN_HEAD(2),
    STRENGTH(3),
    Vampire(4),
    GRAPPLER(5),
    BERSERKER(6),
    Endless_Quiver(7),
    DOUBLE_XP(8);

    private final int id;

    Perks(int idp) {
       id = idp;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<String>();

        if (id == 1) {
            lore.add(main.color("&7Give you a TnT That you can use to blow"));
            lore.add(main.color("&7up your enemies"));
            lore.add(main.color("  "));
            lore.add(main.color("&eNote: &7You get This Item Every 5 Kills"));
        } else if (id == 2) {
            lore.add(main.color("&7This perk will give you a Golden Head which can "));
            lore.add(main.color("&7help you regenerate your health when consumed."));
        } else if (id == 3) {
            lore.add(main.color("&7Gives you &cStrength I &7for &c5 &7seconds"));
            lore.add(main.color("&7which can help you in serious situation."));
            lore.add(main.color("  "));
            lore.add(main.color("&eNote: &7You get This Item Every 5 Kills"));
        } else if (id == 4) {
            lore.add(main.color("&7Heal &c0.2❤ &7on hit. Tripled on arrow crit."));
            lore.add(main.color("&cRegen I (8s) &7on kill."));
        } else if (id == 5) {
            lore.add(main.color("&750% to Pull yourself towards the target"));
            lore.add(main.color("&7when you hit them with a projectile."));
        } else if (id == 6) {
            lore.add(main.color("&7Deal increased damage when your"));
            lore.add(main.color("&7health is below &c2.0❤"));
        } else if (id == 7) {
            lore.add(main.color("&730% chance to get 2 arrows on arrow hit."));
        } else if (id == 8) {
            lore.add(main.color("&7Double the &bXP &7earned from kills and assists"));
        }

        return lore;
    }


    public static int getPerkID(String perkName) {
        if (perkName.equals(main.color("&cTNT Perk"))) {
            return 1;
        } else if (perkName.equals(main.color("&6Golden Head Perk"))) {
            return 2;
        } else if (perkName.equals(main.color("&4Strength Perk"))) {
            return 3;
        } else if (perkName.equals(main.color("&cVampire Perk"))) {
            return 4;
        } else if (perkName.equals(main.color("&aGrappler Perk"))) {
            return 5;
        } else if (perkName.equals(main.color("&2Berserker Perk"))) {
            return 6;
        } else if (perkName.equals(main.color("&eEndless Quiver Perk"))) {
            return 7;
        } else if (perkName.equals(main.color("&bDouble XP Perk"))) {
            return 8;
        } else {
            return 0;
        }
    }


    public ItemStack getPerkUsableItem(){
        ItemStack item = new ItemStack(getPerkMaterial());
        if(id == 2){
            item = Heads.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDIxY2FiNDA5NWU3MWJkOTI1Y2Y0NjQ5OTBlMThlNDNhZGI3MjVkYjdjYzE3NWZkOWQxZGVjODIwOTE0YjNkZSJ9fX0=");
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getPerkName().replace(main.color("Perk"),""));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getPerkItem(){
        ArrayList<String> perkLore = getLore();
        ItemStack item = new ItemStack( getPerkMaterial());
        if(id == 2){
            item = Heads.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDIxY2FiNDA5NWU3MWJkOTI1Y2Y0NjQ5OTBlMThlNDNhZGI3MjVkYjdjYzE3NWZkOWQxZGVjODIwOTE0YjNkZSJ9fX0=");
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getPerkName());
        List<String> lore = new ArrayList<>();
        lore.add(" ");
        for (String line : perkLore) {
            lore.add(main.color("&7" + line));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public Material getPerkMaterial() {
        switch (this) {
            case TNT:
                return Material.TNT;
            case GOLDEN_HEAD:
                return Material.GOLDEN_APPLE;
            case STRENGTH:
                return Material.REDSTONE;
            case Vampire:
                return Material.REDSTONE_ORE;
            case GRAPPLER:
                return Material.CARROT_STICK;
            case BERSERKER:
                return Material.IRON_AXE;
            case Endless_Quiver:
                return Material.BOW;
            case DOUBLE_XP:
                return Material.EXP_BOTTLE;
            default:
                return Material.BARRIER;
        }
    }

    public int getPrice(){
        if (id == 1) {
            return 1500;
        } else if (id == 2) {
            return 2000;
        } else if (id == 3) {
            return 2250;
        } else if (id == 4) {
            return 3000;
        } else if (id == 5) {
            return 1500;
        } else if (id == 6) {
            return 2500;
        } else if (id == 7) {
            return 1800;
        } else if (id == 8) {
            return 3000;
        } else {
            return 0;
        }
    }
    public String getPerkName() {
        if (id == 1) {
            return  main.color("&cTNT Perk");
        } else if (id == 2) {
            return main.color("&6Golden Head Perk");
        } else if (id == 3) {
            return main.color("&4Strength Perk");
        } else if (id == 4) {
            return main.color("&cVampire Perk");
        } else if (id == 5) {
            return  main.color("&aGrappler Perk");
        } else if (id == 6) {
            return main.color("&2Berserker Perk");
        } else if (id == 7) {
            return  main.color("&eEndless Quiver Perk");
        } else if (id == 8) {
            return  main.color("&bDouble XP Perk");
        } else {
            return "Unknown Perk";
        }
    }
    public static   Perks fromId(int id) {
        for (Perks perk : Perks.values()) {
            if (perk.getId() == id) {
                return perk;
            }
        }
        throw new IllegalArgumentException("Invalid perk ID: " + id);
    }


}
