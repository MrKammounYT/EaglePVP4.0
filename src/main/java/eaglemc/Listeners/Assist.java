package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.Holders.UPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.sql.Time;

public class Assist implements Listener {

    private GameManager gm;
    public Assist(GameManager gm){
        this.gm = gm;
    }

    @EventHandler
    public void onAssist(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            if(e.getFinalDamage() <= 0)return;
            UPlayer up = gm.getPlayerManager().getPlayer((Player)e.getEntity());
            int damage = (int)e.getFinalDamage();
            up.getUassist().addDamagers((Player)e.getDamager(),damage);

        }

    }



}
