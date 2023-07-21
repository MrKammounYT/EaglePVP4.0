package eaglemc.Shop;

import eaglemc.InvHolder.Htrail;
import eaglemc.Managers.PlayerManager;
import eaglemc.Perks;
import eaglemc.Trails;
import eaglemc.Utils.ScoreBoard;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TrailPurchaseEvent  implements Listener {

    private final PlayerManager pm;

    public  TrailPurchaseEvent(PlayerManager pm){
        this.pm = pm;
    }

    @EventHandler
    public void onPerkMenuOpen(InventoryClickEvent e){
        if(e.getCurrentItem() ==null)return;
        if(e.getView().getTitle().equals("Are you sure ?.")){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta())return;
            Player p = (Player)e.getWhoClicked();
            UPlayer up = pm.getPlayer(p);
            Trails trail  = Trails.getFromID(up.getTrailToPurchase());
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&aPurchase"))){
                if(up.getCoins() < trail.getPrice()){
                    p.closeInventory();
                    Menu.OpenMainShop(p,up);
                    return;
                }
                up.removeCoins(trail.getPrice());
                up.getTrails().add(trail);
                p.sendMessage(main.Prefix + main.color("&aYou have bought "+trail.getDisplayName()));
                p.playSound(p.getLocation(), Sound.LEVEL_UP,3.0f,5.0f);
                Menu.OpenMainShop(p,up);
                ScoreBoard.refresh(up);
            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&cCancel"))){
                p.closeInventory();
                Menu.OpenMainShop(p,up);
                p.playSound(p.getLocation(), Sound.VILLAGER_NO,3.0f,5.0f);
            }
        }
    }
}