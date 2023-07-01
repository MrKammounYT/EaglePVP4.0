package eaglemc.pvp;

import eaglemc.Commands.admin;
import eaglemc.GameManager.GameManager;
import eaglemc.Listeners.Join;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {


    private static main instance;

    private GameManager gameManager;

    public static String Prefix;
    private final String Version = "4.0 Alpha";
    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        instance = this;
        Prefix =  getConfig().getString("Prefix");
        gameManager = new GameManager(this);
        getLogger().info("PvP "+ Version +" Has Been Enabled");
        getLogger().info("Coded By SrKammounYT");


        Bukkit.getPluginManager().registerEvents(new Join(gameManager),this);
        getCommand("pvp").setExecutor(new admin(gameManager));
    }

    public String getVersion() {
        return Version;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


    public static main getInstance() {
        return instance;
    }
    public static String color(String phrase){
        return ChatColor.translateAlternateColorCodes('&',phrase);
    }
}
