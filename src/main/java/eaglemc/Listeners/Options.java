package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import eaglemc.pvp.main;

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
    public void onInteract(PlayerInteractEvent e){
        if(e.getClickedBlock() ==null)return;
        if(e.getClickedBlock().getType() == Material.CHEST
        || e.getClickedBlock().getType() == Material.ENDER_CHEST
        || e.getClickedBlock().getType() == Material.FURNACE
        || e.getClickedBlock().getType() == Material.WORKBENCH
        || e.getClickedBlock().getType() == Material.DROPPER
        || e.getClickedBlock().getType() == Material.DISPENSER
        || e.getClickedBlock().getType() == Material.HOPPER
        || e.getClickedBlock().getType() == Material.TRAPPED_CHEST
        || e.getClickedBlock().getType() == Material.TRAP_DOOR
        || e.getClickedBlock().getType() == Material.WOOD_DOOR
        || e.getClickedBlock().getType() == Material.ITEM_FRAME){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerMoveInTeleport(PlayerMoveEvent e){
        if (e.getFrom().getZ() != e.getTo().getZ() && e.getFrom().getX() != e.getTo().getX()) {
            if (Utils.intp.containsKey(e.getPlayer().getUniqueId())) {
                e.getPlayer().sendMessage(
                        main.Prefix + main.color("&cYour teleport has been canceled because you moved!"));
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_HIT, 3.0f, 4.0f);
                Utils.intp.get(e.getPlayer().getUniqueId()).cancel();
                Utils.intp.remove(e.getPlayer().getUniqueId());
            }
        }
    }


    @EventHandler
    public void onTeleportPortal(PlayerPortalEvent e) {
        e.setCancelled(true);
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
