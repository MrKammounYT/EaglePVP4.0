package eaglemc.Listeners;

import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

public class Shop implements Listener {


    @EventHandler
    public void onShopDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Villager){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onShopOpen(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            event.setCancelled(true);
            Villager villager = (Villager) event.getRightClicked();
            if (!villager.getCustomName().equals(main.color(main.getInstance().getConfig().getString("Shop-Npc-Name")))) {
                return;
            }

            Player p = event.getPlayer();
            Inventory inventory = Bukkit.createInventory(null, 27, "Shop Inv");
            p.openInventory(inventory);
        }
    }

}
