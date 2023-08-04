package eaglemc.Shop;

import eaglemc.enums.DeathCry;
import eaglemc.Managers.PlayerManager;
import eaglemc.enums.Perks;
import eaglemc.Shop.Menus.BuyMenu;
import eaglemc.Shop.Menus.Menu;
import eaglemc.enums.Trails;
import eaglemc.enums.Type;
import eaglemc.Utils.Inventory.SelectionInventory;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SelectionEvent implements Listener {

    private final PlayerManager pm;

    public  SelectionEvent(PlayerManager pm){
            this.pm = pm;
        }

        @EventHandler
        public void onPerkMenuOpen(InventoryClickEvent e){
            if(e.getCurrentItem() ==null)return;
            if(!(e.getInventory().getHolder() instanceof SelectionInventory))return;
            if(!(e.getWhoClicked() instanceof Player))return;
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta())return;
            if(!e.getCurrentItem().getItemMeta().hasDisplayName())return;
            Type invType = ((SelectionInventory) e.getInventory().getHolder()).getInvType();
            Player p = (Player) e.getWhoClicked();
            UPlayer up = pm.getPlayer(p);
                if(invType == Type.Perks){
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&c&lRemove Selected Perk"))){
                        up.getSlots().put(up.getSelectedSlot(), Perks.NO_PERK);
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                        Menu.OpenMainShop(p,up);
                        return;
                    }
                    if(Perks.getPerkID(main.color(e.getCurrentItem().getItemMeta().getDisplayName())) !=0){
                        int perkID = Perks.getPerkID(e.getCurrentItem().getItemMeta().getDisplayName());
                        Perks perk = Perks.fromId(perkID);
                        if(up.getSelectedSlot() == 0){
                            p.closeInventory();
                            return;
                        }
                        if(!up.getPlayerPerks().contains(perk)){
                            if(up.getCoins() < perk.getPrice()){
                                p.sendMessage(main.Prefix+main.color("&cYou don't have enough coins to Buy this Perk"));
                                return;
                            }
                            BuyMenu.OpenPurchaseMenu(p,perk);
                            return;
                        }
                        if(up.getSlots().containsValue(perk)){
                            p.sendMessage(main.Prefix+main.color("&cThis Perk is already Selected"));
                            return;
                        }
                        up.getSlots().put(up.getSelectedSlot(), Perks.fromId(perkID));
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                        p.closeInventory();
                        Menu.OpenMainShop(p,up);
                }
                }
                else if(invType == Type.Trails){
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&c&lRemove Selected Trail"))){
                        up.setSelectedTrail(Trails.none);
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                        Menu.OpenMainShop(p,up);
                        return;
                    }
                    if(Trails.getTrailID(main.color(e.getCurrentItem().getItemMeta().getDisplayName())) !=0){
                        Trails trail = Trails.getFromID(Trails.getTrailID(e.getCurrentItem().getItemMeta().getDisplayName()));
                        
                        if(!up.getTrails().contains(trail)){
                            if(up.getCoins() < trail.getPrice()){
                                p.sendMessage(main.Prefix+main.color("&cYou don't have enough coins to Buy this Trail"));
                                return;
                            }
                            BuyMenu.OpenPurchaseMenu(p,trail);
                            return;
                        }
                        if(up.getSelectedTrail().equals(trail)){
                            p.sendMessage(main.Prefix+main.color("&cThis Trail is already Selected"));
                            return;
                        }
                        up.setSelectedTrail(trail);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                        p.closeInventory();
                        Menu.OpenMainShop(p,up);

                    }
                }
                else if(invType == Type.DeathCry){
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&c&lRemove Selected DeathCry"))){
                        
                        up.setSelectedDeathCry(DeathCry.none);
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                        Menu.OpenMainShop(p,up);
                        return;
                    }
                    if(DeathCry.getDeathCryByDisplayName(e.getCurrentItem().getItemMeta().getDisplayName()) !=0){
                        DeathCry dc = DeathCry.getDeathCryByID(DeathCry.getDeathCryByDisplayName(e.getCurrentItem().getItemMeta().getDisplayName()));
                        if(!up.getDeathCries().contains(dc)){
                            if(up.getCoins() < dc.getPrice()){
                                p.sendMessage(main.Prefix+main.color("&cYou don't have enough coins to Buy this DeathCry"));
                                return;
                            }
                            BuyMenu.OpenPurchaseMenu(p,dc);
                            return;
                        }
                        if(up.getSelectedDeathCry().equals(dc)){
                            p.sendMessage(main.Prefix+main.color("&cThis DeathCry is already Selected"));
                            return;
                        }
                        up.setSelectedDeathCry(dc);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                        p.closeInventory();
                        Menu.OpenMainShop(p,up);

                    }
                }

        }

}
