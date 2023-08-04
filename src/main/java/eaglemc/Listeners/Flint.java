package eaglemc.Listeners;

import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.Holders.UPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class Flint implements Listener {

    private final PlayerManager pm;
    public Flint(PlayerManager playerManager){
        this.pm = playerManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFlintUseEvent(BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.FIRE){
            UPlayer up = pm.getPlayer(e.getPlayer());
            if(up.getFlint() > 0){
                e.setCancelled(false);
                up.removeFlint(1,e.getPlayer());
                return;
            }
            e.setCancelled(true);
            e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.DOOR_CLOSE,2.0f,1.5f);
        }
    }
}
