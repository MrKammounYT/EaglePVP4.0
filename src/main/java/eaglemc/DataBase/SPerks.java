package eaglemc.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SPerks {



    private final Connection connection;



    public SPerks(Connection connection) {
        this.connection = connection;
        createPerksDB();
    }

    private void createPerksDB(){
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PERKS (UUID VARCHAR(100),PERK1 int(1),PERK2 int(1)" +
                    ",PERK3 int(1),PERK4 int(1),PERK5 int(1),PERK6 int(1),PERK7 int(1),PERK8 int(1),SLOT1 int(1),SLOT2 int(1),SLOT3 int(1),SLOT4 int(1),PRIMARY KEY (UUID))");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addPlayer(UUID uuid){
        try {
            if(PlayerExists(uuid))return;
            PreparedStatement ps = connection.prepareStatement("INSERT INTO PERKS (UUID,SLOT1,SLOT2,SLOT3,SLOT4) VALUES (?,?,?,?,?)");
            ps.setString(1,uuid.toString());
            ps.setInt(2,0);
            ps.setInt(3,0);
            ps.setInt(4,0);
            ps.setInt(5,0);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean PlayerExists(UUID uuid){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PERKS WHERE UUID =?");
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

    public int getSelectedPerkSlot(UUID uuid, int slot){
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT SLOT"+slot+" FROM PERKS WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("SLOT"+slot);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setPerkInSlot(UUID uuid,int slot,int perkID){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PERKS  SET SLOT"+slot+"=?  WHERE UUID=?");
            ps.setInt(1,perkID);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean havePerk(UUID uuid,int perkID){
        boolean result = false;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT PERK"+perkID+" FROM PERKS WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("PERK"+perkID) >= 1;
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addPerk(UUID uuid,int perkID){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PERKS SET PERK"+perkID+"=?  WHERE UUID=?");
            ps.setInt(1,1);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
