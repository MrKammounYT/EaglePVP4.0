package eaglemc.Shop;

import eaglemc.GameManager.GameManager;
import eaglemc.Managers.KitManager;
import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.Kit;
import eaglemc.Utils.ScoreBoard;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.UUID;

public class KitSelectionEvent implements Listener {

    private final GameManager gm;
    private final  ArrayList<String> previewsName = new ArrayList<String>();

    public  KitSelectionEvent(GameManager gm){
        this.gm = gm;
        KitManager kitManager = gm.getKitManager();
        for(Kit kits : kitManager.getKits().values()){
            previewsName.add(main.color(kits.getDisplayName()));
        }
    }

    @EventHandler
    public void onPerkMenuOpen(InventoryClickEvent e){
        if(e.getCurrentItem() ==null)return;
        if(previewsName.contains(main.color(e.getView().getTitle()))){
            e.setCancelled(true);
        }
        if(!e.getCurrentItem().hasItemMeta())return;
        if(!e.getCurrentItem().getItemMeta().hasDisplayName())return;
        if(!e.getCurrentItem().getItemMeta().hasDisplayName())return;
        Player p = (Player) e.getWhoClicked();
        UPlayer up = gm.getPlayerManager().getPlayer(p);
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color("&e&lGo Back ⬅"))){
            Menu.OpenMainShop(p,up);
            p.playSound(p.getLocation(),Sound.CLICK,3.0f,2.0f);
        }
        KitManager kitManager = gm.getKitManager();
        if(e.getView().getTitle().equals("Kits")){
            e.setCancelled(true);
            for(Kit kits : kitManager.getKits().values()){
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(main.color(kits.getDisplayName()))){
                    if(e.getClick().isRightClick()){
                        Inventory inv = Bukkit.createInventory(null,27,main.color(kits.getDisplayName()));
                        for(int i=0;i<kits.getItems().size();i++){
                            inv.setItem(i,kits.getItems().get(i));
                        }
                        inv.setItem(18,new Items().GoBack());
                        p.closeInventory();
                        p.openInventory(inv);
                        return;
                    }
                    if(!p.hasPermission(kits.getPermission()) && (!kits.getPermission().equals("noperm"))){
                        if(!kits.isBuyable())return;
                        if(up.getCoins() < kits.getPrice()){
                            p.sendMessage(main.Prefix+main.color("&cYou don't have enough coins to Buy this kit"));
                            return;
                        }
                        up.removeCoins(kits.getPrice());
                        addPermission(p.getUniqueId(),kits.getPermission());
                        p.sendMessage(main.Prefix + main.color("&aYou have bought "+kits.getDisplayName()));
                        p.playSound(p.getLocation(), Sound.LEVEL_UP,3.0f,5.0f);
                        Menu.OpenMainShop(p,up);
                        ScoreBoard.refresh(up);

                        return;
                    }
                    if(up.getKit().equals(kits)){
                        p.sendMessage(main.Prefix+main.color("&cThis Kit is already Selected"));
                        return;
                    }
                    up.setKit(kits);
                    up.getKit().wear(p);
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP,3.0f,2.0f);
                    Menu.OpenMainShop(p,up);
                }
            }
        }
    }
    void addPermission(UUID uuid, String permission) {
        LuckPerms api = LuckPermsProvider.get();
        User user = api.getUserManager().getUser(uuid);
        user.data().add(Node.builder(permission).build());
        api.getUserManager().saveUser(user);
    }

}
