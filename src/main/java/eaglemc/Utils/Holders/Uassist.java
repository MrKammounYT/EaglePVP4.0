package eaglemc.Utils.Holders;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Uassist {



    private final HashMap<Player,Integer> damagers = new HashMap<>();

    private final HashMap<Player, Long> damagesLastHitTime = new HashMap<>();

    public int getDamagePercentage(Player player){
        return  (damagers.get(player)/20)*100;
    }
    public boolean ShouldGetAssist(Player damager){
        damager.sendMessage("assist: "+(System.currentTimeMillis() <=  damagesLastHitTime.get(damager)));
        return System.currentTimeMillis() <=  damagesLastHitTime.get(damager);
    }
    public void addDamagers(Player player,int damage){
        if(damage == 0)return;
        damagesLastHitTime.remove(player);
        damagesLastHitTime.put(player,System.currentTimeMillis() +(10 * 1000));

        if(damagers.containsKey(player)){
            damagers.put(player,damagers.get(player) + damage);
            return;
        }
        damagers.put(player,damage);
    }
    public HashMap<Player, Integer> getDamages() {
        return damagers;
    }

    public void reset() {
        damagers.clear();
        damagesLastHitTime.clear();
    }
}
