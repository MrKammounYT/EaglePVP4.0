package eaglemc.Managers;

import eaglemc.DataBase.SPlayer;
import eaglemc.GameManager.GameManager;
import eaglemc.Utils.Kit;
import eaglemc.Utils.LocationAPI;
import eaglemc.Utils.ScoreBoard;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {


    private final HashMap<UUID, UPlayer> players = new HashMap<UUID, UPlayer>();

    private final DBManager dbManager;

    private final GameManager gm;
    private final SPlayer sPlayer;

    public PlayerManager(GameManager gm){

        this.dbManager = gm.getDBManager();
        this.gm= gm;
        this.sPlayer = dbManager.getSPlayer();

    }


    public void createPlayer(Player p){
        if(!isPlayer(p)){
            sPlayer.addPlayer(p.getDisplayName(),p.getUniqueId());
            String uuid = p.getUniqueId().toString();
            players.put(p.getUniqueId(),new UPlayer(p,sPlayer.getKills(uuid),sPlayer.getDeaths(uuid),
                    sPlayer.getPoints(uuid),sPlayer.getCoins(uuid),sPlayer.getExperience(uuid),sPlayer.getLevel(uuid)));
        }
        try {
            for(Map.Entry<String,Kit> kits: gm.getKitManager().getKits().entrySet()){
                    if(kits.getValue().getPermission().equals("noperm")){
                        getPlayer(p).setKit(kits.getValue());
                        break;
                    }
                    if(p.hasPermission(kits.getValue().getPermission())){
                        getPlayer(p).setKit(kits.getValue());
                        break;
                    }
            }
        }catch (Exception s){
            Bukkit.getConsoleSender().sendMessage("§cPlease Check Your kits.yml");
            Bukkit.getConsoleSender().sendMessage("§cAnd Setup all Necessary Kits (eagle/diamond/gold/default)!");
        }

        new BukkitRunnable(){
            @Override
            public void run() {
               p.teleport(LocationAPI.getLocation("spawn"));
               p.setGameMode(GameMode.SURVIVAL);
               p.setHealth(20);
               p.setFoodLevel(20);
               ScoreBoard.create(getPlayer(p));
               for(PotionEffect effects : p.getActivePotionEffects()){
                   p.removePotionEffect(effects.getType());
               }
               getPlayer(p).wearKit(p);
            }
        }.runTaskLater(main.getInstance(),10);




    }

    public HashMap<UUID, UPlayer> getPlayers() {
        return players;
    }

    public UPlayer getPlayer(Player p){
        return players.get(p.getUniqueId());
    }

    public void removePlayer(Player p){
        players.remove(p.getUniqueId());
    }

    public boolean isPlayer(Player p){
       return players.containsKey(p.getUniqueId());
    }


}
