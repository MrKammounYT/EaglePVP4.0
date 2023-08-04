package eaglemc.Managers;

import eaglemc.DataBase.SDeathCry;
import eaglemc.DataBase.SPerks;
import eaglemc.DataBase.SPlayer;
import eaglemc.DataBase.STrails;
import eaglemc.Utils.ScoreBoard;
import eaglemc.enums.DeathCry;
import eaglemc.GameManager.GameManager;
import eaglemc.enums.Perks;
import eaglemc.enums.Trails;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {


    private final HashMap<UUID, UPlayer> players = new HashMap<UUID, UPlayer>();

    final DBManager dbManager;

    private final GameManager gm;
    private final SPlayer sPlayer;

    private final SPerks sPerks;

    private final STrails sTrails;

    private final SDeathCry sDeathCry;

    public PlayerManager(GameManager gm){

        this.dbManager = gm.getDBManager();
        this.gm= gm;
        this.sPlayer = dbManager.getSPlayer();
        this.sPerks = dbManager.getsPerks();
        this.sTrails = dbManager.getsTrails();
        this.sDeathCry = dbManager.getsDeathCry();
        ReloadPlayers();
    }

    private void ReloadPlayers(){
        for(Player pls:Bukkit.getOnlinePlayers()){
           createPlayer(pls);
        }
    }
    public void createPlayer(Player p){
        if(isPlayer(p)) {
            ScoreBoard.create(p,getPlayer(p));
            return;
        }
            new BukkitRunnable(){

                @Override
                public void run() {
                    sPlayer.addPlayer(p.getDisplayName(),p.getUniqueId());
                    sPerks.addPlayer(p.getUniqueId());
                    String uuid = p.getUniqueId().toString();
                    players.put(p.getUniqueId(),new UPlayer(p,gm,p.getUniqueId(),sPlayer.getKills(uuid),sPlayer.getDeaths(uuid),
                            sPlayer.getPoints(uuid),sPlayer.getCoins(uuid),sPlayer.getExperience(uuid),sPlayer.getLevel(uuid),getRankColor(p)
                            ,getPerks(p),getSlots(p),getTrails(p),getSelectedTrail(p),getDeathCry(p),getSelectedDeathCry(p)));
                    if(!p.hasPlayedBefore()){
                        getPlayer(p).addPoints(100);
                    }
                }
    }.runTaskAsynchronously(main.getInstance());









    }



    private ArrayList<DeathCry> getDeathCry(Player p){
        ArrayList<DeathCry> list = new ArrayList<>();
        for(int i=1;i<6;i++){
            if(sDeathCry.HaveDeathCry(p.getUniqueId(),i)){
                list.add(DeathCry.getDeathCryByID(i));
            }
        }
        return list;
    }


    private ArrayList<Trails> getTrails(Player p){
        ArrayList<Trails> list = new ArrayList<>();
        for(int i=1;i<6;i++){
            if(sTrails.HaveTrail(p.getUniqueId(),i)){
                list.add(Trails.getFromID(i));
            }
        }
        return list;
    }
    private DeathCry getSelectedDeathCry(Player p){
        return DeathCry.getDeathCryByID(sPlayer.getSelectedDeathCry(p.getUniqueId().toString()));
    }
    private Trails getSelectedTrail(Player p){
        return Trails.getFromID(sPlayer.getSelectedTrail(p.getUniqueId().toString()));
    }
    private HashMap<Integer, Perks> getSlots(Player p){
        HashMap<Integer, Perks> slots = new HashMap<>();
        for (int i=1;i<5;i++){
            slots.put(i,Perks.fromId(sPerks.getSelectedPerkSlot(p.getUniqueId(),i)));
        }
        return slots;
    }

    private ArrayList<Perks> getPerks(Player p){
        ArrayList<Perks> s = new ArrayList<>();
        for(int i=1;i<9;i++){
           if(sPerks.havePerk(p.getUniqueId(),i)){
               s.add(Perks.fromId(i));
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
