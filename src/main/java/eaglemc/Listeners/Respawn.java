package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.others.LocationAPI;
import eaglemc.pvp.main;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Respawn implements Listener {

    private GameManager gm;

    public Respawn(GameManager gm){
            this.gm = gm;
    }
    @EventHandler
    public void onRespawnEvent(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        new BukkitRunnable(){
            @Override
            public void run() {

                p.teleport(LocationAPI.getLocation("spawn"));
                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT,3.0f,4.0f);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setGameMode(GameMode.SURVIVAL);
                gm.getPlayerManager().getPlayer(p).setFlint(1);
                gm.getPlayerManager().getPlayer(p).wearKit(p);

            }
        }.runTask(main.getInstance());

    }



}
