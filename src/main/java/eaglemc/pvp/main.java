package eaglemc.pvp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import eaglemc.Commands.Admin;
import eaglemc.Commands.User;
import eaglemc.GameManager.GameManager;
import eaglemc.Listeners.*;
import com.comphenix.protocol.*;
import eaglemc.Managers.PlayerManager;
import eaglemc.PerkEffect.*;
import eaglemc.Quests.Quest;
import eaglemc.Runnables.LeaderBoardUpdate;
import eaglemc.Shop.*;
import eaglemc.TrailsEffect.PlayEffect;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {


    private static main instance;

    private GameManager gameManager;
    private PlayerManager playerManager;

    public static String Prefix;
    private final String Version = "4.0.1 Beta";
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        instance = this;
        Prefix =  color(getConfig().getString("Prefix"));
        gameManager = new GameManager(this);
        playerManager = gameManager.getPlayerManager();
        getLogger().info("PvP "+ Version +" Has Been Enabled");
        getLogger().info("Coded By SrKammounYT");
        protocolManager = ProtocolLibrary.getProtocolManager();
        registerPacketListener();

        getCommand("pvp").setExecutor(new Admin(gameManager));
        getCommand("stats").setExecutor(new User(gameManager));
        getCommand("spawn").setExecutor(new User(gameManager));
        try {
            LoadPvP();
        }catch (Exception e){

        }
        ReloadPlayers();
    }

    public String getVersion() {
        return Version;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        gameManager.SaveData();
    }

    
    private void LoadPvP() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Join(gameManager),this);
        pm.registerEvents(new Death(gameManager),this);
        pm.registerEvents(new Assist(gameManager),this);
        pm.registerEvents(new Respawn(gameManager),this);
        pm.registerEvents(new Chat(gameManager),this);
        pm.registerEvents(new ShopOpenEvent(playerManager),this);
        pm.registerEvents(new Quest(gameManager),this);
        pm.registerEvents(new Options(playerManager),this);
        pm.registerEvents(new JumpPad(),this);
        pm.registerEvents(new MenusOpenEvent(gameManager),this);
        pm.registerEvents(new PerkSelectionEvent(playerManager),this);
        pm.registerEvents(new PerkPurchaseEvent(playerManager),this);
        pm.registerEvents(new Grappler(playerManager),this);
        pm.registerEvents(new Berserker(playerManager),this);
        pm.registerEvents(new GoldenHead(playerManager),this);
        pm.registerEvents(new Vampire(playerManager),this);
        pm.registerEvents(new Quiver(playerManager),this);
        pm.registerEvents(new TNT(playerManager),this);
        pm.registerEvents(new TrailPurchaseEvent(playerManager),this);
        pm.registerEvents(new TrailsSelectionEvent(playerManager),this);
        pm.registerEvents(new PlayEffect(playerManager),this);
        pm.registerEvents(new KitSelectionEvent(gameManager),this);
        pm.registerEvents(new Flint(playerManager),this);
        Weather();
        LeaderBoardUpdate leaderBoardUpdate = new LeaderBoardUpdate(gameManager,getConfig());
        leaderBoardUpdate.runTaskTimer(this,0,20);
    }





    private void ReloadPlayers(){
        for(Player pls:Bukkit.getOnlinePlayers()){
            playerManager.createPlayer(pls);
        }
    }
    private void Weather() {
        for(World world :Bukkit.getWorlds()){
            world.setTime(5000);
            world.setWeatherDuration(0);
            world.setDifficulty(Difficulty.EASY);
            world.setThundering(false);
            world.setStorm(false);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doWeatherCycle", "false");
            world.setGameRuleValue("doMobSpawning", "false");
        }
    }

    public static main getInstance() {
        return instance;
    }


    public static String color(String phrase){
        return ChatColor.translateAlternateColorCodes('&',phrase);
    }

    private void registerPacketListener() {
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL,
                PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                Entity vehicle = player.getVehicle();
                if (vehicle != null && vehicle.getType() == EntityType.ENDER_PEARL && event.getPacket().getBooleans().read(1)) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
