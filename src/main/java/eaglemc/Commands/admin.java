package eaglemc.Commands;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.Heads;
import eaglemc.Utils.LocationAPI;
import eaglemc.Utils.ScoreBoard;
import eaglemc.pvp.main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

public class admin implements CommandExecutor {


    private final GameManager gm;

    public admin(GameManager gm){
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
                    gm.getPlayerManager().getPlayer(p).setBuilding(false);
                    p.playSound(p.getLocation(), Sound.LEVEL_UP,3.0f,3.0f);
                    return true;
                }
                gm.getPlayerManager().getPlayer(p).setBuilding(true);
                p.sendMessage(main.Prefix + main.color("&aYou have entered build mode !"));
                p.playSound(p.getLocation(), Sound.LEVEL_UP,3.0f,3.0f);

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
                else if(args[1].equalsIgnoreCase("quests")){
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


                }
                else{
                    p.sendMessage(main.Prefix + main.color("&c&l/pvp setloc (spawn/leaderboard/shop/quests"));
                }
            }

        }else if(args.length == 3){
            String d = args[0];
         if(d.equalsIgnoreCase("setlocation") || d.equalsIgnoreCase("setloc")){
                if(args[1].equalsIgnoreCase("leaderboard")){
                    if(args[2].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("2")
                            || args[2].equalsIgnoreCase("3")){
                        Location lc= p.getLocation();
                        LocationAPI.setLocation(lc,"top"+args[2]);
                        int limit = Integer.parseInt(args[2]);
                        CreateTopPlayers(lc,gm.getDBManager().getSPlayer().getTop(limit),limit);


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
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }
               else if(what.equalsIgnoreCase("kills")){
                    gm.getPlayerManager().getPlayer(t).addKills(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been added to "+t.getDisplayName()));
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("deaths")){
                    gm.getPlayerManager().getPlayer(t).addDeaths(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been added to "+t.getDisplayName()));
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("points")){
                    gm.getPlayerManager().getPlayer(t).addPoints(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been added to "+t.getDisplayName()));
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("coins")){
                    gm.getPlayerManager().getPlayer(t).addCoins(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been added to "+t.getDisplayName()));
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
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
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("kills")){
                    gm.getPlayerManager().getPlayer(t).setKills(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("deaths")){
                    gm.getPlayerManager().getPlayer(t).setDeaths(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("points")){
                    gm.getPlayerManager().getPlayer(t).setPoints(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("coins")){
                    gm.getPlayerManager().getPlayer(t).setCoins(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }
                else if(what.equalsIgnoreCase("level")){
                    gm.getPlayerManager().getPlayer(t).setlevel(amount);
                    p.sendMessage(main.Prefix + main.color("&a"+amount +" &e"+args[1]+" has been set to "+t.getDisplayName()));
                    ScoreBoard.refresh(gm.getPlayerManager().getPlayer(t));
                }

            }
            else {
                Help(p);
            }
        }



        return false;
    }



    void CreateTopPlayers(Location lc,String name,int top){
        ArmorStand player = lc.getWorld().spawn(lc,ArmorStand.class);
        player.setSmall(true);
        player.setCustomName(main.color("&e#&a&l"+top + " &r"+name));
        player.setCustomNameVisible(true);
        player.setHelmet(Heads.getPlayerHead(name));
        if(top == 1){
            player.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
            player.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            player.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            player.setBoots(new ItemStack(Material.IRON_BOOTS));
        }else if(top == 2){
            player.setItemInHand(new ItemStack(Material.IRON_SWORD));
            player.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
            player.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
            player.setBoots(new ItemStack(Material.GOLD_BOOTS));
        }
        else {
            player.setItemInHand(new ItemStack(Material.STONE_SWORD));
            player.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            player.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            player.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        }




    }


    void Help(Player p){
        p.sendMessage(main.color("&eAvailable commands:"));
        p.sendMessage(main.color("&e/cosmetics setkit <kitName> &7- Create a new kit"));
        p.sendMessage(main.color("&e/cosmetics setlocation <location> &7- Set a specific location"));
        p.sendMessage(main.color("&e/cosmetics give <what> <player> <amount> &7- Give resources to a player"));
        p.sendMessage(main.color("&e/cosmetics set <what> <player> <amount> &7- Set the value of a resource for a player"));
        p.sendMessage(main.color("&eNote: Replace <kitName>, <location>, <what>, <player>, and <amount> with appropriate values."));

    }
}
