package eaglemc.Commands;

import eaglemc.DataBase.DataContainer;
import eaglemc.GameManager.GameManager;
import eaglemc.Utils.others.Heads;
import eaglemc.Utils.others.LocationAPI;
import eaglemc.Utils.ScoreBoard;
import eaglemc.pvp.main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class Admin implements CommandExecutor {


    private final GameManager gm;

    public Admin(GameManager gm){
        this.gm = gm;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player))return true;

        Player p = (Player)commandSender;

        if(!p.hasPermission("pvp.admin"))return true;

        if(args.length == 0){
            //help

        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")){
                Help(p);
            }
            else if(args[0].equalsIgnoreCase("build")){
                if(gm.getPlayerManager().getPlayer(p).isInBuild()){
                    p.sendMessage(main.Prefix + main.color("&cYou have left build mode !"));
                    p.setGameMode(GameMode.SURVIVAL);
                    gm.getPlayerManager().getPlayer(p).setBuilding(false);
                    p.playSound(p.getLocation(), Sound.LEVEL_UP,3.0f,3.0f);
                    return true;
                }
                gm.getPlayerManager().getPlayer(p).setBuilding(true);
                p.sendMessage(main.Prefix + main.color("&aYou have entered build mode !"));
                p.playSound(p.getLocation(), Sound.LEVEL_UP,3.0f,3.0f);
                p.setGameMode(GameMode.CREATIVE);


            }
            else if(args[0].equalsIgnoreCase("reload")){
                main.getInstance().reload(p);
            }

        } else if (args.length == 2) {
            String d = args[0];
            if (d.equalsIgnoreCase("setkit")){
                gm.getKitManager().CreateKit(p,args[1]);
                p.sendMessage(main.Prefix + main.color("&aKit &e"+ args[1] + " &ahas been added!"));
            }
           else if(d.equalsIgnoreCase("setlocation") || d.equalsIgnoreCase("setloc")){
                if(args[1].equals("spawn")){
                    LocationAPI.setLocation(p.getLocation(),args[1]);
                    p.sendMessage(main.Prefix + main.color("&aLocation &e"+ args[1] + " &ahas been set!"));

                }
                else if (args[1].equalsIgnoreCase("shop")){
                    LocationAPI.setLocation(p.getLocation(),args[1]);
                    p.sendMessage(main.Prefix + main.color("&aLocation &e"+ args[1] + " &ahas been set!"));
                    Villager shop = p.getWorld().spawn(p.getLocation(),Villager.class);
                    shop.setAdult();
                    shop.setHealth(20);
                    shop.setCustomName(main.color(main.getInstance().getConfig().getString("Shop-Npc-Name")));
                    shop.setCustomNameVisible(true);
                    shop.setProfession(Villager.Profession.BLACKSMITH);
                    net.minecraft.server.v1_8_R3.Entity nmsVil = ((CraftEntity) shop).getHandle();
                    NBTTagCompound comp = new NBTTagCompound();
                    nmsVil.c(comp);
                    comp.setByte("NoAI", (byte) 1);
                    nmsVil.f(comp);
                    nmsVil.b(true);
                }
             /*   else if(args[1].equalsIgnoreCase("quests")){
                    LocationAPI.setLocation(p.getLocation(),args[1]);
                    p.sendMessage(main.Prefix + main.color("&aLocation &e"+ args[1] + " &ahas been set!"));
                    Villager shop = p.getWorld().spawn(p.getLocation(),Villager.class);
                    shop.setAdult();
                    shop.setHealth(20);
                    shop.setCustomName(main.color(main.getInstance().getConfig().getString("Quest-Npc-Name")));
                    shop.setCustomNameVisible(true);
                    shop.setProfession(Villager.Profession.LIBRARIAN);
                    net.minecraft.server.v1_8_R3.Entity nmsVil = ((CraftEntity) shop).getHandle();
                    NBTTagCompound comp = new NBTTagCompound();
                    nmsVil.c(comp);
                    comp.setByte("NoAI", (byte) 1);
                    nmsVil.f(comp);
                    nmsVil.b(true);


                }*/
                else{
                    p.sendMessage(main.Prefix + main.color("&c&l/pvp setloc (spawn/leaderboard/shop/quests"));
                }
            }

        }else if(args.length == 3){
            String d = args[0];
         if(d.equalsIgnoreCase("setlocation") || d.equalsIgnoreCase("setloc")){
                if(args[1].equalsIgnoreCase("leaderboard")){
                    if(args[2].equalsIgnoreCase("kills") || args[2].equalsIgnoreCase("points")){
                        Location lc= p.getLocation();
                        LocationAPI.setLocation(lc,("top"+args[2]).toLowerCase());
                        CreateTopPlayers(lc,args[2].toLowerCase());


                    }



                }
         }
        }
        else if(args.length == 4){
            if(args[0].equalsIgnoreCase("give")){
                String what = args[1];
                Player t = Bukkit.getPlayer(args[2]);
                if(t == null){
                    p.sendMessage(main.Prefix + main.color("&cThis Player isn't online"));
                    return true;
                }
                int amount  = Integer.parseInt(args[3]);
                if(what.equalsIgnoreCase("xp")){
                    gm.getPlayerManager().getPlayer(t).addexp(t,amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been added to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }
               else if(what.equalsIgnoreCase("kills")){
                    gm.getPlayerManager().getPlayer(t).addKills(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been added to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("deaths")){
                    gm.getPlayerManager().getPlayer(t).addDeaths(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been added to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("points")){
                    gm.getPlayerManager().getPlayer(t).addPoints(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been added to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("coins")){
                    gm.getPlayerManager().getPlayer(t).addCoins(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been added to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }

            }
            else if(args[0].equalsIgnoreCase("set")){
                String what = args[1];
                Player t = Bukkit.getPlayer(args[2]);
                if(t == null){
                    p.sendMessage(main.Prefix + main.color("&cThis Player isn't online"));
                    return true;
                }
                int amount  = Integer.parseInt(args[3]);
                if(what.equalsIgnoreCase("xp")){
                    gm.getPlayerManager().getPlayer(t).setexp(t,amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("kills")){
                    gm.getPlayerManager().getPlayer(t).setKills(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("deaths")){
                    gm.getPlayerManager().getPlayer(t).setDeaths(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("points")){
                    gm.getPlayerManager().getPlayer(t).setPoints(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("coins")){
                    gm.getPlayerManager().getPlayer(t).setCoins(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("level")){
                    gm.getPlayerManager().getPlayer(t).setlevel(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.create(p,gm.getPlayerManager().getPlayer(t));
                }

            }
            else {
                Help(p);
            }
        }



        return false;
    }



    void CreateTopPlayers(Location lc,String type){
        for(World worlds : Bukkit.getWorlds()){
            for(Entity entity : worlds.getEntities()){
                if(entity instanceof  ArmorStand){
                    ((ArmorStand) entity).setHealth(0);
                }
            }
        }
        float x = 0.4f;
        ArmorStand leaderboard = (ArmorStand) lc.getWorld().spawnEntity(lc.clone().add(0,1,0), EntityType.ARMOR_STAND);
        leaderboard.setVisible(false);
        leaderboard.setCanPickupItems(true);
        leaderboard.setSmall(true);
        leaderboard.setGravity(false);
        if(type.equalsIgnoreCase("points")){
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
        }else if(type.equalsIgnoreCase("kills")){
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
        }else {
            leaderboard.setHealth(0);
        }

    }


    void Help(Player p){
        p.sendMessage(main.color("&eAvailable commands:"));
        p.sendMessage(main.color("&e/pvp setkit <kitName> &7- Create a new kit"));
        p.sendMessage(main.color("&e/pvp setlocation <location> &7- Set a specific location"));
        p.sendMessage(main.color("&e/pvp give <what> <player> <amount> &7- Give resources to a player"));
        p.sendMessage(main.color("&e/pvp set <what> <player> <amount> &7- Set the value of a resource for a player"));
        p.sendMessage(main.color("&eNote: Replace <kitName>, <location>, <what>, <player>, and <amount> with appropriate values."));

    }
}
