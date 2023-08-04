package eaglemc.Managers;

import com.mysql.jdbc.Connection;
import eaglemc.DataBase.SDeathCry;
import eaglemc.DataBase.SPerks;
import eaglemc.DataBase.SPlayer;
import eaglemc.DataBase.STrails;
import org.bukkit.Bukkit;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private Connection connection;

    private SPlayer sPlayer;
    private SPerks sPerks;

    private STrails sTrails;
    private SDeathCry sDeathCry;

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection !=null;
    }

    public DBManager(String address, String username, String Password, String database, String JDBC_CONNECTION_STRING) {
            try {
                connection = (Connection) DriverManager.getConnection("jdbc:mysql://" +address + ":3306" +"/" + database + "?autoReconnect=true&useSSL=false",username, Password);
                Bukkit.getConsoleSender().sendMessage("§7PvP: §aSuccessful Connected to the Database");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        if(isConnected()){
            sPlayer = new SPlayer(connection);
            sPerks = new SPerks(connection);
            sTrails = new STrails(connection);
            sDeathCry = new SDeathCry(connection);
        }

    }

    public SDeathCry getsDeathCry() {
        return sDeathCry;
    }

    public STrails getsTrails() {
        return sTrails;
    }

    public SPerks getsPerks() {
        return sPerks;
    }

    public SPlayer getSPlayer() {
        return sPlayer;
    }
}
