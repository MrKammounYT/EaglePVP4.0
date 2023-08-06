package eaglemc;

import eaglemc.GameManager.GameManager;
import eaglemc.Utils.Holders.UPlayer;
import org.bukkit.entity.Player;

public class PvPCoreAPI {



    private final GameManager manager;


    public PvPCoreAPI(GameManager manager) {
        this.manager = manager;
    }
    public UPlayer getPvPPlayer(Player p){
        return manager.getPlayerManager().getPlayer(p);
    }

    public GameManager getPvPManager(){
        return manager;
    }
}
