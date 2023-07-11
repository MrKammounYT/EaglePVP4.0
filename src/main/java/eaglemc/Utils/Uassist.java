package eaglemc.Utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Uassist {



    private int finaldamage;
    private final HashMap<Player,Double> damagers = new HashMap<>();


    public void AddfinalDamage(int finaldamage){
        this.finaldamage += finaldamage;
    }


    public int getDamagePercentage(Player player){
        double data = damagers.get(player);
        int value = (int)data;
        return  (value*100)/finaldamage;
    }

    public void addDamagers(Player player,double damage){
        if(damage == 0)return;
        if(damagers.containsKey(player) ){
            damagers.put(player,damagers.get(player) + damage);
            return;
        }
        damagers.put(player,damage);
    }

    public void clearDamagers(){
        damagers.clear();
    }

    public HashMap<Player, Double> getDamagers() {
        return damagers;
    }
}
