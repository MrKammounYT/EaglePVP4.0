package eaglemc.Commands;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.UPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import eaglemc.pvp.main;
public class user implements CommandExecutor {


    private GameManager gameManager;

    public user(GameManager gm){
        this.gameManager = gm;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))return true;

        Player player = (Player)commandSender;

            if(command.getName().equalsIgnoreCase("stats")){

                UPlayer uPlayer = gameManager.getPlayerManager().getPlayer(player);

                int deaths = uPlayer.getDeaths();
                int kills = uPlayer.getKills();
                int points = uPlayer.getPoints();
                int coins = uPlayer.getCoins();
                int exp = uPlayer.getExp();
                int level = uPlayer.getLevel();
                double kdr = deaths != 0 ? (double) kills / deaths : kills;
                player.sendMessage(main.color("&6=== &eStats &6==="));
                player.sendMessage(main.color("&aKills: &e" + kills));
                player.sendMessage(main.color("&cDeaths: &e" + deaths));
                player.sendMessage(main.color("&bK/D: &e" + kdr));
                player.sendMessage(main.color("&dPoints: &e" + points));
                player.sendMessage(main.color("&6Coins: &e" + coins));
                player.sendMessage(main.color("&5Current xp: &e" + exp));
                player.sendMessage(main.color("&9Level: &e" + level));
                player.sendMessage(main.color("&6================"));

            }



        return false;
    }
}
