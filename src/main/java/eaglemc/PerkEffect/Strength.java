package eaglemc.PerkEffect;

import eaglemc.Managers.PlayerManager;
import eaglemc.enums.Perks;
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

public class Strength implements Listener {

    private PlayerManager pm;
    public Strength(PlayerManager pm){
        this.pm = pm;
    }

    @EventHandler
    public void onStrengthActivate(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getPlayer().getItemInHand().getType()  == Material.REDSTONE){
                Player p = e.getPlayer();
                if(!p.getItemInHand().hasItemMeta())return;
                if(p.getItemInHand().getItemMeta().getDisplayName().equals(main.color(Perks.STRENGTH.getPerkName().replace(main.color("Perk"),"")))){
                    p.playSound(p.getLocation(), Sound.EAT,3.0f,1.2f);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,120,0));
                    if(p.getItemInHand().getAmount()<= 1){
                        e.getPlayer().getInventory().remove(Perks.STRENGTH.getPerkUsableItem());
                    }else{
                        p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
                    }
                }
            }
        }
    }


}
