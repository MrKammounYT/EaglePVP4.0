package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Perks;
import eaglemc.Utils.Title;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
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
        if(uk.getSlots().containsValue(Perks.DOUBLE_XP)){
            exp *=2;
        }
        uk.addKillStreak();
        DecimalFormat df = new DecimalFormat("##.##");
        String health = df.format(k.getHealth() / 2.0D);
        Title.sendActionBar(k,main.color("&a&lKilled "+p.getDisplayName()+" with "+health + " &c❤ left"));
        Title.sendActionBar(p,main.color("&c&lKilled By "+k.getDisplayName()+" with "+health + " &c❤ left"));
        k.sendMessage(main.Prefix + main.color("&a&lKILL! &7on "+p.getDisplayName() + " &b+"+exp+"XP" +" &6+"+coins+"✪"));
        k.setHealth(20);
        p.sendMessage(main.Prefix + main.color("&c&lDEATH! &7by "+k.getDisplayName()));
        up.setDeathLocation(k.getLocation());
        up.addDeaths(1);
        givePerk(k,uk);
        int points = (up.getPoints()*3)/100;
        if(points <= 0){
            points = getRandomInt(2,10);
        }
        uk.addPoints(points);
        uk.addflint(1);
        if(up.getPoints() - points >= 0){
            up.removePoints(points);
        }
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
                if(udamager.getSlots().containsValue(Perks.DOUBLE_XP)){
                    newexp *=2;
                }
                Title.sendActionBar(damagers,main.color("&b&lASSIST &a"+percentage +"% "+"&e+"+newpoints+"✮" +" &6+"+newcoins+"✪" ));
                damagers.playSound(damagers.getLocation(), Sound.NOTE_BASS_GUITAR,2.0f,3.0f);
                damagers.sendMessage(main.Prefix + main.color("&b&lASSIST! &7on "+p.getDisplayName() + " &b+"+newexp+"XP" +" &6+"+newcoins+"✪"));
                damagers.setHealth(damagers.getHealth() + ((damagers.getMaxHealth()*percentage)/100));
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

    void givePerk(Player p,UPlayer up){
        if(up.getSlots().containsValue(Perks.GOLDEN_HEAD)){
            if(up.HasGoldenHead())return;
            p.getInventory().addItem(Perks.GOLDEN_HEAD.getPerkUsableItem());
            up.setGoldenHead(true);
        }
        else if(up.getSlots().containsValue(Perks.TNT)){
            p.getInventory().addItem(Perks.TNT.getPerkUsableItem());
        }
        else if(up.getSlots().containsValue(Perks.STRENGTH)){
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,100,1));
        }
        else if(up.getSlots().containsValue(Perks.Vampire)){
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,160,1));
        }
    }
}
