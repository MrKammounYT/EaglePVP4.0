package eaglemc.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class STrails {

    private final Connection connection;



    public STrails(Connection connection) {
        this.connection = connection;
        CreateTable();
    }

    public void CreateTable(){
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Trails (ID INT(11) NOT NULL AUTO_INCREMENT,UUID VARCHAR(100),Unlocked int(10),PRIMARY KEY(ID))");
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private List<Integer> getTrailsForPlayer(UUID playerID) {
        List<Integer> Trails = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Unlocked FROM Trails WHERE UUID = ?");
            statement.setString(1, playerID.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int TrailID = resultSet.getInt("Unlocked");
                Trails.add(TrailID);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Trails;
    }
    public boolean HaveTrail(UUID uuid,int ID){
        return getTrailsForPlayer(uuid).contains(ID);
    }
    public void addTrail(UUID uuid, int id) {
        try {
            if (HaveTrail(uuid,id))return;
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Trails (UUID, Unlocked) VALUES (?, ?)");
            ps.setString(1, uuid.toString());
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
