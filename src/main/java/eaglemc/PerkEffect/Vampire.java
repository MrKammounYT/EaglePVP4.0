package eaglemc.PerkEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.Perks;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

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
                    p.setHealth(p.getHealth() + 0.6);
                }
            }
        }
        if(e.getDamager() instanceof  Player){
            if(pm.getPlayer((Player) e.getDamager()).getSlots().containsValue(Perks.Vampire)){
                ((Player) e.getDamager()).setHealth(((Player) e.getDamager()).getHealth() + 0.2);
            }
        }

    }

}
