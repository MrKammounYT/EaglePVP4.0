package eaglemc.Utils.Holders;

import eaglemc.pvp.main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Kit {



    private final String Permission;
    private HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();

    private final String DisplayName;
    private final int slot;
    private final Material icon;

    private final boolean buyable;

    private final int price;


    private ArrayList<ItemStack> armor = new ArrayList<>();
    private ArrayList<ItemStack> contents = new ArrayList<>();


    public Kit(String Permission,HashMap<Integer,ItemStack> items,String displayName,int slot,Material icon,boolean buyable,int price) {
        this.items = items;
        this.Permission = Permission;
        this.DisplayName = displayName;
        this.slot = slot;
        this.icon = icon;
        this.buyable = buyable;
        this.price = price;
        loadKits();
    }
    public String getPermission() {   return Permission;
    }


    public ArrayList<ItemStack> getArmor() {
        return armor;
    }

    public ArrayList<ItemStack> getContents() {
        return contents;
    }

    private void loadKits(){
        for(Map.Entry<Integer,ItemStack> map : items.entrySet()){
            int slot = map.getKey();
            if(36<=slot && slot <=39){
                armor.add(map.getValue());
            }
            else {
                contents.add(map.getValue());
            }
        }
    }
    public int getPrice() {
        return price;
    }

    public boolean isBuyable() {
        return buyable;
    }

    public Material getIcon() {
        return icon;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public int getSlot() {
        return slot;
    }


    public ArrayList<ItemStack> getItems(){
        ArrayList<ItemStack> i  = new ArrayList<ItemStack>();
        i.addAll(items.values());
        return i;
    }




    public void wear(Player p){
        p.getInventory().clear();
        for(Map.Entry<Integer,ItemStack> map : items.entrySet()){
            int slot = map.getKey();
            p.getInventory().setItem(slot, map.getValue());
        }
    }





}
