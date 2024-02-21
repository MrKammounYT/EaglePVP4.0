package eaglemc.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SDeathCry {


    private final Connection connection;



    public SDeathCry(Connection connection) {
        this.connection = connection;
        CreateTable();
    }

    public void CreateTable(){
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS DeathCry (ID INT(11) NOT NULL AUTO_INCREMENT,UUID VARCHAR(100),Unlocked int(10),PRIMARY KEY(ID))");
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<Integer> getDeathCryForPlayer(UUID playerID) {
        List<Integer> DeathCry = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Unlocked FROM DeathCry WHERE UUID = ?");
            statement.setString(1, playerID.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int DeathCryID = resultSet.getInt("Unlocked");
                DeathCry.add(DeathCryID);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return DeathCry;
    }
    public boolean HaveDeathCry(UUID uuid,int ID){
        return getDeathCryForPlayer(uuid).contains(ID);
    }
    public void addDeathCry(UUID uuid, int id) {
        try {
            if (HaveDeathCry(uuid,id))return;
            PreparedStatement ps = connection.prepareStatement("INSERT INTO DeathCry (UUID, Unlocked) VALUES (?, ?)");
            ps.setString(1, uuid.toString());
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
