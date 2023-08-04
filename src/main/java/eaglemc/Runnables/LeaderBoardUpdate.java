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
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class LeaderBoardUpdate extends BukkitRunnable {

    int timer;

    private final int initialTime;
    private final GameManager gm;

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
                UpdateLeaderBoard();
            }catch (Exception e){
                e.printStackTrace();
            }
            timer = initialTime;
        }

        timer--;
    }

    private void UpdateLeaderBoard(){
        loadShops();
        for(int i = 1;i<4;i++){
            if(LocationAPI.getLocation("top"+i) == null)continue;
            Location lc = LocationAPI.getLocation("top"+i);
            ArmorStand player = lc.getWorld().spawn(lc,ArmorStand.class);
            ArmorStand topArmorStand = lc.getWorld().spawn(lc.clone().add(0,0.3,0),ArmorStand.class);
            ArmorStand under = lc.getWorld().spawn(lc.clone().add(0,0,0),ArmorStand.class);
            DataContainer container = gm.getDBManager().getSPlayer().getTop(i);
            under.setVisible(false);
            under.setCustomNameVisible(true);
            under.setCustomName(main.color("&a"+container.getPoints()+" &ePoints"));
            under.setGravity(false);
            under.setSmall(true);
            topArmorStand.setCustomName(main.color("&e#&a&l"+i+ " &r"+container.getCustomName()));
            topArmorStand.setCustomNameVisible(true);
            topArmorStand.setVisible(false);
            topArmorStand.setGravity(false);
            topArmorStand.setSmall(true);
            player.setSmall(true);
            player.setCustomNameVisible(false);
            player.setHelmet(Heads.getPlayerHead(container.getName()));
            if(i == 1){
                player.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
                player.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                player.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                player.setBoots(new ItemStack(Material.IRON_BOOTS));
                lc.clone().add(0,-1,0).getBlock().setType(Material.DIAMOND_BLOCK);
            }else if(i == 2){
                player.setItemInHand(new ItemStack(Material.IRON_SWORD));
                player.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
                player.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
                player.setBoots(new ItemStack(Material.GOLD_BOOTS));
                lc.clone().add(0,-1,0).getBlock().setType(Material.GOLD_BLOCK);
            }
            else {
                player.setItemInHand(new ItemStack(Material.STONE_SWORD));
                player.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                player.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                player.setBoots(new ItemStack(Material.LEATHER_BOOTS));
                lc.clone().add(0,-1,0).getBlock().setType(Material.IRON_BLOCK);
            }
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
     /*   if(LocationAPI.getLocation("quests") !=null){
            Location lc = LocationAPI.getLocation("quests");
            ArmorStand topArmorStand = lc.getWorld().spawn(lc.clone().add(0,0.3,0),ArmorStand.class);
            ArmorStand under = lc.getWorld().spawn(lc.clone().add(0,0,0),ArmorStand.class);
            under.setVisible(false);
            under.setCustomNameVisible(true);
            under.setCustomName(main.color("&7Right-Click To Open"));
            under.setGravity(false);
            topArmorStand.setCustomName(main.color(main.getInstance().getConfig().getString("Quest-Npc-Name")));
            topArmorStand.setCustomNameVisible(true);
            topArmorStand.setVisible(false);
            topArmorStand.setGravity(false);
            Villager quest = lc.getWorld().spawn(lc,Villager.class);
            quest.setAdult();
            quest.setHealth(20);
            quest.setCustomNameVisible(false);
            quest.setProfession(Villager.Profession.LIBRARIAN);
            Utils.Quest.add(quest);
            net.minecraft.server.v1_8_R3.Entity nmsVil = ((CraftEntity) quest).getHandle();
            NBTTagCompound comp = new NBTTagCompound();
            nmsVil.c(comp);
            comp.setByte("NoAI", (byte) 1);
            nmsVil.f(comp);
            nmsVil.b(true);
        }*/
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
