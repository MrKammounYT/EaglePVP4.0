package eaglemc.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Fix implements Listener {


    @EventHandler
    public void onFix(PlayerToggleSneakEvent e){
        e.getPlayer().performCommand("fix");
    }
}
