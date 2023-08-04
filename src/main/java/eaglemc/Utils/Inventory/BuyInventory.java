package eaglemc.Utils.Inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class BuyInventory implements InventoryHolder {


    private Object object;

    @Override
    public Inventory getInventory() {
        return null;
    }

    public BuyInventory(Object object){
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
