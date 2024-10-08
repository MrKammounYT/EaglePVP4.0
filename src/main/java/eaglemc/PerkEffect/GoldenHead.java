package eaglemc.PerkEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.Utils.enums.Perks;
import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GoldenHead implements Listener {


    private final PlayerManager pm;


    public GoldenHead(PlayerManager pm) {
        this.pm = pm;
    }


    @EventHandler
    public void onGoldenHeadConsume(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getPlayer().getItemInHand().getType()  == Material.SKULL_ITEM){
                Player p = e.getPlayer();
                if(!p.getItemInHand().hasItemMeta())return;
                if(p.getItemInHand().getItemMeta().getDisplayName().equals(main.color("&6Golden Head "))){
                    p.playSound(p.getLocation(), Sound.EAT,3.0f,1.2f);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,120,0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,40,3));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,120,0));
                    p.setItemInHand(null);
                }
            }
        }
    }

}
