package eaglemc.Utils.Holders;

import eaglemc.Utils.enums.TQuests;

import java.util.ArrayList;
import java.util.HashMap;

public class Quest {


    private final int questID;
    private final TQuests questType;
    private final String questTitle;
    private final ArrayList<String> questDescription;
    private final HashMap<String,Integer> questRewards;
    private final  HashMap<String,Integer> requirements;
    private boolean isCompleted;

    // Constructor
    public Quest(int questID, TQuests questType, String questTitle, ArrayList<String> questDescription, HashMap<String,Integer> questRewards,HashMap<String,Integer> requirements) {
        this.questID = questID;
        this.questType = questType;
        this.questTitle = questTitle;
        this.questDescription = questDescription;
        this.questRewards = questRewards;
        this.isCompleted = false;
        this.requirements = requirements;
    }

    public int getQuestID() {
        return questID;
    }


    public boolean checkQuest(UPlayer up){
        for(String requirement : requirements.keySet()){
            if(requirement.equals("kills"))return requirements.get(requirement) >= up.getDailyKills();
            else if(requirement.equals("deaths"))return requirements.get(requirement) >= up.getDailyDeaths();
            else if(requirement.equals("points"))return requirements.get(requirement) >= up.getDailyPoints();
            else if(requirement.equals("coins"))return requirements.get(requirement) >= up.getDailyCoins();
            else if(requirement.equals("xp"))return requirements.get(requirement) >= up.getDailyXP();
        }
        return false;
    }
    public TQuests getQuestType() {
        return questType;
    }

    public HashMap<String, Integer> getRequirements() {
        return requirements;
    }

    public String getQuestTitle() {
        return questTitle;
    }

    public ArrayList<String> getQuestDescription() {
        return questDescription;
    }

    public HashMap<String,Integer> getQuestRewards() {
        return questRewards;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
