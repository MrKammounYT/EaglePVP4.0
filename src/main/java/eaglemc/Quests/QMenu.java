package eaglemc.Quests;

import eaglemc.Managers.QuestManager;
import eaglemc.Utils.Quest;
import eaglemc.Utils.UPlayer;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class QMenu {



    public static void OpenInventory(Player p, UPlayer up, QuestManager questManager){
        Inventory inv = Bukkit.createInventory(null, 36, main.color("&6&l⚔ PvP &e&l4.0 &e&lQuests"));
        int i = 11;
        for(Quest quests : up.getDailyQuests()){
            inv.setItem(i,getDailyQuestItems(quests));
            i+=2;
        }
        p.openInventory(inv);
    }
    private static ItemStack getDailyQuestItems(Quest quest){
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color(quest.getQuestTitle()));
        ArrayList<String> lore = quest.getQuestDescription();
        lore.add("  ");
        if(quest.isCompleted()){
            lore.add(main.color("&eStatue: &aCompleted"));

        }else {
            lore.add(main.color("&eStatue: incomplete"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
