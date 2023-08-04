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


public class User implements CommandExecutor {


    private GameManager gameManager;


    public User(GameManager gm){
        this.gameManager = gm;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))return true;

        Player p= (Player)commandSender;

            if(command.getName().equalsIgnoreCase("stats")){

                UPlayer uPlayer = gameManager.getPlayerManager().getPlayer(p);

                int deaths = uPlayer.getDeaths();
                int kills = uPlayer.getKills();
                int points = uPlayer.getPoints();
                int coins = uPlayer.getCoins();
                int exp = uPlayer.getExp();
                int level = uPlayer.getLevel();
                double kdr = deaths != 0 ? (double) kills / deaths : kills;
                p.sendMessage(main.color("&6====== &eStats &6====="));
                p.sendMessage(main.color("&aKills: &e" + kills));
                p.sendMessage(main.color("&cDeaths: &e" + deaths));
                p.sendMessage(main.color("&bK/D: &e" + kdr));
                p.sendMessage(main.color("&dPoints: &e" + points));
                p.sendMessage(main.color("&6Coins: &e" + coins));
                p.sendMessage(main.color("&5Current xp: &e" + exp));
                p.sendMessage(main.color("&9Level: &e" + level));
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



        return false;
    }
}
