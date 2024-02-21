package eaglemc.Managers;

import eaglemc.Utils.enums.TQuests;
import eaglemc.Utils.Holders.Quest;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestManager {

    private final File questFile;
    private final FileConfiguration config;

    private final HashMap<Integer,Quest> DailyQuestList = new HashMap<Integer,Quest>();
    public QuestManager(main main){
        questFile = new File(main.getDataFolder().getAbsolutePath(),"Quests.yml");
        config = YamlConfiguration.loadConfiguration(questFile);
        if (!questFile.exists()) {
            main.saveResource("Quests.yml",false);
        }
        LoadQuests();
    }




    private void LoadQuests(){
        for(String quests: config.getConfigurationSection("Daily").getKeys(false)){
            String name = config.getString("Daily."+quests+".name");
            HashMap<String,Integer> requirements = new HashMap<>();
            HashMap<String,Integer> rewards = new HashMap<>();
            for(String map: config.getConfigurationSection("Daily."+quests+".requirements").getKeys(false)){
                if(config.getInt("Daily."+quests+".requirements."+map) <= 0)continue;
                requirements.put(config.getString("Daily."+quests+".requirements."+map),config.getInt("Daily."+quests+".requirements."+map));
            }
            for(String map: config.getConfigurationSection("Daily."+quests+".rewards").getKeys(false)){
                if(config.getInt("Daily."+quests+".rewards."+map) <= 0)continue;
                requirements.put(config.getString("Daily."+quests+".rewards."+map),config.getInt("Daily."+quests+".rewards."+map));
            }
            ArrayList<String> description = new ArrayList<String>();
            for(String map: config.getStringList("Daily."+quests+".description")){
                description.add(main.color(map));
            }

            DailyQuestList.put(Integer.parseInt(quests),new Quest(Integer.parseInt(quests), TQuests.daily,name,description,rewards,requirements));
        }
        Bukkit.getConsoleSender().sendMessage(main.Prefix + "§eLoaded §a"+DailyQuestList.size()+ " Daily Quests");
    }

    public HashMap<Integer, Quest> getDailyQuestList() {
        return DailyQuestList;
    }


}
