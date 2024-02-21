package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;

import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Join implements Listener {

    private final GameManager gm;

    public Join(GameManager gm){
        this.gm = gm;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        gm.getPlayerManager().createPlayer(p);
    }
}
