package eaglemc.Utils.Holders;

import eaglemc.Utils.ScoreBoard;
import eaglemc.enums.DeathCry;
import eaglemc.GameManager.GameManager;
import eaglemc.enums.Perks;
import eaglemc.enums.Trails;
import eaglemc.Utils.others.LocationAPI;
import eaglemc.Utils.Title;
import eaglemc.pvp.main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import xyz.refinedev.phoenix.Phoenix;
import xyz.refinedev.phoenix.SharedAPI;
import xyz.refinedev.phoenix.profile.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UPlayer {



    private int kills;
    private int deaths;

    private int points;

    private boolean build;


    private int KillStreaks;

    private Location deathLocation;

    private boolean HasGoldenHead = false;


    private final HashMap<Integer, Perks> slots;

    private final ArrayList<Perks> Perks;

    private int SelectedSlot = 0;
    private int exp;

    private final ArrayList<Quest> DailyQuests = new ArrayList<Quest>();
    private int level;
    private int coins;

    private ArrayList<Trails> Trails ;

    private ArrayList<DeathCry> DeathCries;
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


    private  DeathCry SelectedDeathCry;

    private int flint;





    public UPlayer(Player p, GameManager manager, UUID uuid, int kills, int deaths, int points, int coins, int exp, int level, ArrayList<Perks> perks, HashMap<Integer, Perks> slots,
                   ArrayList<Trails> trails, Trails SelectedTrail, ArrayList<DeathCry> DC, DeathCry selectedDC){
        this.kills = kills;
        this.deaths= deaths;
        this.points = points;
        this.coins = coins;
        this.exp = exp;
        this.level = level;
        this.p = Bukkit.getPlayer(uuid);
        this.Perks = perks;
        this.slots = slots;
        this.SelectedTrail = SelectedTrail;
        this.Trails = trails;
        this.SelectedDeathCry = selectedDC;
        this.DeathCries = DC;
        flint = 1;
        for(Kit kits: manager.getKitManager().getKits().values()){
            if(kits.getPermission().equals("noperm")){
                setKit(kits);
                continue;
            }
            if(p.hasPermission(kits.getPermission())){
                setKit(kits);
            }
        }
                p.teleport(LocationAPI.getLocation("spawn"));
                p.setHealth(20);
                p.setFoodLevel(20);
                eaglemc.Utils.ScoreBoard.create(p,UPlayer.this);
                for(PotionEffect effects : p.getActivePotionEffects()){
                    p.removePotionEffect(effects.getType());
                }
                wearKit(p);

    }

    public void setSelectedDeathCry(DeathCry selectedDeathCry) {
        SelectedDeathCry = selectedDeathCry;
    }

    public DeathCry getSelectedDeathCry() {
        return SelectedDeathCry;
    }

    public ArrayList<DeathCry> getDeathCries() {
        return DeathCries;
    }

    public void addDailyQuest(Quest quest){
        DailyQuests.add(quest);
    }

    public ArrayList<Quest> getDailyQuests() {
        return DailyQuests;
    }

    public eaglemc.enums.Trails getSelectedTrail() {
        return SelectedTrail;
    }
    public void setSelectedTrail(Trails trail){
        this.SelectedTrail = trail;
    }


    public int getFlint() {
        return flint;
    }

    public void setFlint(int flint) {
        this.flint = flint;
    }

    public boolean canUseFlint(){
        return flint > 0;
    }

    public void addFlint(Player p) {
        this.flint +=1;
        updateFlint(p);


    }
    public  void removeFlint(Player p) {
        this.flint -=1;
        updateFlint(p);
    }
    private void updateFlint(Player p){
        int slot = getFlintSlot(p);
        if(slot == -1)return;
        ItemStack item = p.getInventory().getItem(slot);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(main.color("&6Charge: &e" + flint));
        item.setItemMeta(meta);
        p.getInventory().setItem(slot, item);
    }

    private int getFlintSlot(Player p){
        for(int i=0;i<p.getInventory().getSize();i++){
            if(p.getInventory().getItem(i) ==null)continue;
            if(p.getInventory().getItem(i).getType() == Material.FLINT_AND_STEEL){
                return i;
            }
        }
        return -1;
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



    public ArrayList<eaglemc.enums.Trails> getTrails() {
        return Trails;
    }

    public void setSelectedSlot(int selectedSlot) {
        SelectedSlot = selectedSlot;
    }

    public int getSelectedSlot() {
        return SelectedSlot;
    }

    public String getRankColor(Player p) {
        return SharedAPI.getInstance().getProfileHandler().getProfile(p.getUniqueId()).getNameColor();
    }



    public String getColoredName(Player p){
        return main.color(getRankColor(p) + p .getName());
    }
    public String getCustomName(Player p){
        return main.color(getStringLevel() + " "+getRankColor(p) + p.getName());

    }

    public boolean HasGoldenHead() {
        return HasGoldenHead;
    }

    public void setGoldenHead(boolean hasGoldenHead) {
        HasGoldenHead = hasGoldenHead;
    }



    public ArrayList<Perks> getPlayerPerks(){
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
        ScoreBoard.create(p,this);

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
