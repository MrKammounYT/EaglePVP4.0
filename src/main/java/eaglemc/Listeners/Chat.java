package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.UPlayer;
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
        e.setFormat(up.getStringLevel() + " "+e.getPlayer().getDisplayName() + " §7» " + e.getMessage());
    }
}
