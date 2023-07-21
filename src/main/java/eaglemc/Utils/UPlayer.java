package eaglemc.Utils;

import eaglemc.Perks;
import eaglemc.Trails;
import eaglemc.pvp.main;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UPlayer {



    private FastBoard ScoreBoard;
    private int kills;
    private int deaths;

    private int points;

    private boolean build;


    private int KillStreaks;

    private Location deathLocation;

    private boolean HasGoldenHead = false;

    private int PerkToPurchase;
    private int TrailToPurchase;

    private final HashMap<Integer, Perks> slots;

    private final ArrayList<Integer> Perks;

    private int SelectedSlot = 0;
    private int exp;

    private final ArrayList<Quest> DailyQuests = new ArrayList<Quest>();
    private int level;
    private int coins;

    private ArrayList<Trails> Trails ;
    private final Uassist uassist = new Uassist();

    private Kit kit;

    private Player p;

    private boolean Build;

    private int dailyKills = 0;
    private int dailyPoints = 0;
    private int dailyDeaths = 0;

    private int dailyCoins = 0;

    private int dailyXP = 0;


    private Trails SelectedTrail;

    private final String rankColor;

    private int flint = 2;





    public UPlayer(UUID uuid, int kills, int deaths, int points, int coins, int exp, int level, String rankColor, ArrayList<Integer> perks, HashMap<Integer, Perks> slots,
                   ArrayList<Trails> trails, Trails SelectedTrail){
        this.kills = kills;
        this.deaths= deaths;
        this.points = points;
        this.coins = coins;
        this.exp = exp;
        this.level = level;
        this.p = Bukkit.getPlayer(uuid);
        this.rankColor = rankColor;
        this.Perks = perks;
        this.slots = slots;
        this.SelectedTrail = SelectedTrail;
        this.Trails = trails;
    }

    public void addDailyQuest(Quest quest){
        DailyQuests.add(quest);
    }

    public ArrayList<Quest> getDailyQuests() {
        return DailyQuests;
    }

    public eaglemc.Trails getSelectedTrail() {
        return SelectedTrail;
    }
    public void setSelectedTrail(Trails trail){
        this.SelectedTrail = trail;
    }

    public void setTrailToPurchase(int trailToPurchase) {
        TrailToPurchase = trailToPurchase;
    }


    public int getFlint() {
        return flint;
    }

    public void addflint(int flint) {
        this.flint +=flint;
        updateFlint(p.getItemInHand());

    }
    public  void removeflint(int flint,Player p) {
        this.flint -=flint;
        updateFlint(p.getItemInHand());

    }

    public int getDailyCoins() {
        return dailyCoins;
    }

    public int getDailyDeaths() {
        return dailyDeaths;
    }

    public int getDailyKills() {
        return dailyKills;
    }

    public int getDailyPoints() {
        return dailyPoints;
    }

    public int getDailyXP() {
        return dailyXP;
    }

    private ItemStack updateFlint(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&6Charge: &e" + flint));
        item.setItemMeta(meta);
        return  item;
    }

    public int getTrailToPurchase() {
        return TrailToPurchase;
    }

    public ArrayList<eaglemc.Trails> getTrails() {
        return Trails;
    }

    public void setSelectedSlot(int selectedSlot) {
        SelectedSlot = selectedSlot;
    }

    public int getSelectedSlot() {
        return SelectedSlot;
    }

    public String getRankColor() {
        return rankColor;
    }


    public boolean HasGoldenHead() {
        return HasGoldenHead;
    }

    public void setGoldenHead(boolean hasGoldenHead) {
        HasGoldenHead = hasGoldenHead;
    }

    public String getCustomName(){
        return main.color(getStringLevel() + " "+getRankColor()+p.getName());
    }

    public ArrayList<Integer> getPlayerPerks(){
        return Perks;
    }
    public Perks getPerkInSlot(int slot){
        return slots.get(slot);
    }
    public HashMap<Integer,Perks> getSlots(){
        return slots;
    }

    public boolean isInBuild() {
        return build;
    }


    public int getKillStreaks() {
        return KillStreaks;
    }

    public void addKillStreak(){
        KillStreaks++;
    }
    public void clearKillStreaks(){
        KillStreaks = 0;
    }
    public void setBuilding(boolean build){
        this.build = build;
    }

    public void setScoreBoard(FastBoard scoreBoard) {
        ScoreBoard = scoreBoard;
    }

    public FastBoard getScoreBoard() {
        return ScoreBoard;
    }

    public Player getPlayer() {
        return p;
    }

    public Uassist getUassist() {
        return uassist;
    }

    public Kit getKit() {
        return kit;
    }
    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public void setDeathLocation(Location deathLocation) {
        this.deathLocation = deathLocation;
    }

    public Location getDeathLocation() {
        return deathLocation;
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

    public int getXPToLevelUp(){
        return getRequiredXP() - exp;
    }
    public int getRequiredXP(){
        return ((level+1) *10)/3;
    }
    public void checkEXP(Player p){
        int x = getRequiredXP();
        boolean levelup = false;
        while ((x - exp) <=0){
            x = getRequiredXP();
            if(exp - x > 0){
                exp = exp - x;
            }
            level+=1;
            levelup = true;
            Title.sendTitle(p, main.color("&e&lLEVEL UP!"),main.color("&7["+getLevelColor()+(level-1)+"&7]" +" &7-> " + getStringLevel()),40);
        }
        if(!levelup)return;
        p.playSound(p.getLocation(), Sound.LEVEL_UP,3.0f,1.8f);

    }

    public void setPerkToPurchase(int perkToPurchase) {
        PerkToPurchase = perkToPurchase;
    }

    public int getPerkToPurchase() {
        return PerkToPurchase;
    }

    public int getPoints() {
        return points;
    }
    public void addexp(Player p,int amount) {
        exp += amount;
        dailyXP+=amount;
        checkEXP(p);
    }
    public void removeCoins(int amount){
        coins -=amount;
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
        dailyCoins+=coins;
    }
    public void addPoints(int Points){
        this.points += Points;
        dailyPoints+=Points;
    }
    public void addKills(int kills){
        this.kills +=kills;
        dailyKills +=kills;
    }
    public void addDeaths(int deaths){
        this.deaths +=deaths;
        dailyDeaths += deaths;
    }

    public void wearKit(Player p){
        if(kit!= null){
            kit.wear(p);
        }
    }

    public void removePoints(int points){
        if(this.points <=0)return;
        this.points -= points;
    }

    public void setexp(Player p, int amount) {
        exp = amount;
        checkEXP(p);
    }

    public void setlevel(int amount) {
        level = amount;
    }
}
