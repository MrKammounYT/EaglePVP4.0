package eaglemc.Utils.others;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import eaglemc.pvp.main;

import java.io.File;
import java.io.IOException;


public class LocationAPI {
   private final static File file = new File(main.getInstance().getDataFolder().getAbsolutePath(), "Locations.yml");

    private final static FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

    public static void setLocation(Location loc, String name) {
        String world = loc.getWorld().getName();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        double yaw = loc.getYaw();
        double pitch = loc.getPitch();
        cfg.set(name + ".world", world);
        cfg.set(name+ ".x", x);
        cfg.set(name+ ".y", y);
        cfg.set(name + ".z", z);
        cfg.set(name + ".yaw", yaw);
        cfg.set(name + ".pitch", pitch);
        try {
            cfg.save(file);
        } catch (IOException e) {
            System.err.println("&aBackToSpawn");
            e.printStackTrace();
        }
    }

    public static Location getLocation(String name) {
        String world = cfg.getString(String.valueOf(name) + ".world");
        double x = cfg.getDouble(String.valueOf(name) + ".x");
        double y = cfg.getDouble(String.valueOf(name) + ".y");
        double z = cfg.getDouble(String.valueOf(name) + ".z");
        double yaw = cfg.getDouble(String.valueOf(name) + ".yaw");
        double pitch = cfg.getDouble(String.valueOf(name) + ".pitch");
        Location loc = new Location(Bukkit.getWorld(world), x, y, z);
        loc.setYaw((float)yaw);
        loc.setPitch((float)pitch);
        return loc;

    }
}
