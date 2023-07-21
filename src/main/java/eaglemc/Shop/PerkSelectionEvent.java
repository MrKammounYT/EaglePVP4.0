package eaglemc.Shop;

import eaglemc.Managers.PlayerManager;
import eaglemc.Perks;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PerkSelectionEvent implements Listener {

    private final PlayerManager pm;

    public  PerkSelectionEvent(PlayerManager pm){
        this.pm = pm;
    }

    @EventHandler
    public void onPerkMenuOpen(InventoryClickEvent e){
        if(e.getCurrentItem() ==null)return;
        if(e.getView().getTitle().equals("Choose a Perk")){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta())return;
            if(!e.getCurrentItem().getItemMeta().hasDisplayName())return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&c&lRemove Selected Perk"))){
                Player p = (Player) e.getWhoClicked();
                UPlayer up = pm.getPlayer(p);
                up.getSlots().put(up.getSelectedSlot(), Perks.NO_PERK);
                p.closeInventory();
                p.playSound(p.getLocation(),Sound.ORB_PICKUP,3.0f,2.0f);
                Menu.OpenMainShop(p,up);
                return;
            }
            if(Perks.getPerkID(main.color(e.getCurrentItem().getItemMeta().getDisplayName())) !=0){
                int perkID = Perks.getPerkID(e.getCurrentItem().getItemMeta().getDisplayName());
                Player p = (Player) e.getWhoClicked();
                UPlayer up = pm.getPlayer(p);
                if(up.getSelectedSlot() == 0){
                    p.closeInventory();
                    return;
                }
                if(!up.getPlayerPerks().contains(perkID)){
                    if(up.getCoins() < Perks.fromId(perkID).getPrice()){
                        p.sendMessage(main.Prefix+main.color("&cYou don't have enough coins to Buy this Perk"));
                        return;
                    }
                    up.setPerkToPurchase(perkID);
                    Menu.OpenPurchaseMenuPerks(p,Perks.fromId(perkID));
                    return;
                }
                if(up.getSlots().containsValue(Perks.fromId(perkID))){
                    p.sendMessage(main.Prefix+main.color("&cThis Perk is already Selected"));
                    return;
                }
                up.getSlots().put(up.getSelectedSlot(), Perks.fromId(perkID));
                p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                p.closeInventory();
                Menu.OpenMainShop(p,up);

            }
        }
    }
}