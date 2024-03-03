package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.enums.Perks;
import eaglemc.Utils.ScoreBoard;
import eaglemc.Utils.Title;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

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
        killStreaks.remove(e.getEntity().getUniqueId());
        if(e.getEntity().getKiller() != null) {
        UPlayer up = gm.getPlayerManager().getPlayer(e.getEntity());
        Player p = e.getEntity();
        int exp = getRandomInt(2,10);
        int coins = getRandomInt(10,50);
        Player k = e.getEntity().getKiller();
        k.setLevel(0);
        p.setLevel(0);
        UPlayer uk = gm.getPlayerManager().getPlayer(k);
        if(uk.getSlots().containsValue(Perks.DOUBLE_XP)){
            exp *=2;
        }
        DecimalFormat df = new DecimalFormat("##.##");
        String health = df.format(k.getHealth() / 2.0D);
        Title.sendActionBar(k,main.color("&a&lKilled "+up.getColoredName(p)+" with "+health + " &c❤ left"));
        Title.sendActionBar(p,main.color("&c&lKilled By "+uk.getColoredName(k)+" with "+health + " &c❤ left"));
        k.setHealth(20);
        k.getInventory().addItem(new ItemStack(Material.ARROW,2));
        up.setDeathLocation(k.getLocation());
        p.sendMessage(main.Prefix + main.color("&c&lDEATH! &7by "+uk.getColoredName(k)));
        int points = (up.getPoints()*3)/100;
        if(points <= 0){
            points = getRandomInt(2,10);
        }
        if(up.getKillStreaks() != 0 && up.getKillStreaks() % 5 == 0){
            coins = coins + ((coins * 20)/100);
            exp = exp + ((exp * 20) /100);
            k.sendMessage(main.Prefix + main.color("&a+20% &eCoins and Exp For Killing &r"+up.getColoredName(p)+ " &eWith &c"+up.getKillStreaks() + " &eKillStreaks"));
        }
        k.sendMessage(main.Prefix + main.color("&a&lKILL! &7on "+up.getColoredName(p)+ " &b+"+exp+"XP" +" &6+"+coins+"✪"));
        uk.addKillStreak();
        uk.addPoints(points);
        uk.addFlint(k);
        uk.addKills(1);
        uk.addexp(k,exp);
        uk.addCoins(coins);
        givePerk(k,uk);
        if(up.getPoints() - points >= 0){
          up.removePoints(points);
        }
        up.addDeaths(1);
        up.clearKillStreaks();
        playDeathCry(up,k);
        if(!p.getDisplayName().contains(".")){
            p.setGameMode(GameMode.SPECTATOR);
        }
            for(Player damagers : up.getUassist().getDamages().keySet()){
                if(damagers.getName().equals(k.getName()))continue;
                if(!up.getUassist().ShouldGetAssist(damagers))continue;
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
                damagers.sendMessage(main.Prefix + main.color("&b&lASSIST! &7on "+up.getColoredName(p)+ " &b+"+newexp+"XP" +" &6+"+newcoins+"✪"));
                if(damagers.getHealth() + ((damagers.getMaxHealth()*percentage)/100) >= 20){
                    damagers.setHealth(20);
                }else {
                    damagers.setHealth(damagers.getHealth() + ((damagers.getMaxHealth()*percentage)/100));
                }
                udamager.addexp(damagers,newexp);
                udamager.addPoints(newpoints);
                udamager.addCoins(newcoins);
            }
            up.getUassist().reset();
            ScoreBoard.create(p,up);
            ScoreBoard.create(k,uk);
        }

    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        e.setDroppedExp(0);
        e.getDrops().clear();
        UPlayer up = gm.getPlayerManager().getPlayer(e.getEntity());
        Player p = e.getEntity();
        up.getUassist().reset();
        if(e.getEntity().getKiller() != null)return;
        up.clearKillStreaks();
        p.sendMessage(main.Prefix + main.color("&c&lDEATH!"));
        up.addDeaths(1);
        up.setDeathLocation(p.getLocation());
        ScoreBoard.create(p,up);

    }

    int getRandomInt(int b1,int b2){
        int x = rd.nextInt(b2);
        while (b1>x){
            x = rd.nextInt(b2);
        }
        return x;
    }


    void playDeathCry(UPlayer up,Player k){
        up.getSelectedDeathCry().playSound(k);
    }

    void givePerk(Player p,UPlayer up){
        p.updateInventory();
        if(up.getKillStreaks() %5 ==0) {
            Bukkit.broadcastMessage(main.Prefix + main.color(up.getColoredName(p)+ " &eIs on &c"+up.getKillStreaks() + " &eKillStreaks"));
            PlayKillStreakEffect(p,up);
            if(up.getSlots().containsValue(Perks.TNT)){
                p.getInventory().addItem(Perks.TNT.getPerkUsableItem());
            }
            else if(up.getSlots().containsValue(Perks.STRENGTH)){
                p.getInventory().addItem(Perks.STRENGTH.getPerkUsableItem());
            }
            else if(up.getSlots().containsValue(Perks.Vampire)){
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,160,0));
            }
        }
        if(up.getSlots().containsValue(Perks.GOLDEN_HEAD)){
            if(up.HasGoldenHead(p))return;
            p.getInventory().addItem(Perks.GOLDEN_HEAD.getPerkUsableItem());
        }

    }

    private final ArrayList<UUID> killStreaks = new ArrayList<>();

    @EventHandler
    public void LeaveStreak(PlayerQuitEvent e){
        killStreaks.remove(e.getPlayer().getUniqueId());
    }

    private void PlayKillStreakEffect(Player p ,UPlayer up){
        if(up.getSelectedKillStreakEffect() == null || up.getSelectedKillStreakEffect().getKillstreakItem().getType() == Material.AIR) return;
        if(killStreaks.contains(p.getUniqueId()))return;
        if(up.getKillStreaks() %5  != 0)return;
        killStreaks.add(p.getUniqueId());
            new BukkitRunnable(){
                @Override
                public void run() {
                    if((!killStreaks.contains(p.getUniqueId())) || up.getSelectedKillStreakEffect() == null){
                        cancel();
                    }
                    Item item = p.getWorld().dropItemNaturally(p.getLocation().add(0,2,0),up.getSelectedKillStreakEffect().getKillstreakItem());
                    item.setPickupDelay(-1);
                    Item item2 = p.getWorld().dropItemNaturally(p.getLocation().add(0,2,0),up.getSelectedKillStreakEffect().getKillstreakItem());
                    item2.setPickupDelay(-1);

                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            item.remove();
                            item2.remove();
                        }
                    }.runTaskLater(main.getInstance(),18);
                }
            }.runTaskTimer(main.getInstance(),0,20);

    }



}
