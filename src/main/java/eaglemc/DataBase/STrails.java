package eaglemc.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class STrails {

    private final Connection connection;



    public STrails(Connection connection) {
        this.connection = connection;
        createTrailsDB();
    }

    private void createTrailsDB(){
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TRAILS (UUID VARCHAR(100),T1 int(1),T2 int(1),T3 int(1),T4 int(1),T5 int(1),ST int(1),PRIMARY KEY (UUID))");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(UUID uuid){
        try {
            if(PlayerExists(uuid))return;
            PreparedStatement ps = connection.prepareStatement("INSERT INTO TRAILS (UUID, ST) VALUES (?,?)");
            ps.setString(1,uuid.toString());
            ps.setInt(2,0);
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    boolean PlayerExists(UUID uuid){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM TRAILS WHERE UUID =?");
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


    public boolean HaveTrail(UUID uuid,int TrailID){
        boolean s = false;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT T"+TrailID+" FROM TRAILS WHERE UUID =?");
            ps.setString(1,uuid.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
               s =  rs.getInt("T"+TrailID) >= 1;
            }
            ps.close();
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getSelectedTrail(UUID uuid){
          int  st = 0;
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT ST FROM TRAILS WHERE UUID =?");
                ps.setString(1,uuid.toString());
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    st = rs.getInt("ST");
                }
                ps.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
          return st;

    }

    public void addTrail(UUID uuid,int TrailID){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE TRAILS SET T"+TrailID+"=? WHERE UUID =?");
            ps.setInt(1,1);
            ps.setString(2,uuid.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void SetSelectedTrail(UUID uuid,int TrailID){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE TRAILS SET ST=? WHERE UUID =?");
            ps.setInt(1,TrailID);
            ps.setString(2,uuid.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
