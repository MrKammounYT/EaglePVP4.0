package eaglemc.PerkEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.enums.Perks;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class Grappler implements Listener {

    private final PlayerManager pm;
    private final Random random =new Random();


    public Grappler(PlayerManager pm) {
        this.pm = pm;
    }
    @EventHandler
    public void onGrapplerActivate(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Arrow){
            if(((Arrow) e.getDamager()).getShooter() instanceof Player){
                Player p = (Player) ((Arrow) e.getDamager()).getShooter();
                if(!pm.getPlayer(p).getSlots().containsValue(Perks.GRAPPLER))return;
                if(random.nextDouble() < 0.5){
                Vector direction = e.getEntity().getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                direction.setY(0);
                direction.multiply(8);
                p.setVelocity(direction);
                p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS,3.0f,2.0f);
                }
            }
        }

    }
}
