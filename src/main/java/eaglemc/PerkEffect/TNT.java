package eaglemc.PerkEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.Perks;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TNT implements Listener {


    private PlayerManager pm;


    public TNT(PlayerManager pm){
        this.pm = pm;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlockPlaced() == null || e.getBlockPlaced().getType() != Material.TNT)
            return;
        if(!pm.getPlayer(e.getPlayer()).getSlots().containsValue(Perks.TNT))return;
        e.getBlockPlaced().setType(Material.AIR);
        Entity tnt = e.getPlayer().getWorld().spawn(e.getBlockPlaced().getLocation(), TNTPrimed.class);
        ((TNTPrimed)tnt).setFuseTicks(2 * 20);
    }
  @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        e.blockList().clear();
        e.setCancelled(true);
    }




}
