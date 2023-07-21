package eaglemc.Quests;

import eaglemc.GameManager.GameManager;
import eaglemc.Managers.PlayerManager;
import eaglemc.Managers.QuestManager;
import eaglemc.Shop.Items;
import eaglemc.Utils.UPlayer;
import eaglemc.Utils.Utils;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

public class Quest implements Listener {
    private GameManager gm;

    public Quest(GameManager pm) {
        this.gm = pm;
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
            if(!Utils.Quest.contains(villager))return;
            event.setCancelled(true);
            Player p = event.getPlayer();
            UPlayer up = gm.getPlayerManager().getPlayer(p);
            QMenu.OpenInventory(p,up,gm.getQuestManager());
        }
    }

}
