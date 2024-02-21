package eaglemc.Shop;

import eaglemc.GameManager.GameManager;
import eaglemc.Managers.PlayerManager;
import eaglemc.Shop.Items.Items;
import eaglemc.Shop.Menus.Menu;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SectionSelectionEvent implements Listener {


    private final PlayerManager pm;
    private final GameManager gm;

    private final Items items;
    public SectionSelectionEvent(GameManager pm){
        this.gm = pm;
        this.pm = pm.getPlayerManager();
        this.items = new Items();
    }

    @EventHandler
    public void onPerkMenuOpen(InventoryClickEvent e){
        if(e.getCurrentItem() ==null)return;
        if(!e.getCurrentItem().hasItemMeta())return;
        if(!e.getCurrentItem().getItemMeta().hasDisplayName())return;
        Player p = (Player) e.getWhoClicked();
        UPlayer up =pm.getPlayer(p);
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&e&lGo Back ⬅"))){
            e.setCancelled(true);
            p.closeInventory();
            Menu.OpenMainShop(p,up);
            p.playSound(p.getLocation(),Sound.CLICK,3.0f,2.0f);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&a&lSave Your Current Inventory"))){
            e.setCancelled(true);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&cClose"))){
            e.setCancelled(true);
            p.closeInventory();
        }
        if(e.getView().getTitle().equals(main.color("&6&l⚔ PvP &e&l4.0 &e&lShop"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType() == Material.BEDROCK)return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().contains(main.color("&e&lPerk Slot &a#"))){
                int slot = Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName().replace(main.color("&e&lPerk Slot &a#"),""));
                up.setSelectedSlot(slot);
                p.playSound(p.getLocation(), Sound.CLICK,3.0f,2.0f);
                Menu.OpenPerksMenu(p,up,slot);

            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&c&lArrow Trails"))){
                p.playSound(p.getLocation(), Sound.CLICK,3.0f,2.0f);
                Menu.OpenTrailsMenu(p,up);

            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&3&lKits"))){
                p.playSound(p.getLocation(), Sound.CLICK,3.0f,2.0f);
                Menu.OpenKitMenu(p,gm);

            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&e&lDeath Cry"))){
                p.playSound(p.getLocation(), Sound.CLICK,3.0f,2.0f);
                Menu.openDeathCryMenu(p,up);

            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&c&lKillStreak Effect"))){
                p.playSound(p.getLocation(), Sound.CLICK,3.0f,2.0f);
                Menu.openKillStreakEffectsMenu(p,up);

            }
        }

    }
}
