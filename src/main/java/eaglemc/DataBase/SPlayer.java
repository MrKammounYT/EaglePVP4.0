package eaglemc.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SPlayer {


    private final Connection connection;



    public SPlayer(Connection connection) {
        this.connection = connection;
        createStatsDB();
    }

    private void createStatsDB(){
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS  PLAYERS (NAME VARCHAR(100),UUID VARCHAR(100)," +
                    "DEATHS INT(100),POINTS INT(100),KILLS INT(100),COINS INT(100),EXP INT(100),LEVEL INT(100),PRIMARY KEY (UUID))");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addPlayer(String name, UUID uuid){
        try {
            if(PlayerExists(uuid))return;
            PreparedStatement ps = connection.prepareStatement("INSERT INTO PLAYERS (NAME,UUID,LEVEL) VALUES (?,?,?)");
            ps.setString(1,name);
            ps.setString(2,uuid.toString());
            ps.setInt(3,1);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean PlayerExists(UUID uuid){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PLAYERS WHERE UUID =?");
            ps.setString(1,uuid.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                ps.close();
                return true;
            }
            ps.close();
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public void addKill(String uuid) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PLAYERS SET KILLS = KILLS + 1 WHERE UUID = ?");
            ps.setString( 1, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDeath(String uuid) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PLAYERS SET DEATHS = DEATHS + 1 WHERE UUID = ?");
            ps.setString( 1, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCoins(String uuid, int amount) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PLAYERS SET COINS = COINS + ? WHERE UUID = ?");
            ps.setInt(1, amount);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPoints(String uuid, int amount) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PLAYERS SET POINTS = POINTS + ? WHERE UUID = ?");
            ps.setInt(1, amount);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getKills(String uuid) {
        int kills = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT KILLS FROM PLAYERS WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kills = rs.getInt("KILLS");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kills;
    }

    public int getDeaths(String uuid) {
        int deaths = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT DEATHS FROM PLAYERS WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                deaths = rs.getInt("DEATHS");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deaths;
    }

    public int getCoins(String uuid) {
        int coins = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COINS FROM PLAYERS WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                coins = rs.getInt("COINS");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coins;
    }

    public int getPoints(String uuid) {
        int points = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT POINTS FROM PLAYERS WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                points = rs.getInt("POINTS");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }


    public int getLevel(String uuid){
        int level = 1;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT LEVEL FROM PLAYERS WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                level = rs.getInt("LEVEL");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return level;
    }
    public int getExperience(String uuid) {
        int experience = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT EXP FROM PLAYERS WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                experience = rs.getInt("EXP");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return experience;
    }


}
