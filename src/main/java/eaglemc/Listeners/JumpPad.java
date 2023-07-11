package eaglemc.Listeners;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.PacketAdapter;
import eaglemc.pvp.main;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class JumpPad implements Listener {
    private final Random random = new Random();


    @EventHandler
    public void JumpPadLaunch(PlayerMoveEvent e){

        if (e.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType() == Material.GOLD_BLOCK) {
            launchEnderPearl(e.getPlayer());
        }
    }

    public void PacketRecive(){
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(main.getInstance(), PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving (com.comphenix.protocol.events.PacketEvent e) {
                if(e.getPacketType() == PacketType.Play.Client.STEER_VEHICLE) {
                    if(e.getPacket().getHandle() instanceof PacketPlayInSteerVehicle) {
                        Field f = null;
                        try {
                            f = PacketPlayInSteerVehicle.class.getDeclaredField("d");
                            f.setAccessible(true);
                            f.set(e.getPacket().getHandle(), false);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onPacketSending (com.comphenix.protocol.events.PacketEvent e) {
                e.setCancelled(true);
            }
        });
    }

    private void launchEnderPearl(Player player) {
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS,5.0f,3.0f);
        Location location = player.getLocation();
        Vector direction = player.getEyeLocation().getDirection();
        Vector velocity = new Vector(direction.getX(), 1, direction.getZ()).normalize().multiply(1);
        EnderPearl enderPearl = player.launchProjectile(EnderPearl.class);
        enderPearl.setPassenger(player);
        enderPearl.setVelocity(velocity);



    }
}
