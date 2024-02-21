package eaglemc.PerkEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.enums.Perks;
import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        if(e.getItemInHand().getAmount()<= 1){
        e.getPlayer().getInventory().remove(Perks.TNT.getPerkUsableItem());
        }else{
            e.getItemInHand().setAmount(e.getItemInHand().getAmount()-1);
        }
        e.getBlockPlaced().setType(Material.AIR);
        TNTPrimed tnt = e.getPlayer().getWorld().spawn(e.getBlockPlaced().getLocation(), TNTPrimed.class);
        tnt.setFuseTicks(2 * 20);
    }
  @EventHandler
    public void onBlockExplode(EntityExplodeEvent e) {
      if (e.getEntity() instanceof TNTPrimed) {
          e.blockList().clear();
      }

    }




}
