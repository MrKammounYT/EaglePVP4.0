package eaglemc.pvp;


import eaglemc.Commands.Admin;
import eaglemc.Commands.User;
import eaglemc.Event.EventCommand;
import eaglemc.GameManager.GameManager;
import eaglemc.PvPCoreAPI;
import eaglemc.Utils.others.EmojiUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {


    private static main instance;

    private GameManager gameManager;

    public static String Prefix;
    private final String Version = "4.0.9 Beta";


    private PvPCoreAPI pvpcoreAPI;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        instance = this;
        Prefix =  color(getConfig().getString("Prefix"));
        gameManager = new GameManager(this);
        pvpcoreAPI = new PvPCoreAPI(gameManager);
        getLogger().info("PvP "+ Version +" Has Been Enabled");
        getLogger().info("Coded By Kammoun");
        EmojiUtils.load();
        getCommand("pvp").setExecutor(new Admin(gameManager));
        getCommand("stats").setExecutor(new User(gameManager));
        getCommand("spawn").setExecutor(new User(gameManager));
        getCommand("fix").setExecutor(new User(gameManager));
        getCommand("save").setExecutor(new User(gameManager));
        getCommand("vote").setExecutor(new User(gameManager));
        //getCommand("event").setExecutor(new EventCommand(gameManager.getEventManager()));
    }



    public GameManager getGameManager() {
        return gameManager;
    }

    public String getVersion() {
        return Version;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        gameManager.SaveData(this);
    }


    public void reload(Player p){
        HandlerList.unregisterAll(this);
        reloadConfig();
        gameManager =new GameManager(this);
        pvpcoreAPI = new PvPCoreAPI(gameManager);
        p.sendMessage(Prefix + color("&aPvP Plugin Has been reloaded!"));
    }



    public static main getInstance() {
        return instance;
    }


    public static String color(String phrase){
        return ChatColor.translateAlternateColorCodes('&',phrase);
    }


}
