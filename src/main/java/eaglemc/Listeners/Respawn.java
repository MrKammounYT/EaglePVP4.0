package eaglemc.Listeners;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.others.LocationAPI;
import eaglemc.Utils.Title;
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
        p.setGameMode(GameMode.SPECTATOR);
        p.setHealth(20);
        e.setRespawnLocation(gm.getPlayerManager().getPlayer(p).getDeathLocation());
        Title.sendTitle(p, main.color("&cYou Died"),main.color("&fRespawning in 3 seconds"),40);
        new BukkitRunnable(){
            @Override
            public void run() {
                gm.getPlayerManager().getPlayer(p).wearKit(p);
                p.teleport(LocationAPI.getLocation("spawn"));
                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT,3.0f,4.0f);
                p.setGameMode(GameMode.SURVIVAL);
            }
        }.runTaskLater(main.getInstance(),60);

    }



}
