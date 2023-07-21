package eaglemc.Shop;

import eaglemc.Managers.PlayerManager;
import eaglemc.Perks;
import eaglemc.Utils.ScoreBoard;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PerkPurchaseEvent implements Listener {

    private final PlayerManager pm;

    public  PerkPurchaseEvent(PlayerManager pm){
        this.pm = pm;
    }

    @EventHandler
    public void onPerkMenuOpen(InventoryClickEvent e){
        if(e.getCurrentItem() ==null)return;
        if(e.getView().getTitle().equals("Are you sure ?")){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta())return;
            Player p = (Player)e.getWhoClicked();
            UPlayer up = pm.getPlayer(p);
            Perks perk = Perks.fromId(up.getPerkToPurchase());
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&aPurchase"))){
                if(up.getCoins() < perk.getPrice()){
                    p.closeInventory();
                    Menu.OpenMainShop(p,up);
                    return;
                }
                up.removeCoins(perk.getPrice());
                up.getPlayerPerks().add(up.getPerkToPurchase());
                p.sendMessage(main.Prefix + main.color("&aYou have bought "+perk.getPerkName() ));
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
