package eaglemc.DataBase;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class SPlayer {


    private final Connection connection;



    public SPlayer(Connection connection) {
        this.connection = connection;
        createStatsDB();
    }

    private void createStatsDB(){
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PVP (NAME VARCHAR(100),UUID VARCHAR(100),CUSTOMNAME VARCHAR(1000)," +
                    "DEATHS INT(100),POINTS INT(100),KILLS INT(100),COINS INT(100),EXP INT(100),LEVEL INT(100)" +
                    ",STRAIL int(1),SDEATHCRY int(1),PRIMARY KEY (UUID))");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addPlayer(String name, UUID uuid){
        try {
            if(PlayerExists(uuid))return;
            PreparedStatement ps = connection.prepareStatement("INSERT INTO PVP (NAME,UUID,POINTS,LEVEL) VALUES (?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,uuid.toString());
            ps.setInt(3,200);
            ps.setInt(4,1);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean PlayerExists(UUID uuid){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PVP WHERE UUID =?");
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



    public void setSelectedTrail(String uuid,int TrailID){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET STRAIL = ? WHERE UUID = ?");
            ps.setInt(1,TrailID);
            ps.setString( 2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setSelectedDeathCry(String uuid,int DeathCryID){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET SDEATHCRY = ? WHERE UUID = ?");
            ps.setInt(1,DeathCryID);
            ps.setString( 2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addKill(String uuid) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET KILLS = KILLS + 1 WHERE UUID = ?");
            ps.setString( 1, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDeath(String uuid) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET DEATHS = DEATHS + 1 WHERE UUID = ?");
            ps.setString( 1, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCoins(String uuid, int amount) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET COINS = COINS + ? WHERE UUID = ?");
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
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET POINTS = POINTS + ? WHERE UUID = ?");
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
            PreparedStatement ps = connection.prepareStatement("SELECT KILLS FROM PVP WHERE UUID = ?");
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
    public int getSelectedTrail(String uuid) {
        int id = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT STRAIL FROM PVP WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("STRAIL");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public int getSelectedDeathCry(String uuid) {
        int id = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT SDEATHCRY FROM PVP WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("SDEATHCRY");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public String  getCustomName(Player p){
        String customName ="";
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT CUSTOMNAME FROM PVP WHERE UUID = ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customName = rs.getString("CUSTOMNAME");
            }
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customName;
    }
    public int getDeaths(String uuid) {
        int deaths = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT DEATHS FROM PVP WHERE UUID = ?");
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
            PreparedStatement ps = connection.prepareStatement("SELECT COINS FROM PVP WHERE UUID = ?");
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
            PreparedStatement ps = connection.prepareStatement("SELECT POINTS FROM PVP WHERE UUID = ?");
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
            PreparedStatement ps = connection.prepareStatement("SELECT LEVEL FROM PVP WHERE UUID = ?");
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
            PreparedStatement ps = connection.prepareStatement("SELECT EXP FROM PVP WHERE UUID = ?");
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


    public void setKill(String uuid,int amount) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET KILLS = ? WHERE UUID = ?");
            ps.setString( 2, uuid);
            ps.setInt(1,amount);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setDeath(String uuid,int amount) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET DEATHS = ? WHERE UUID = ?");
            ps.setString( 2, uuid);
            ps.setInt(1,amount);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setExp(String uuid, int amount) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET EXP = ? WHERE UUID = ?");
            ps.setInt(1, amount);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPoints(String uuid, int amount) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET POINTS = ? WHERE UUID = ?");
            ps.setInt(1, amount);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCoins(String uuid, int amount) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET COINS = ? WHERE UUID = ?");
            ps.setInt(1, amount);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setLevel(String uuid, int level) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET LEVEL = ? WHERE UUID = ?");
            ps.setInt(1, level);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setCustomName(UUID uuid,String name){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PVP SET CUSTOMNAME = ? WHERE UUID = ?");
            ps.setString(1,name);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public DataContainer getTop(int id){
        DataContainer PlayerData;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT NAME,POINTS,DEATHS,CUSTOMNAME FROM PVP ORDER BY POINTS DESC LIMIT "+id);
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                if (++i != id)continue;
                PlayerData = new DataContainer(rs.getString("NAME"),rs.getInt("POINTS")
                        ,rs.getInt("DEATHS"),rs.getString("CUSTOMNAME"));
                return PlayerData;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataContainer("Chesthead",0,0,"&fNone");
    }

}
