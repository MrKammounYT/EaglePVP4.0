package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Managers.PlayerManager;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Options implements Listener {

    private final PlayerManager pm;


    public Options(PlayerManager pm) {
        this.pm = pm;
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickBlock(PlayerPickupItemEvent e){
        if(pm.getPlayer(e.getPlayer()).isInBuild())return;
        e.setCancelled(true);
    }

    @EventHandler
    public void OnRemovingArmorStand(PlayerInteractAtEntityEvent e){
        if(pm.getPlayer(e.getPlayer()).isInBuild())return;
        if(e.getRightClicked() instanceof ArmorStand){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OnDestroyingArmorStand(EntityDamageEvent e){
        if (e.getEntity() instanceof  ArmorStand){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void Drop(PlayerDropItemEvent e){
        if(pm.getPlayer(e.getPlayer()).isInBuild())return;
        e.setCancelled(true);
    }

    @EventHandler
    public void FallDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(pm.getPlayer(e.getPlayer()).isInBuild())return;
        e.setCancelled(true);
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(pm.getPlayer(e.getPlayer()).isInBuild())return;
        e.setCancelled(true);
    }
}
