package eaglemc.Utils;

import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

public class ScoreBoard {


    public static void create(Player p, UPlayer up){
        FastBoard board = new FastBoard(p);
        board.updateTitle(main.color("&6&l⚔ PvP &e&l4.0-Beta"));
        board.updateLines(
                "         ",
                main.color("&fLevel: " + up.getStringLevel()),
                main.color("&fRequired XP: &b"+up.getXPToLevelUp()),
                "          ",
                main.color("&fDeaths: &a" + up.getDeaths()),
                main.color("&fKills: &a" + up.getKills()),
                main.color("&fPoints: &a"+up.getPoints()),
                main.color("&fCoins: &a"+up.getCoins()),
                main.color("&fKillStreaks: &a"+up.getKillStreaks()),
                "   ",
                main.color("&eEaglemc.net")
        );
    }


}
