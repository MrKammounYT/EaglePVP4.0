package eaglemc.Utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Uassist {

    private int totalDamage;

    private final HashMap<Player,Double> damagers = new HashMap<>();


    public int getDamagePercentage(Player player){
        return (int) (damagers.get(player) /totalDamage);
    }

    public void addDamagers(Player player,double damage){
        if(damagers.containsKey(player) || damage == 0)return;
        damagers.put(player,damage);
    }

    public void clearDamagers(){
        damagers.clear();
    }

    public HashMap<Player, Double> getDamagers() {
        return damagers;
    }
}
