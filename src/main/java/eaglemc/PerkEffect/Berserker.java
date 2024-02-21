package eaglemc.PerkEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.enums.Perks;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Berserker implements Listener {
    private final PlayerManager pm;

    public Berserker(PlayerManager pm) {
        this.pm = pm;
    }
    @EventHandler
    public void onBerserker(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player p = (Player)e.getDamager();
            if(pm.getPlayer(p).getSlots().containsValue(Perks.BERSERKER)){
                if(p.getHealth() <=  3){
                    e.setDamage(e.getFinalDamage() * 2);
                    p.playSound(p.getLocation(), Sound.BLAZE_DEATH,3.0f,2.0f);

                }
            }
        }


    }
}
