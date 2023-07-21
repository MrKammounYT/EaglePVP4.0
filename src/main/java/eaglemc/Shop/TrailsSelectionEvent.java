package eaglemc.Shop;

import eaglemc.Managers.PlayerManager;
import eaglemc.Perks;
import eaglemc.Trails;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TrailsSelectionEvent implements Listener {
    private final PlayerManager pm;

    public  TrailsSelectionEvent(PlayerManager pm){
        this.pm = pm;
    }

    @EventHandler
    public void onPerkMenuOpen(InventoryClickEvent e){
        if(e.getCurrentItem() ==null)return;
        if(e.getView().getTitle().equals("Arrow Trails")){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta())return;
            if(!e.getCurrentItem().getItemMeta().hasDisplayName())return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&c&lRemove Selected Trail"))){
                Player p = (Player) e.getWhoClicked();
                UPlayer up = pm.getPlayer(p);
                up.setSelectedTrail(Trails.none);
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                Menu.OpenMainShop(p,up);
                return;
            }
            if(Trails.getTrailID(main.color(e.getCurrentItem().getItemMeta().getDisplayName())) !=0){
                Trails trail = Trails.getFromID(Trails.getTrailID(e.getCurrentItem().getItemMeta().getDisplayName()));
                Player p = (Player) e.getWhoClicked();
                UPlayer up = pm.getPlayer(p);
                if(!up.getTrails().contains(trail)){
                    if(up.getCoins() < trail.getPrice()){
                        p.sendMessage(main.Prefix+main.color("&cYou don't have enough coins to Buy this Trail"));
                        return;
                    }
                    up.setTrailToPurchase(trail.getId());
                    Menu.OpenPurchaseMenuTrails(p,trail);
                    return;
                }
                if(up.getSelectedTrail().equals(trail)){
                    p.sendMessage(main.Prefix+main.color("&cThis Perk is already Selected"));
                    return;
                }
                up.setSelectedTrail(trail);
                p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                p.closeInventory();
                Menu.OpenMainShop(p,up);

            }
        }
    }

}
