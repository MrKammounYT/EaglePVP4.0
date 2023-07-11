package eaglemc.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Heads {

    public static ItemStack getPlayerHead(String name){
        ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(name);
        item.setItemMeta(meta);
        return item;

    }



}
