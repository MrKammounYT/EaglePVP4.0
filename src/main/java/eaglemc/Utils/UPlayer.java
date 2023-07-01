package eaglemc.Utils;

import eaglemc.pvp.main;
import org.bukkit.entity.Player;

public class UPlayer {


    private int kills;
    private int deaths;

    private int points;

    private boolean build;

    private int exp;
    private int level;
    private int coins;
    private final Uassist uassist = new Uassist();

    private Kit kit;



    public UPlayer(Kit kit,int kills,int deaths,int points,int coins,int exp,int level){
        this.kills = kills;
        this.deaths= deaths;
        this.points = points;
        this.coins = coins;
        this.exp = exp;
        this.level = level;
        this.kit = kit;
    }


    public Uassist getUassist() {
        return uassist;
    }

    public Kit getKit() {
        return kit;
    }
    public void setKit(Kit kit) {
        kit = kit;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public String getStringLevel(){
        return main.color("&7["+getLevelColor()+level+"&7]");
    }
    String getLevelColor(){
        if(level <= 10)return "&8";
        else if(level <= 20)return "&7";
        else if(level <= 30)return "&f";
        else if(level <= 40)return "&b";
        else if(level <= 50)return "&3";
        else if(level <= 60)return "&9";
        else if(level <= 70)return "&a";
        else if(level <= 80)return "&2";
        else if(level <= 90)return "&e";
        else if(level <= 100)return "&6";
        else if(level <= 120)return "&c";
        else if(level <= 130)return "&4";
        else if(level <= 140)return "&4&l";
        else return "&4&l&n";
    }

    public int getRequiredXP(){
        int xplevel = (level *10)/3;
        return xplevel - exp;
    }
    public void checkEXP(Player p){
        if(getRequiredXP() <= 0){
            exp = 0;
            level+=1;
            Title.sendTitle(p, main.color("&f&lLEVEL UP!"),main.color("&7["+getLevelColor()+(level-1)+"&7]" +" &7-> " + getStringLevel()),40);
        }
    }
    public int getPoints() {
        return points;
    }
    public void addexp(Player p,int amount) {
        exp += amount;
        checkEXP(p);
    }

    public int getCoins() {
        return coins;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addCoins(int coins){
        this.coins +=coins;
    }
    public void addPoints(int Points){
        this.points += Points;
    }
    public void addKills(int kills){
        this.kills +=kills;
    }
    public void addDeaths(int deaths){
        this.deaths +=deaths;
    }


    public void removePoints(int points){
        this.points -= points;
    }
    public boolean isInBuilding() {
        return build;
    }
}
