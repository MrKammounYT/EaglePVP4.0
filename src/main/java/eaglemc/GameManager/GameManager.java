package eaglemc.GameManager;

import eaglemc.DataBase.*;
import eaglemc.Event.EventManager;
import eaglemc.Listeners.*;
import eaglemc.Managers.*;
import eaglemc.PerkEffect.*;
import eaglemc.Quests.Quest;
import eaglemc.Runnables.LeaderBoardUpdate;
import eaglemc.Shop.*;
import eaglemc.Utils.enums.DeathCry;
import eaglemc.Utils.enums.KillStreakEffect;
import eaglemc.Utils.enums.Perks;
import eaglemc.Utils.enums.Trails;
import eaglemc.TrailsEffect.PlayEffect;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GameManager {


    private DataBaseManager DBManager;

    private PlayerManager playerManager;
    private SaveManager saveManager;

    private EventManager eventManager;
    private KitManager kitManager;

    private final ConfigManager configManager;


    private QuestManager questManager;

    private LeaderBoardUpdate leaderBoardUpdate;

    public GameManager(main main){
        configManager = new ConfigManager(main.getConfig());
        this.saveManager = new SaveManager();
        eventManager = new EventManager(this);

        new BukkitRunnable(){

                    @Override
                    public void run() {
                        DBManager = new DataBaseManager(configManager.getConnectionInfo().get("Address"),
                                configManager.getConnectionInfo().get("Username"),
                                configManager.getConnectionInfo().get("Password"),
                                configManager.getConnectionInfo().get("Database"),
                                configManager.getConnectionInfo().get("JDBC_CONNECTION_STRING"));
                        if(!DBManager.isConnected()){
                            return;
                        }
                        new BukkitRunnable(){

                            @Override
                            public void run() {
                                kitManager = new KitManager();
                                questManager = new QuestManager(main);
                                playerManager = new PlayerManager(main.getGameManager());
                                LoadPvP(main);

                            }
                        }.runTask(main);
                    }
                }.runTaskAsynchronously(main);




    }

    private void LoadPvP(main main) {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Join(this),main);
        pm.registerEvents(new Death(this),main);
        pm.registerEvents(new Assist(this),main);
        pm.registerEvents(new Respawn(this),main);
        pm.registerEvents(new Chat(this),main);
        pm.registerEvents(new ShopOpenEvent(playerManager),main);
        pm.registerEvents(new Quest(this),main);
        pm.registerEvents(new Options(playerManager),main);
        pm.registerEvents(new JumpPad(),main);
        pm.registerEvents(new SectionSelectionEvent(this),main);
        pm.registerEvents(new Grappler(playerManager),main);
        pm.registerEvents(new Berserker(playerManager),main);
        pm.registerEvents(new GoldenHead(playerManager),main);
        pm.registerEvents(new Vampire(playerManager),main);
        pm.registerEvents(new Quiver(playerManager),main);
        pm.registerEvents(new TNT(playerManager),main);
        pm.registerEvents(new PlayEffect(playerManager),main);
        pm.registerEvents(new KitSelectionEvent(this),main);
        pm.registerEvents(new Flint(playerManager),main);
        pm.registerEvents(new SelectionEvent(playerManager),main);
        pm.registerEvents(new PurchaseEvent(playerManager),main);
        pm.registerEvents(new Strength(playerManager),main);
        pm.registerEvents(eventManager,main);
        pm.registerEvents(new Quit(this),main);

        Weather();
        leaderBoardUpdate = new LeaderBoardUpdate(this,main.getConfig());
        leaderBoardUpdate.runTaskTimer(main,0,20);
    }

    public EventManager getEventManager() {
        return eventManager;
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

    public LeaderBoardUpdate getLeaderBoardUpdate() {
        return leaderBoardUpdate;
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public void SaveData(main main){
                SPlayer sPlayer = DBManager.getSPlayer();
                SPerks sPerks = DBManager.getsPerks();
                STrails sTrails =DBManager.getsTrails();
            SDeathCry sDeathCry = DBManager.getsDeathCry();
            SKillStreaksEffect sKillStreaksEffect = DBManager.getsKillStreaksEffect();

        for (UUID uuid : playerManager.getPlayers().keySet()){
                    UPlayer up = playerManager.getPlayers().get(uuid);
                    sPlayer.setCoins(uuid.toString(),up.getCoins());
                    sPlayer.setLevel(uuid.toString(),up.getLevel());
                    sPlayer.setDeath(uuid.toString(),up.getDeaths());
                    sPlayer.setKill(uuid.toString(),up.getKills());
                    sPlayer.setExp(uuid.toString(),up.getExp());
                    sPlayer.setPoints(uuid.toString(),up.getPoints());
                    sPlayer.setCustomName(uuid,up.getCustomName(up.getPlayer()));
                    for(Perks perks : up.getPlayerPerks()){
                        sPerks.addPerk(uuid,perks.getId());
                    }
                    for(int i=1;i<5;i++){
                        sPerks.setPerkInSlot(uuid,i,up.getPerkInSlot(i).getId());
                    }
                    for(Trails trails : up.getTrails()){
                        sTrails.addTrail(uuid,trails.getId());
                    }
                    for(DeathCry deathCry : up.getDeathCries()){
                        sDeathCry.addDeathCry(uuid,deathCry.getId());
                    }
                    for(KillStreakEffect killStreakEffect : up.getKillStreakEffects()){
                        sKillStreaksEffect.addKillStreaksEffect(uuid,killStreakEffect.getId());
                    }
                    sPlayer.setSelectedTrail(uuid.toString(),up.getSelectedTrail().getId());
                    sPlayer.setSelectedDeathCry(uuid.toString(),up.getSelectedDeathCry().getId());
                    sPlayer.setSelectedKillStreakEffect(uuid.toString(),up.getSelectedKillStreakEffect().getId());

                }
    }

    public DataBaseManager getDBManager() {
        return DBManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

}
