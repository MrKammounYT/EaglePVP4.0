package eaglemc.Utils.Inventory;

import eaglemc.enums.Type;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SelectionInventory implements InventoryHolder {


    @Override
    public Inventory getInventory() {
        return null;
    }

    private final Type  invType;

    public SelectionInventory(Type invType){
        this.invType = invType;
    }

    public Type getInvType() {
        return invType;
    }
}
