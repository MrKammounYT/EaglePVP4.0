package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Assist implements Listener {


    private GameManager gm;
    public Assist(GameManager gm){
        this.gm = gm;
    }

    @EventHandler
    public void onAssist(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            if(e.getFinalDamage() <= 0)return;
            Player p = (Player)e.getEntity();
            Player damager = (Player)e.getDamager();
            gm.getPlayerManager().getPlayer(p).getUassist().addDamagers(damager,e.getFinalDamage());
            gm.getPlayerManager().getPlayer(p).getUassist().AddfinalDamage((int)e.getFinalDamage());
        }

    }



}
