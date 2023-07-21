package eaglemc.GameManager;

import eaglemc.DataBase.SPerks;
import eaglemc.DataBase.SPlayer;
import eaglemc.DataBase.STrails;
import eaglemc.Managers.*;
import eaglemc.Trails;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GameManager {


    private final DBManager DBManager;

    private final PlayerManager playerManager;

    private final KitManager kitManager;

    private final ConfigManager configManager;

    private final QuestManager questManager;

    public GameManager(main main){
        configManager = new ConfigManager(main.getConfig());
        this.DBManager = new DBManager(configManager.getConnectionInfo().get("Address"),
                configManager.getConnectionInfo().get("Username"),
                configManager.getConnectionInfo().get("Password"),
                configManager.getConnectionInfo().get("Database"),
                configManager.getConnectionInfo().get("JDBC_CONNECTION_STRING"));
        if(!DBManager.isConnected()){
            Bukkit.getServer().getPluginManager().disablePlugin(main);
        }
        this.playerManager = new PlayerManager(this);
        this.kitManager = new KitManager();
        this.questManager = new QuestManager(main);



    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public void SaveData(){
        SPlayer sPlayer = DBManager.getSPlayer();
        SPerks sPerks = DBManager.getsPerks();
        STrails sTrails =DBManager.getsTrails();
        for (UUID uuid : playerManager.getPlayers().keySet()){
            UPlayer up = playerManager.getPlayers().get(uuid);
            sPlayer.setCoins(uuid.toString(),up.getCoins());
            sPlayer.setLevel(uuid.toString(),up.getLevel());
            sPlayer.setDeath(uuid.toString(),up.getDeaths());
            sPlayer.setKill(uuid.toString(),up.getKills());
            sPlayer.setExp(uuid.toString(),up.getExp());
            sPlayer.setPoints(uuid.toString(),up.getPoints());
            sPlayer.setCustomName(uuid,up.getCustomName());
            for(int id : up.getPlayerPerks()){
                sPerks.addPerk(uuid,id);
            }
            for(int i=1;i<5;i++){
                sPerks.setPerkInSlot(uuid,i,up.getPerkInSlot(i).getId());
            }
            for(Trails trails : up.getTrails()){
                sTrails.addTrail(uuid,trails.getId());
            }
            sTrails.SetSelectedTrail(uuid,up.getSelectedTrail().getId());
        }
    }

    public eaglemc.Managers.DBManager getDBManager() {
        return DBManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

}
