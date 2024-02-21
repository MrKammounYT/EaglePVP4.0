package eaglemc.Utils;

import eaglemc.Event.EventManager;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.Event.EventTeams;
import eaglemc.pvp.main;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

public class ScoreBoard {


    public static void create(Player p, UPlayer up){
        FastBoard board = new FastBoard(p);
        board.updateTitle(main.color("&6&l⚔ PvP &e&l"+main.getInstance().getVersion()));
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
                main.color("&eplay.EagleMc.net")
        );
    }

    public static void CreateEventScoreBoard(Player p , EventManager eventManager,int timer){
        FastBoard board = new FastBoard(p);
        board.updateTitle(main.color("&6&l⚔ PvP &e&l4.0-Beta"));
        board.updateLines(
                "         ",
                main.color("&c"+eventManager.getEventType().getType() + " &eEvent"),
                "           ",
                main.color("&fPlayers Remaining: &b"+eventManager.getEventPlayers().size()),
                "          ",
                main.color("&fTeam 1 Players: &b" + eventManager.getEventTeams().get(EventTeams.a).size()),
                main.color("&fTeam 2 Players: &b" + eventManager.getEventTeams().get(EventTeams.b).size()),
                main.color("&f                  "),
                main.color("&fTime Remaining: "+convert(timer)),
                "   ",
                main.color("&eplay.EagleMc.net")
        );
    }
    private static String convert(int secs) {
        int h = secs / 3600, i = secs - h * 3600, m = i / 60, s = i - m * 60;
        String timeF = "";
        if (m < 10) {
            timeF = timeF + "0";
        }
        timeF = timeF + m + ":";
        if (s < 10) {
            timeF = timeF + "0";
        }
        timeF = timeF + s;

        return timeF;
    }


}
