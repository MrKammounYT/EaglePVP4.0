package eaglemc.DataBase;

import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class InventorySaveTable {




    private final Connection connection;

    public InventorySaveTable(Connection connection){
        this.connection = connection;

    }



    public void createTable(){
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS INVENTORYSAVE(UUID varchar(100),SWORD INT(1),ROD int(1)," +
                    "BOW int(1),FLINT int(1),AROOWS int(1),PRIMARY KEY (UUID))");
            ps.executeUpdate();



        }catch (SQLException e){
            e.printStackTrace();
        }

    }


}
