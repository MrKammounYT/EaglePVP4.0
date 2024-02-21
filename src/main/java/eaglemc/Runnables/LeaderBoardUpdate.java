package eaglemc.Runnables;

import eaglemc.DataBase.DataContainer;
import eaglemc.GameManager.GameManager;
import eaglemc.Utils.others.Heads;
import eaglemc.Utils.others.LocationAPI;
import eaglemc.Utils.Holders.Utils;
import eaglemc.pvp.main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class LeaderBoardUpdate extends BukkitRunnable {

    int timer;

    private final int initialTime;
    private final GameManager gm;

    private HashMap<String,DataContainer> TopPointsPlayers = new HashMap<String,DataContainer>();
    public LeaderBoardUpdate(GameManager gm, FileConfiguration file ){
        this.gm = gm;
        initialTime = file.getInt("Leaderboard-update-time");
        timer = 2;
        //UpdateLeaderBoard();
    }

    @Override
    public void run() {
        if(timer == 0){
            try {
                UpdateNpcAndLeaderBoards();
                TopPointsPlayers.clear();
                TopPointsPlayers = gm.getDBManager().getSPlayer().getTopPointsPlayers(10);
            }catch (Exception e){
                e.printStackTrace();
            }
            timer = initialTime;
        }

        timer--;
    }


    public void UpdateNpcAndLeaderBoards(){
        loadShops();
        updateKills();
        updatePoints();
    }
    public boolean isATopPointPlayer(Player p){
        return TopPointsPlayers.containsKey(p.getUniqueId().toString());
    }
    public DataContainer getTopPointPlayerData(Player p){
        return TopPointsPlayers.get(p.getUniqueId().toString());
    }
    private void updateKills(){
        if(LocationAPI.getLocation("topkills") == null)return;
            Location lc = LocationAPI.getLocation("topkills");
            float x = 0.4f;
            ArmorStand leaderboard = (ArmorStand) lc.getWorld().spawnEntity(lc.clone().add(0,1,0), EntityType.ARMOR_STAND);
            leaderboard.setVisible(false);
            leaderboard.setCanPickupItems(true);
            leaderboard.setSmall(true);
            leaderboard.setGravity(false);
            leaderboard.setCustomName("§e§lLeaderboard - §6§lSorted By Kills");
            leaderboard.setCustomNameVisible(true);
            for (int i = 1; i <= 10; i++) {
                DataContainer container = gm.getDBManager().getSPlayer().getTopKills(i);
                ArmorStand ar = (ArmorStand) lc.getWorld().spawnEntity(lc.clone().add(0.0,1 -(x * i), 0.0),
                        EntityType.ARMOR_STAND);
                ar.setVisible(false);
                ar.setCanPickupItems(true);
                ar.setSmall(true);
                ar.setGravity(false);
                ar.setCustomName(main.color("&b#&e&l"+i+ " &r"+container.getCustomName() + " &7- &e"+container.getKills()));
                ar.setCustomNameVisible(true);

        }
        //top points

    }

    private void updatePoints(){
        if(LocationAPI.getLocation("toppoints") == null)return;
        Location lc = LocationAPI.getLocation("toppoints");
        float x = 0.4f;
        ArmorStand leaderboard = (ArmorStand) lc.getWorld().spawnEntity(lc.clone().add(0,1,0), EntityType.ARMOR_STAND);
        leaderboard.setVisible(false);
        leaderboard.setCanPickupItems(true);
        leaderboard.setSmall(true);
        leaderboard.setGravity(false);
        leaderboard.setCustomName("§e§lLeaderboard - §6§lSorted By Points");
        leaderboard.setCustomNameVisible(true);
        for (int i = 1; i <= 10; i++) {
            DataContainer container = gm.getDBManager().getSPlayer().getTopPoints(i);
            ArmorStand ar = (ArmorStand) lc.getWorld().spawnEntity(lc.clone().add(0.0,1 -(x * i), 0.0),
                    EntityType.ARMOR_STAND);
            ar.setVisible(false);
            ar.setCanPickupItems(true);
            ar.setSmall(true);
            ar.setGravity(false);
            ar.setCustomName(main.color("&b#&e&l"+i+ " &r"+container.getCustomName() + " &7- &e"+container.getPoints()));
            ar.setCustomNameVisible(true);

        }
    }
    private void loadShops(){
        for(World worlds : Bukkit.getWorlds()){
            for(Entity entity : worlds.getEntities()){
                if(entity instanceof  ArmorStand){
                    ((ArmorStand) entity).setHealth(0);
                }
                if(entity instanceof Villager){
                    ((Villager) entity).setHealth(0);
                }
            }
        }

        if(LocationAPI.getLocation("shop") !=null){
            Location lc = LocationAPI.getLocation("shop");
            Villager shop = lc.getWorld().spawn(lc,Villager.class);
            ArmorStand topArmorStand = lc.getWorld().spawn(lc.clone().add(0,0.3,0),ArmorStand.class);
            ArmorStand under = lc.getWorld().spawn(lc.clone().add(0,0,0),ArmorStand.class);
            under.setVisible(false);
            under.setCustomNameVisible(true);
            under.setCustomName(main.color("&7Right-Click To Open"));
            under.setGravity(false);
            topArmorStand.setCustomName(main.color(main.getInstance().getConfig().getString("Shop-Npc-Name")));
            topArmorStand.setCustomNameVisible(true);
            topArmorStand.setVisible(false);
            topArmorStand.setGravity(false);
            shop.setAdult();
            shop.setHealth(20);
            shop.setCustomNameVisible(false);
            shop.setProfession(Villager.Profession.BLACKSMITH);
            Utils.shop.add(shop);
            net.minecraft.server.v1_8_R3.Entity nmsVil = ((CraftEntity) shop).getHandle();
            NBTTagCompound comp = new NBTTagCompound();
            nmsVil.c(comp);
            comp.setByte("NoAI", (byte) 1);
            nmsVil.f(comp);
            nmsVil.b(true);
        }
    }
}
