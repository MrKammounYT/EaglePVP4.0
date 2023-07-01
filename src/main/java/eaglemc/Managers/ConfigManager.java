package eaglemc.Managers;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfigManager {


    
    private final FileConfiguration config;
    
    public ConfigManager(FileConfiguration config){
        this.config = config;

    }


    public HashMap<String,String> getConnectionInfo(){
        HashMap<String,String> info = new HashMap<String,String>();
        info.put("JDBC",config.getString("MySQL.JDBC"));
        info.put("Address",config.getString("MySQL.Address"));
        info.put("Username",config.getString("MySQL.Username"));
        info.put("Password",config.getString("MySQL.Password"));
        info.put("Database",config.getString("MySQL.Database"));
    return info;
    }




}
