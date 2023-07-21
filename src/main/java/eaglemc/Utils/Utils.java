package eaglemc.Utils;

import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Utils {

    public static final   HashMap<UUID, BukkitTask> intp = new HashMap<>();

    public static  final ArrayList<Villager> shop = new ArrayList<>();
    public static  final ArrayList<Villager> Quest = new ArrayList<>();

}
