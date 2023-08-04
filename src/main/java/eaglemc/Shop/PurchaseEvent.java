package eaglemc.Shop;

import eaglemc.enums.DeathCry;
import eaglemc.Managers.PlayerManager;
import eaglemc.enums.Perks;
import eaglemc.Shop.Menus.Menu;
import eaglemc.enums.Trails;
import eaglemc.Utils.Inventory.BuyInventory;
import eaglemc.Utils.ScoreBoard;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PurchaseEvent implements Listener {

    private final PlayerManager pm;

    public  PurchaseEvent(PlayerManager pm){
        this.pm = pm;
    }

    @EventHandler
    public void onPerkMenuOpen(InventoryClickEvent e){
        if(!(e.getInventory().getHolder() instanceof BuyInventory))return;
        if(e.getCurrentItem() ==null)return;
        if(!e.getCurrentItem().hasItemMeta())return;
            Object o = ((BuyInventory) e.getInventory().getHolder()).getObject();
            Trails trail =null;
            Perks perk = null;
            DeathCry deathCry = null;
            if(o instanceof Trails){
                trail = (Trails) o;
            }
            else if(o instanceof Perks){
                perk = (Perks) o;
            }
            else if(o instanceof DeathCry){
                deathCry = (DeathCry) o;
            }
            e.setCancelled(true);
            Player p = (Player)e.getWhoClicked();
            UPlayer up = pm.getPlayer(p);
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&aPurchase"))){
            if(perk !=null){
                    if(up.getCoins() < perk.getPrice()){
                        p.closeInventory();
                        Menu.OpenMainShop(p,up);
                        return;
                    }
                    up.removeCoins(perk.getPrice());
                    up.getPlayerPerks().add(perk);
                    p.sendMessage(main.Prefix + main.color("&aYou have bought "+perk.getPerkName() ));
                    p.playSound(p.getLocation(), Sound.LEVEL_UP,3.0f,5.0f);
                    Menu.OpenMainShop(p,up);
                    ScoreBoard.create(p,up);

            }
            else if(trail != null){
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
                    ScoreBoard.create(p,up);
            }
            else if(deathCry != null){
                if(up.getCoins() < deathCry.getPrice()){
                    p.closeInventory();
                    Menu.OpenMainShop(p,up);
                    return;
                }
                up.removeCoins(deathCry.getPrice());
                up.getDeathCries().add(deathCry);
                p.sendMessage(main.Prefix + main.color("&aYou have bought "+deathCry.getDisplayName()));
                p.playSound(p.getLocation(), Sound.LEVEL_UP,3.0f,5.0f);
                Menu.OpenMainShop(p,up);
                ScoreBoard.create(p,up);
            }
        }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&cCancel"))){
                p.closeInventory();
                Menu.OpenMainShop(p,up);
                p.playSound(p.getLocation(), Sound.VILLAGER_NO,3.0f,5.0f);
            }

    }
}
