package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.others.EmojiUtils;
import eaglemc.Utils.Holders.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    private final GameManager gameManager;
    public Chat(GameManager gm){

        this.gameManager= gm;
    }
    @EventHandler
    public void PlayerChatEvent(AsyncPlayerChatEvent e)
    {
        UPlayer up = gameManager.getPlayerManager().getPlayer(e.getPlayer());
        String msg = e.getMessage().replace("%","");
        String top ="";
        if(gameManager.getLeaderBoardUpdate().isATopPointPlayer(e.getPlayer())){
            top = main.color("&b#"+gameManager.getLeaderBoardUpdate().getTopPointPlayerData(e.getPlayer()).getTop()+" ");
        }
        e.setFormat(top+up.getCustomName(e.getPlayer()) +  " §7» " + EmojiUtils.turnToEmoji(msg));
    }
}
