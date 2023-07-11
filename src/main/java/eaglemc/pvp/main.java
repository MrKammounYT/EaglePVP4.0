package eaglemc.pvp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import eaglemc.Commands.admin;
import eaglemc.Commands.user;
import eaglemc.GameManager.GameManager;
import eaglemc.Listeners.*;
import com.comphenix.protocol.*;
import net.minecraft.server.v1_8_R3.GameRules;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class main extends JavaPlugin {


    private static main instance;

    private GameManager gameManager;

    public static String Prefix;
    private final String Version = "4.0 Alpha";
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        instance = this;
        Prefix =  color(getConfig().getString("Prefix"));
        gameManager = new GameManager(this);
        getLogger().info("PvP "+ Version +" Has Been Enabled");
        getLogger().info("Coded By SrKammounYT");
        protocolManager = ProtocolLibrary.getProtocolManager();
        registerPacketListener();

        Bukkit.getPluginManager().registerEvents(new Join(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new Death(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new Assist(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new Respawn(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new Chat(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new Shop(),this);
        Bukkit.getPluginManager().registerEvents(new Options(gameManager.getPlayerManager()),this);
        Bukkit.getPluginManager().registerEvents(new JumpPad(),this);

        Weather();
        getCommand("pvp").setExecutor(new admin(gameManager));
        getCommand("stats").setExecutor(new user(gameManager));
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


    public void ReloadPlayers(){
        for(Player pls:Bukkit.getOnlinePlayers()){
            gameManager.getPlayerManager().createPlayer(pls);
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
