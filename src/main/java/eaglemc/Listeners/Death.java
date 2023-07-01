package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Map;
import java.util.Random;

public class Death implements Listener {



    GameManager gm;
    Random rd = new Random();

    public Death(GameManager gm){
        this.gm = gm;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e){
        e.setDroppedExp(0);
        e.getDrops().clear();
        UPlayer up = gm.getPlayerManager().getPlayer(e.getEntity());
        Player p = e.getEntity();
        int exp = rd.nextInt(8);
        int coins = rd.nextInt(10);
        for(Player damagers : up.getUassist().getDamagers().keySet()){
            int newexp = (exp * up.getUassist().getDamagePercentage(damagers)) /100;
            int newcoins = (coins * up.getUassist().getDamagePercentage(damagers)) /100;
            damagers.sendMessage(main.Prefix + main.color("&b&lASSIST! &7on "+p.getDisplayName() + "&b+"+newexp+"XP" +" &6+"+newcoins+"✪"));
        }
        if(e.getEntity().getKiller() == null)return;
        Player k = e.getEntity().getKiller();
        k.setLevel(0);
        p.setLevel(0);
        UPlayer uk = gm.getPlayerManager().getPlayer(k);
        k.sendMessage(main.Prefix + main.color("&a&lKILL! &7on "+p.getDisplayName() + "&b+"+exp+"XP" +" &6+"+coins+"✪"));
        p.sendMessage(main.Prefix + main.color("&c&lDEATH! &7by "+k.getDisplayName()));
        up.addDeaths(1);
        int points = (up.getPoints()*3)/100;
        uk.addPoints(points);
        up.removePoints(points);
        uk.addexp(k,exp);
        uk.addKills(1);
        uk.addCoins(coins);


    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if(e.getEntity().getKiller() != null)return;
        Player p = e.getEntity();
        p.sendMessage(main.Prefix + main.color("&c&lDEATH!"));
        UPlayer up = gm.getPlayerManager().getPlayer(p);
        up.addDeaths(1);
    }
}
