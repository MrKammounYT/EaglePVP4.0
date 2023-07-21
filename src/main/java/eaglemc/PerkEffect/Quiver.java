package eaglemc.PerkEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.Perks;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Quiver implements Listener {

    private PlayerManager pm;


    public Quiver(PlayerManager pm){
        this.pm = pm;
    }


    @EventHandler
    public void onQuiver(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Arrow){
            if(((Arrow) e.getDamager()).getShooter() instanceof  Player){
                Player p = (Player) ((Arrow) e.getDamager()).getShooter();
                if(pm.getPlayer(p).getSlots().containsValue(Perks.Endless_Quiver)){
                    p.getInventory().addItem(new ItemStack(Material.ARROW,3));
                }
            }
        }

    }


}
