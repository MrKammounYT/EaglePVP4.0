package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Flint implements Listener {

    private final GameManager gm;
    public Flint(GameManager gameManager){
        this.gm = gameManager;
    }
    
    @EventHandler
    public void OnBlockIgnite(BlockIgniteEvent e){
        if(e.getBlock() == null)return;
        if(e.getBlock().getType() == Material.STAINED_GLASS || e.getBlock().getType() == Material.GLASS)return;
        if(e.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)return;
        UPlayer up = gm.getPlayerManager().getPlayer(e.getPlayer());
        if(up ==null)return;
        if(up.canUseFlint()){
            e.setCancelled(false);
            up.removeFlint(e.getPlayer());
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
                if(block==null)return;
                block.setType(Material.AIR);
            }
        }.runTaskLater(main.getInstance(),10*20);
    }
}
