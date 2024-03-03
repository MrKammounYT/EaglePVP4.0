package eaglemc.Managers;

import eaglemc.DataBase.*;
import eaglemc.Utils.ScoreBoard;
import eaglemc.Utils.enums.KillStreakEffect;
import eaglemc.Utils.others.LocationAPI;
import eaglemc.Utils.enums.DeathCry;
import eaglemc.GameManager.GameManager;
import eaglemc.Utils.enums.Perks;
import eaglemc.Utils.enums.Trails;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {


    private final HashMap<UUID, UPlayer> players = new HashMap<UUID, UPlayer>();

    final DataBaseManager dbManager;

    private final GameManager gm;
    private final SPlayer sPlayer;

    private final SKillStreaksEffect sKillStreaksEffect;

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
        this.sKillStreaksEffect = dbManager.getsKillStreaksEffect();
        ReloadPlayers();
    }

    private void ReloadPlayers(){
        for(Player pls:Bukkit.getOnlinePlayers()){
           createPlayer(pls);
        }
    }
    public void createPlayer(Player p){
        p.setGameMode(GameMode.SURVIVAL);
        if(isPlayer(p)) {
            ScoreBoard.create(p,getPlayer(p));
            players.get(p.getUniqueId()).setFlint(1);
            getPlayer(p).getKit().wear(p);
            p.teleport(LocationAPI.getLocation("spawn"));
            p.spigot().setCollidesWithEntities(true);
            return;
        }
        try {
            new BukkitRunnable(){

                @Override
                public void run() {
                    sPlayer.addPlayer(p.getDisplayName(),p.getUniqueId());
                    sPerks.addPlayer(p.getUniqueId());
                    String uuid = p.getUniqueId().toString();
                    players.put(p.getUniqueId(),new UPlayer(p,gm,p.getUniqueId(),sPlayer.getKills(uuid),sPlayer.getDeaths(uuid),
                            sPlayer.getPoints(uuid),sPlayer.getCoins(uuid),sPlayer.getExperience(uuid),sPlayer.getLevel(uuid)
                            ,getPerks(p),getSlots(p),getTrails(p),getSelectedTrail(p),getDeathCry(p),getSelectedDeathCry(p)
                            ,getKillStreaksEffect(p),getSelectedKillStreakEffect(p)));
                }
    }.runTaskAsynchronously(main.getInstance());
        }catch (Exception e){
            p.kickPlayer("Please try and rejoin the game");
            e.printStackTrace();

        }








    }

    private KillStreakEffect getSelectedKillStreakEffect(Player p) {
        return KillStreakEffect.getKillStreakEffectByID(sPlayer.getSelectedKillStreakEffect(p.getUniqueId().toString()));

    }


    private ArrayList<DeathCry> getDeathCry(Player p){
        ArrayList<DeathCry> list = new ArrayList<>();
        for(int id : sDeathCry.getDeathCryForPlayer(p.getUniqueId())){
                list.add(DeathCry.getDeathCryByID(id));
        }
        return list;
    }

    private ArrayList<KillStreakEffect> getKillStreaksEffect(Player p){
        ArrayList<KillStreakEffect> list = new ArrayList<>();
        for(int id : sKillStreaksEffect.getKillStreaksEffectForPlayer(p.getUniqueId())){
            list.add(KillStreakEffect.getKillStreakEffectByID(id));
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


    public HashMap<UUID, UPlayer> getPlayers() {
        return players;
    }

    public UPlayer getPlayer(Player p){
        //add a condition to check if the player is a player
        if(isPlayer(p)) {
            return players.get(p.getUniqueId());
        }
        p.kickPlayer("§cPlease rejoin again");
        removePlayer(p);
        return null;
    }

    public void removePlayer(Player p){
        players.remove(p.getUniqueId());
    }

    public boolean isPlayer(Player p){
       return players.containsKey(p.getUniqueId());
    }


}
