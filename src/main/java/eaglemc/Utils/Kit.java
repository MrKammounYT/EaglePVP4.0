package eaglemc.Utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;


public class Kit {



    private final String Permission;
    private HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();


    public Kit(String Permission,HashMap<Integer,ItemStack> items){
        this.items = items;
        this.Permission = Permission;

    }
    public String getPermission() {   return Permission;
    }
    
    

    public void wear(Player p){
        p.getInventory().clear();
        for(Map.Entry<Integer,ItemStack> map : items.entrySet()){
            p.getInventory().setItem(map.getKey(), map.getValue());
        }
    }





}
