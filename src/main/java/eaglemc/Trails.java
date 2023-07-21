package eaglemc;

import eaglemc.pvp.main;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;

import java.util.ArrayList;

public enum Trails {

    HEARTS(1),SMOKE(2),MAGIC(3),LAVA(4),RAINBOW(5),none(0);



    private final int id;
     Trails(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static int getTrailID(String trailName) {
        if (trailName.equals(main.color("&cHearts"))) {
            return 1;
        } else if (trailName.equals(main.color("&7Smoke"))) {
            return 2;
        } else if (trailName.equals(main.color("&5Magic"))) {
            return 3;
        } else if (trailName.equals(main.color("&6Lava"))) {
            return 4;
        }else if (trailName.equals(main.color("&eR&aa&bi&cn&2b&5o&fw"))) {
            return 5;
        }
        else {
            return 0;
        }
    }

    public Material getMaterial() {
        switch (id) {
            case 1:
                return Material.RED_ROSE;
            case 2:
                return Material.TORCH;
            case 3:
                return Material.POTION;
            case 4:
                return Material.LAVA_BUCKET;
            case 5:
                return Material.REDSTONE;
            default:
                return Material.AIR;
        }
    }
    public String getDisplayName() {
        switch (id) {
            case 1:
                return main.color("&cHearts");
            case 2:
                return main.color("&7Smoke");
            case 3:
                return main.color("&5Magic");
            case 4:
                return main.color("&6Lava");
            case 5:
                return main.color("&eR&aa&bi&cn&2b&5o&fw");
            default:
                return main.color("&cUnknown Trail");
        }
    }
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();

        switch (id) {
            case 1:
                lore.add(main.color("&7Hearts will follow your arrows as you shoot."));
                lore.add(main.color("&7Spread love and leave a trail of hearts behind!"));
                break;
            case 2:
                lore.add(main.color("&7Emit a trail of smoke with each arrow."));
                lore.add(main.color("&7Leave a smoky trail behind as you shoot!"));
                break;
            case 3:
                lore.add(main.color("&7Unleash the power of magic with your arrows."));
                lore.add(main.color("&7Watch as mystical particles follow your shots!"));
                break;
            case 4:
                lore.add(main.color("&7Shoot arrows that leave a trail of flowing lava."));
                lore.add(main.color("&7Leave a fiery mark wherever your arrows fly!"));
                break;
            case 5:
                lore.add(main.color("&7Spread joy and color wherever you go!"));
                break;
            default:
                lore.add(main.color("&cUnknown Trail"));
                break;
        }

        return lore;
    }

    public EnumParticle getParticleEffect() {
        switch (id) {
            case 1:
                return EnumParticle.HEART;
            case 2:
                return EnumParticle.SMOKE_NORMAL;
            case 3:
                return EnumParticle.CRIT_MAGIC;
            case 4:
                return EnumParticle.DRIP_LAVA;
            case 5:
                return EnumParticle.SPELL_MOB;
            default:
                return null;
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
                return 2200;
            default:
                return 0;
        }
    }

    public static Trails getFromID(int id){
        for(Trails trails : Trails.values()){
            if(trails.getId() == id)return trails;
        }
        throw new IllegalArgumentException("Invalid Trail ID: " + id);
    }


}
