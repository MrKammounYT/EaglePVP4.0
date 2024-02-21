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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Flint implements Listener {

    private final PlayerManager pm;
    public Flint(PlayerManager playerManager){
        this.pm = playerManager;
    }
    
    @EventHandler
    public void OnBlockIgnite(BlockIgniteEvent e){
        if(e.getBlock() == null)return;
        if(pm.getPlayer(e.getPlayer()).canUseFlint()){
            e.setCancelled(false);
            pm.getPlayer(e.getPlayer()).removeFlint(e.getPlayer());
            removeFlint(e.getBlock());
        }else {
            e.setCancelled(true);
            e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.DOOR_CLOSE,2.0f,1.5f);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFlintPlace(BlockPlaceEvent e){
        if(e.getBlock()==null)return;
        if(e.getBlock().getType() == Material.FIRE){
            if(e.getPlayer().getLocation().getY() >= 45)return;
            e.setCancelled(false);
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
