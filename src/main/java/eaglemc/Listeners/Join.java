package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    private GameManager gm;

    public Join(GameManager gm){
        this.gm = gm;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        gm.getPlayerManager().createPlayer(e.getPlayer());
    }
}
