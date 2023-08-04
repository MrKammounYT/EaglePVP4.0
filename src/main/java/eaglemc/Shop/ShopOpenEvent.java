package eaglemc.Shop;

import eaglemc.Managers.PlayerManager;
import eaglemc.Shop.Items.Items;
import eaglemc.Shop.Menus.Menu;
import eaglemc.Utils.Holders.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ShopOpenEvent implements Listener {



    private Items items;

    private PlayerManager pm;

    public ShopOpenEvent(PlayerManager pm) {
        this.items = new Items();
        this.pm = pm;
    }
    @EventHandler
    public void onShopDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Villager){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onShopOpen(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();
             if (Utils.shop.contains(villager)){
            event.setCancelled(true);
            Player p = event.getPlayer();
            p.playSound(p.getLocation(), Sound.CLICK,3.0f,2.0f);
            Menu.OpenMainShop(p,pm.getPlayer(p));
            }
        }
    }

}
