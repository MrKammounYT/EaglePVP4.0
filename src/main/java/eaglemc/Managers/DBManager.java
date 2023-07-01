package eaglemc.Managers;

import com.mysql.jdbc.Connection;
import eaglemc.DataBase.SPlayer;
import org.bukkit.Bukkit;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {


    private Connection connection;

    private SPlayer sPlayer;


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
        }

    }

    public SPlayer getSPlayer() {
        return sPlayer;
    }
}