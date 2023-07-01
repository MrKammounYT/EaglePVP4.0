package eaglemc.Managers;

import eaglemc.DataBase.SPlayer;
import eaglemc.GameManager.GameManager;
import eaglemc.Utils.Kit;
import eaglemc.Utils.UPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {


    final HashMap<UUID, UPlayer> players = new HashMap<UUID, UPlayer>();

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
            Kit kit = null;
            for(Map.Entry<String, Kit> kits : gm.getKitManager().getKits().entrySet()){
                if(!p.hasPermission(kits.getValue().getPermission()))continue;
                kit = kits.getValue();
            }
            String uuid = p.getUniqueId().toString();
            players.put(p.getUniqueId(),new UPlayer(kit,sPlayer.getKills(uuid),sPlayer.getDeaths(uuid),
                    sPlayer.getPoints(uuid),sPlayer.getCoins(uuid),sPlayer.getExperience(uuid),sPlayer.getLevel(uuid)));


        }



        //sql

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
