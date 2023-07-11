package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.Title;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
        e.setDeathMessage(null);
        e.getDrops().clear();
        e.setDroppedExp(0);
        if(e.getEntity().getKiller() != null) {
        UPlayer up = gm.getPlayerManager().getPlayer(e.getEntity());
        Player p = e.getEntity();
        int exp = getRandomInt(2,10);
        int coins = getRandomInt(2,10);
        Player k = e.getEntity().getKiller();
        k.setLevel(0);
        p.setLevel(0);
        UPlayer uk = gm.getPlayerManager().getPlayer(k);
        uk.addKillStreak();
        k.sendMessage(main.Prefix + main.color("&a&lKILL! &7on "+p.getDisplayName() + " &b+"+exp+"XP" +" &6+"+coins+"✪"));
        p.sendMessage(main.Prefix + main.color("&c&lDEATH! &7by "+k.getDisplayName()));
        up.addDeaths(1);
        int points = (up.getPoints()*3)/100;
        if(points == 0){
            points = getRandomInt(2,10);
        }
        uk.addPoints(points);
        up.removePoints(points);
        uk.addexp(k,exp);
        uk.addKills(1);
        uk.addCoins(coins);
            for(Player damagers : up.getUassist().getDamagers().keySet()){
                if(damagers.getName().equals(k.getName()))continue;
                int percentage =  up.getUassist().getDamagePercentage(damagers);
                UPlayer udamager = gm.getPlayerManager().getPlayer(damagers);
                int newexp = (exp *percentage) /100;
                int newcoins = (coins * percentage) /100;
                int newpoints = (points * percentage)/100;
                Title.sendActionBar(damagers,main.color("&b&lASSIST &a"+percentage +"% "+"&e+"+newpoints+"✮" +" &6+"+newcoins+"✪" ));
                damagers.playSound(damagers.getLocation(), Sound.NOTE_BASS_GUITAR,2.0f,3.0f);
                damagers.sendMessage(main.Prefix + main.color("&b&lASSIST! &7on "+p.getDisplayName() + " &b+"+newexp+"XP" +" &6+"+newcoins+"✪"));
                udamager.addexp(damagers,newexp);
                udamager.addPoints(newpoints);
                udamager.addCoins(newcoins);
            }

        }

    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        e.setDroppedExp(0);
        e.getDrops().clear();
        UPlayer up = gm.getPlayerManager().getPlayer(e.getEntity());
        Player p = e.getEntity();
        up.getUassist().clearDamagers();
        up.clearKillStreaks();
        if(e.getEntity().getKiller() != null)return;
        p.sendMessage(main.Prefix + main.color("&c&lDEATH!"));
        up.addDeaths(1);
    }

    int getRandomInt(int b1,int b2){
        int x = rd.nextInt(b2);
        while (b1>x){
            x = rd.nextInt(b2);
        }
        return x;
    }
}
