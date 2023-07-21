package eaglemc.TrailsEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.Trails;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class PlayEffect implements Listener {
    HashMap<Arrow , BukkitTask>  animation = new HashMap<>();

    private PlayerManager pm;

    public PlayEffect(PlayerManager pm){
        this.pm = pm;
    }

    @EventHandler
    public void OnArrowLaunch(ProjectileLaunchEvent e){
        if(e.getEntity() instanceof Arrow){
            if(e.getEntity().getShooter() instanceof Player){
                Player p = (Player)e.getEntity().getShooter();
                Trails trail = pm.getPlayer(p).getSelectedTrail();
                if(trail.getParticleEffect() == null)return;
                Arrow arrow = (Arrow) e.getEntity();
                    animation.put(arrow, new BukkitRunnable() {
                        @Override
                        public void run() {
                            particle(arrow.getLocation(),trail.getParticleEffect());
                        }
                    }.runTaskTimer(main.getInstance(),0,5L));

            }
        }
    }

    @EventHandler
    public void onProjectHitEvent(ProjectileHitEvent e){
        if(e.getEntity() instanceof  Arrow){
            if(e.getEntity().getShooter() instanceof  Player){
                if(animation.containsKey((Arrow) e.getEntity())){
                    animation.get((Arrow) e.getEntity()).cancel();
                    animation.remove((Arrow) e.getEntity());
                }
            }
        }
    }


    private void particle(Location loc, EnumParticle e) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(e, true, (float) loc.getX(),
                (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 15, null);
        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

}
