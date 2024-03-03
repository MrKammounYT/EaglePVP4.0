package eaglemc.Utils.Holders;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

public class SavedKit {


    private  final ItemStack[] armor;
    private  final ItemStack[] contents;

    private final Kit PlayerKit;

    public SavedKit(Kit kit,ItemStack[] armor , ItemStack[] contents) {
        this.armor =armor;
        this.contents = contents;
        this.PlayerKit = kit;
    }


    public ItemStack[] getArmor() {
        return armor;
    }



    public ItemStack[] getContents() {
        return contents;
    }

    public void Wear(Player p){
        p.getInventory().clear();
        p.getInventory().setArmorContents(armor);
        p.getInventory().setContents(contents);
    }
}
