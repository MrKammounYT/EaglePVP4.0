package eaglemc.Commands;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.LocationAPI;
import eaglemc.pvp.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

            }

        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("setkit")){
                gm.getKitManager().addKit(args[1],p.getInventory());
                p.sendMessage(main.Prefix + main.color("&aKit &e"+ args[1] + " &ahas been added!"));
            }
           else if(args[0].equalsIgnoreCase("setlocation")){
                if(args[1].equals("spawn") || args[1].equals("leaderboard") ){
                    LocationAPI.setLocation(p.getLocation(),args[1]);
                    p.sendMessage(main.Prefix + main.color("&aLocation &e"+ args[1] + " &ahas been set!"));
                }else if (args[1].equals("shop")  || args[1].equals("quests")){


                }
            }

        }



        return false;
    }
}
