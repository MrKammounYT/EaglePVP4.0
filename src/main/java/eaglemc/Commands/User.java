package eaglemc.Commands;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.others.LocationAPI;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.Utils.Holders.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import eaglemc.pvp.main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;


public class User implements CommandExecutor {


    private GameManager gameManager;

    private final HashMap<UUID,Long> FixCooldown = new HashMap<UUID,Long>();

    public User(GameManager gm){
        this.gameManager = gm;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))return true;

        Player p= (Player)commandSender;

            if(command.getName().equalsIgnoreCase("stats")){
                if(strings.length == 1){
                    String t = strings[0];
                    if(Bukkit.getPlayer(t) == null){
                        p.sendMessage(main.Prefix + main.color("&cThis Player isn't Online"));
                        return true;
                    }
                    UPlayer uPlayer = gameManager.getPlayerManager().getPlayer(Bukkit.getPlayer(t));
                    p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP,3.0f,1.5f);
                    int deaths = uPlayer.getDeaths();
                    int kills = uPlayer.getKills();
                    double kdr = deaths != 0 ? (double) kills / deaths : kills;
                    p.sendMessage(main.color("&6====== &eStats &6====="));
                    p.sendMessage(main.color("&eName: " + uPlayer.getCustomName()));
                    p.sendMessage(main.color("&eKills: &7" + uPlayer.getKills()));
                    p.sendMessage(main.color("&eDeaths: &7" + uPlayer.getDeaths()));
                    p.sendMessage(main.color("&eK/D: &7" + kdr));
                    p.sendMessage(main.color("&ePoints: &7" + uPlayer.getPoints()));
                    p.sendMessage(main.color("&eCoins: &7" + uPlayer.getCoins()));
                    p.sendMessage(main.color("&eCurrent xp: &7" + uPlayer.getExp()));
                    p.sendMessage(main.color("&eLevel: &7" + uPlayer.getStringLevel()));
                    p.sendMessage(main.color("&6================"));

                    return true;
                }
                //your own stats
                p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP,3.0f,1.5f);
                UPlayer uPlayer = gameManager.getPlayerManager().getPlayer(p);
                int deaths = uPlayer.getDeaths();
                int kills = uPlayer.getKills();
                double kdr = deaths != 0 ? (double) kills / deaths : kills;
                p.sendMessage(main.color("&6====== &eStats &6====="));
                p.sendMessage(main.color("&eName: " + uPlayer.getCustomName()));
                p.sendMessage(main.color("&eKills: &7" + uPlayer.getKills()));
                p.sendMessage(main.color("&eDeaths: &7" + uPlayer.getDeaths()));
                p.sendMessage(main.color("&eK/D: &7" + kdr));
                p.sendMessage(main.color("&ePoints: &7" + uPlayer.getPoints()));
                p.sendMessage(main.color("&eCoins: &7" + uPlayer.getCoins()));
                p.sendMessage(main.color("&eCurrent xp: &7" + uPlayer.getExp()));
                p.sendMessage(main.color("&eLevel: &7" + uPlayer.getStringLevel()));
                p.sendMessage(main.color("&6================"));

            }
        else if(command.getName().equalsIgnoreCase("spawn")){
            if(Utils.intp.containsKey(p.getUniqueId())){
                p.sendMessage(main.Prefix + main.color("&cYou already in teleport"));
                return true;
            }
                p.sendMessage(main.Prefix +main.color("&aYou will be teleported to spawn in &c5 &aseconds."));

                BukkitTask run = new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(Utils.intp.containsKey(p.getUniqueId())){
                            Utils.intp.remove(p.getUniqueId());
                            try {
                                p.teleport(LocationAPI.getLocation("spawn"));
                            }catch (Exception e2){
                                Bukkit.getConsoleSender().sendMessage("§7[PvP] §cSpawn is not set");
                            }
                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 2.0f);
                            p.setHealth(20);


                        }

                    }
                }.runTaskLater(main.getInstance(),100);
                Utils.intp.put(p.getUniqueId(),run);



        }
        else if(command.getName().equalsIgnoreCase("fix")){
                if(FixCooldown.containsKey(p.getUniqueId())){
                    if(FixCooldown.get(p.getUniqueId()) > System.currentTimeMillis())return true;
                    FixCooldown.remove(p.getUniqueId());
                }
                FixCooldown.put(p.getUniqueId(),System.currentTimeMillis() + (5*1000));
                p.teleport(p.getLocation().clone().add(0,0.8,0));
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 0.2F,  2.0F + 0.9F);
                p.sendMessage(main.Prefix  + "§aThere you go!");
        }



        return true;
    }
}
