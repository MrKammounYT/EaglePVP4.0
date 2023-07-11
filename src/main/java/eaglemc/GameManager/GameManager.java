package eaglemc.GameManager;

import eaglemc.DataBase.SPlayer;
import eaglemc.Managers.ConfigManager;
import eaglemc.Managers.DBManager;
import eaglemc.Managers.KitManager;
import eaglemc.Managers.PlayerManager;
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



    }
    public void SaveData(){
        for (UUID uuid : playerManager.getPlayers().keySet()){
            SPlayer sPlayer = DBManager.getSPlayer();
            UPlayer up = playerManager.getPlayers().get(uuid);
            sPlayer.setCoins(uuid.toString(),up.getCoins());
            sPlayer.setLevel(uuid.toString(),up.getLevel());
            sPlayer.setDeath(uuid.toString(),up.getDeaths());
            sPlayer.setKill(uuid.toString(),up.getKills());
            sPlayer.setExp(uuid.toString(),up.getExp());
            sPlayer.setPoints(uuid.toString(),up.getPoints());
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
