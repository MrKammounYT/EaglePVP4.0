package eaglemc.Listeners;

import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Flint implements Listener {

    private final PlayerManager pm;
    public Flint(PlayerManager playerManager){
        this.pm = playerManager;
    }

    @EventHandler
    public void onFlintUseEvent(BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.FIRE){
            if(e.isCancelled())return;
           e.setCancelled(false);
           removeFlint(e.getBlock());
        }
    }
    @EventHandler
    public void onFlintUseEvent(PlayerInteractEvent e){
        if(e.getItem() == null)return;
        if(e.getItem().getType() == Material.FLINT_AND_STEEL){
            UPlayer up = pm.getPlayer(e.getPlayer());
            if(!up.canUseFlint()){
                e.setCancelled(true);
                e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.DOOR_CLOSE,2.0f,1.5f);
                return;
            }
            e.setCancelled(false);
            up.removeFlint(e.getPlayer());
        }
    }

    private void removeFlint(Block block){
        new BukkitRunnable(){

            @Override
            public void run() {
                block.setType(Material.AIR);
            }
        }.runTaskLater(main.getInstance(),10*20);
    }
}
