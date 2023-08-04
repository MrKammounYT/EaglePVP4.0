package eaglemc.enums;

import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public enum DeathCry {

    none(0),CAT(1),ENDER_MAN(2),GHAST(3), Guardian(4),wolf(5);


    private  int id;
    private DeathCry(int id){
        this.id = id;
    }


    public static DeathCry getDeathCryByID(int id){
        for(DeathCry deathCry : DeathCry.values()){
            if(deathCry.id == id)return deathCry;
        }
        throw new IllegalArgumentException("Invalid Trail ID: " + id);
    }
    public ItemStack getItemStack(int id){
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(main.color(getDisplayName()));
        meta.setLore(getLore());

        return itemStack;

    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<String>();

        switch (id) {
            case 1:
                lore.add(main.color("&7Play Cat Sound when you Die"));
                break;
            case 2:
                lore.add(main.color("&7Play EnderMan Sound when you Die"));
                break;
            case 3:
                lore.add(main.color("&7Play Ghast Sound when you Die"));
                break;
            case 4:
                lore.add(main.color("&7Play Guardian Sound when you Die"));
                break;
            case 5:
                lore.add(main.color("&7Play Wolf Sound when you Die"));
                break;
            default:
                break;
        }

        return lore;
    }


    public void  playSound(Player p){
        switch (id) {
            case 1:
                p.playSound(p.getLocation(), Sound.CAT_MEOW, 2.0f, 1.3f);
                break;
            case 2:
                p.playSound(p.getLocation(), Sound.ENDERMAN_DEATH, 2.0f, 1.4f);
                break;
            case 3:
                p.playSound(p.getLocation(), Sound.GHAST_MOAN, 2.0f, 1.2f);
                break;
            case 4:
                p.playSound(p.getLocation(), "mob.guardian.curse", 2.0f, 1.0f);
                break;
            case 5:
                p.playSound(p.getLocation(), Sound.WOLF_HOWL, 2.0f, 1.2f);
                break;
            default:
                break;
        }

    }

    public static int getDeathCryByDisplayName(String perkName) {
        switch (perkName) {
            case "§3Meow":
                return 1;
            case "§5EnderMan":
                return 2;
            case "§fGhast":
                return 3;
            case "§eGuardian":
                return 4;
            case "§bWolf":
                return 5;
            default:
                return 0;
        }
    }

    public Material getMaterial() {
        switch (id) {
            case 1:
                return Material.RAW_FISH;
            case 2:
                return Material.ENDER_PEARL;
            case 3:
                return Material.GHAST_TEAR;
            case 4:
                return Material.PRISMARINE_SHARD;
            case 5:
                return Material.BONE;
            default:
                return Material.AIR;
        }
    }
    public String getDisplayName() {
        switch (id) {
            case 1:
                return main.color("&3Meow");
            case 2:
                return main.color("&5EnderMan");
            case 3:
                return main.color("&fGhast");
            case 4:
                return main.color("&eGuardian");
            case 5:
                return main.color("&bWolf");
            default:
                return main.color("&cUnknown Trail");
        }
    }
    public int getPrice() {
        switch (id) {
            case 1:
                return 1500;
            case 2:
                return 2000;
            case 3:
                return 2500;
            case 4:
                return 1800;
            case 5:
                return 1600;
            default:
                return 0;
        }
    }


}
