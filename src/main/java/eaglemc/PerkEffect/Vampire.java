package eaglemc.PerkEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.enums.Perks;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Vampire implements Listener {

    private final PlayerManager pm;

    public Vampire(PlayerManager pm) {
        this.pm = pm;
    }
    @EventHandler
    public void onVampireActivate(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Arrow){
            if(((Arrow) e.getDamager()).getShooter() instanceof Player){
                Player p = (Player) ((Arrow) e.getDamager()).getShooter();
                if(pm.getPlayer(p).getSlots().containsValue(Perks.Vampire)){
                    if(p.getHealth() + 0.6 > 20.0)return;
                    p.setHealth(p.getHealth() + 0.6);
                }
            }
        }
        if(e.getDamager() instanceof  Player){
            Player p = (Player) e.getDamager();
            if(pm.getPlayer(p).getSlots().containsValue(Perks.Vampire)){
                if(p.getHealth() + 0.6 > 20.0)return;
                p.setHealth(p.getHealth() + 0.2);
            }
        }

    }

}
