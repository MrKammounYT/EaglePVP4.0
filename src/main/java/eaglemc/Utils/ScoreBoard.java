package eaglemc.Utils;

import eaglemc.GameManager.GameManager;
import eaglemc.pvp.main;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

public class ScoreBoard {


    public static void create(UPlayer up){

        FastBoard board = new FastBoard(up.getPlayer());
        up.setScoreBoard(board);
        board.updateTitle(main.color("&6&l⚔ PvP &e&l4.0-Beta"));


        board.updateLines(
                "         ",
                main.color("&fLevel: " + up.getStringLevel()),
                main.color("&fRequired XP: &b"+up.getXPToLevelUp()),
                main.color("&fQuest: &eComing soon"),
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

    public static void refresh(UPlayer up){
        FastBoard board =  up.getScoreBoard();
        board.updateLines(
                "         ",
                main.color("&fLevel: " + up.getStringLevel()),
                main.color("&fRequired XP: &b"+up.getXPToLevelUp()),
                main.color("&fQuest: &eComing soon"),
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
