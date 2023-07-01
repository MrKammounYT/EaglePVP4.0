package eaglemc.Utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {

    public static void sendTitle(Player player, String msgTitle, String msgSubTitle, int ticks) {
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', msgTitle)  + "\"}");
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', msgSubTitle) + "\"}");
        PacketPlayOutTitle p = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle p2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet<?>)p);
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet<?>)p2);
        sendTime(player, ticks);
    }

    private static void sendTime(Player player, int ticks) {
        PacketPlayOutTitle p = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, ticks, 20);
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet<?>)p);
    }

    public static void sendActionBar(Player player, String message) {
        try {
            IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
            PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet<?>)ppoc);

        }catch (Exception q){
            q.printStackTrace();
        }
    }


}
