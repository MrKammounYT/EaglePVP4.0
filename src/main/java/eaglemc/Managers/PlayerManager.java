package eaglemc.Managers;

import eaglemc.DataBase.SPerks;
import eaglemc.DataBase.SPlayer;
import eaglemc.DataBase.STrails;
import eaglemc.GameManager.GameManager;
import eaglemc.Perks;
import eaglemc.Trails;
import eaglemc.Utils.*;
import eaglemc.pvp.main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {


    private final HashMap<UUID, UPlayer> players = new HashMap<UUID, UPlayer>();

    private final DBManager dbManager;

    private final GameManager gm;
    private final SPlayer sPlayer;

    private final SPerks sPerks;

    private final STrails sTrails;

    public PlayerManager(GameManager gm){

        this.dbManager = gm.getDBManager();
        this.gm= gm;
        this.sPlayer = dbManager.getSPlayer();
        this.sPerks = dbManager.getsPerks();
        this.sTrails = dbManager.getsTrails();

    }


    public void createPlayer(Player p){
        if(!isPlayer(p)){
            sPlayer.addPlayer(p.getDisplayName(),p.getUniqueId());
            sPerks.addPlayer(p.getUniqueId());
            sTrails.addPlayer(p.getUniqueId());
            String uuid = p.getUniqueId().toString();
            players.put(p.getUniqueId(),new UPlayer(p.getUniqueId(),sPlayer.getKills(uuid),sPlayer.getDeaths(uuid),
                    sPlayer.getPoints(uuid),sPlayer.getCoins(uuid),sPlayer.getExperience(uuid),sPlayer.getLevel(uuid),getRankColor(p)
                    ,getPerks(p),getSlots(p),getTrails(p),getSelectedTrail(p)));
            if(!p.hasPlayedBefore()){
                getPlayer(p).addPoints(100);
            }

        }
        try {
            for(Kit kits: gm.getKitManager().getKits().values()){
                if(kits.getPermission().equals("noperm")){
                    getPlayer(p).setKit(kits);
                    continue;
                }
                if(p.hasPermission(kits.getPermission())){
                    getPlayer(p).setKit(kits);

                }
            }

        }catch (Exception s){
            Bukkit.getConsoleSender().sendMessage("§cPlease Check Your kits.yml");
        }

        int counter = 0;
        for(Quest quest : gm.getQuestManager().getDailyQuestList().values()){
            if(counter == 3)break;
            getPlayer(p).addDailyQuest(quest);
            counter++;
        }

        new BukkitRunnable(){
            @Override
            public void run() {
               p.teleport(LocationAPI.getLocation("spawn"));
               p.setGameMode(GameMode.SURVIVAL);
               p.setHealth(20);
               p.setFoodLevel(20);
               ScoreBoard.create(getPlayer(p));
               ScoreBoard.refresh(getPlayer(p));
               for(PotionEffect effects : p.getActivePotionEffects()){
                   p.removePotionEffect(effects.getType());
               }
               getPlayer(p).wearKit(p);
            }
        }.runTaskLater(main.getInstance(),10);




    }




    private ArrayList<Trails> getTrails(Player p){
        ArrayList<Trails> list = new ArrayList<>();
        for(int i=1;i<5;i++){
            if(sTrails.HaveTrail(p.getUniqueId(),i)){
                list.add(Trails.getFromID(i));
            }
        }
        return list;
    }

    public Trails getSelectedTrail(Player p){
        return Trails.getFromID(sTrails.getSelectedTrail(p.getUniqueId()));
    }
    private HashMap<Integer, Perks> getSlots(Player p){
        HashMap<Integer, Perks> slots = new HashMap();
        for (int i=1;i<5;i++){
            slots.put(i,Perks.fromId(sPerks.getSelectedPerkSlot(p.getUniqueId(),i)));
        }
        return slots;
    }

    private ArrayList<Integer> getPerks(Player p){
        ArrayList<Integer> s = new ArrayList<>();
        for(int i=1;i<9;i++){
           if(sPerks.havePerk(p.getUniqueId(),i)){
               s.add(i);
           }
        }
        return s;
    }

    private String getRankColor(Player p){
        LuckPerms api = LuckPermsProvider.get();
        User user = api.getUserManager().getUser(p.getName());
        if(user !=null){
            if(user.getPrimaryGroup().equalsIgnoreCase("owner")) {
                return "&e";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("HeadAdmin")) {
                return "&6";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("Admin")) {
                return "&1";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("dev")) {
                return "&3";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("srmod")) {
                return "&4";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("moderator")) {
                return "&c";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("Helper")) {
                return "&a";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("builder")) {
                return "&d";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("trial")) {
                return "&f";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("youtuber")) {
                return "&4";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("eagle")) {
                return "&9";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("diamond")) {
                return "&b";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("gold")) {
                return "&6";
            }
            else if(user.getPrimaryGroup().equalsIgnoreCase("member")) {
                return "&7";
            }else {
                return "&7";
            }
        }
        return "&7";
    }
    public HashMap<UUID, UPlayer> getPlayers() {
        return players;
    }

    public UPlayer getPlayer(Player p){
        return players.get(p.getUniqueId());
    }

    public void removePlayer(Player p){
        players.remove(p.getUniqueId());
    }

    public boolean isPlayer(Player p){
       return players.containsKey(p.getUniqueId());
    }


}
