package eaglemc.Utils.others;

import eaglemc.pvp.main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class EmojiUtils {
    private static HashMap<String,String> allEmojis;

    public static void load() {
        allEmojis = new HashMap<>();
        File file = new File(main.getInstance().getDataFolder(), "emoji.yml");
        YamlConfiguration fc = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            main.getInstance().saveResource("emoji.yml",false);
        }
        ConfigurationSection emojisSection = fc.getConfigurationSection("allEmojis");
        if (emojisSection != null) {
            for (String key : emojisSection.getKeys(false)) {
                String emoticon = emojisSection.getString(key + ".emoticon");
                String emoji = emojisSection.getString(key + ".emoji").replace('&','§');
                allEmojis.put(emoticon, emoji);
            }
        }
    }

    public static String turnToEmoji(String message) {
        for (String emoticon : allEmojis.keySet()) {
            if (!message.toLowerCase().replace("§","").contains(emoticon.toLowerCase())) continue;
            message = main.color(message.replace(emoticon,allEmojis.get(emoticon)));
            return message;
        }
        return message;
    }
}
