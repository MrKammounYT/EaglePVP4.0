package eaglemc.Shop;

import eaglemc.GameManager.GameManager;
import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenusOpenEvent implements Listener {


    private final PlayerManager pm;
    private final GameManager gm;

    private final Items items;
    public MenusOpenEvent(GameManager pm){
        this.gm = pm;
        this.pm = pm.getPlayerManager();
        this.items = new Items();
    }

    @EventHandler
    public void onPerkMenuOpen(InventoryClickEvent e){
        if(e.getCurrentItem() ==null)return;
        if(e.getView().getTitle().equals(main.color("&6&l⚔ PvP &e&l4.0 &e&lShop"))){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta())return;
            if(!e.getCurrentItem().getItemMeta().hasDisplayName())return;
            if(e.getCurrentItem().getType() == Material.BEDROCK)return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().contains(main.color("&e&lPerk Slot &a#"))){
                Player p = (Player) e.getWhoClicked();
                UPlayer up =pm.getPlayer(p);
                int slot = Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName().replace(main.color("&e&lPerk Slot &a#"),""));
                up.setSelectedSlot(slot);
                p.playSound(p.getLocation(), Sound.CLICK,3.0f,2.0f);
                Menu.OpenPerksMenu(p,up,slot);

            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&c&lArrow Trails"))){
                Player p = (Player) e.getWhoClicked();
                UPlayer up =pm.getPlayer(p);
                p.playSound(p.getLocation(), Sound.CLICK,3.0f,2.0f);
                Menu.OpenTrailsMenu(p,up);

            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&3&lKits"))){
                Player p = (Player) e.getWhoClicked();
                p.playSound(p.getLocation(), Sound.CLICK,3.0f,2.0f);
                Menu.OpenKitMenu(p,gm);

            }
        }
        if(e.getView().getTitle().equals("Choose a Perk")){
            e.setCancelled(true);
        }
        if(e.getView().getTitle().equals("Arrow Trails")){
            e.setCancelled(true);
        }
    }
}
