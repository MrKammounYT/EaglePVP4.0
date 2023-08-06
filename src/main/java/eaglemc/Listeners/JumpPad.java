package eaglemc.Listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;


import java.util.Random;

public class JumpPad implements Listener {
    private final Random random = new Random();


    @EventHandler
    public void JumpPadLaunch(PlayerMoveEvent e){
        if (e.getTo().getBlock().getType() == Material.GOLD_PLATE) {
            if(e.getPlayer().getGameMode() != GameMode.SURVIVAL)return;
            Player p = e.getPlayer();
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS,5.0f,3.0f);
            Vector v = p.getLocation().getDirection().clone().normalize();
            v.multiply(2);
            v.setY(0.8D);
            v.setX(-5);
            p.setVelocity(v);
        }
    }






}
