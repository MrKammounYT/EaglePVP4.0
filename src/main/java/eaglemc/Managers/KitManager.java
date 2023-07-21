package eaglemc.Managers;

import eaglemc.Utils.Kit;
import eaglemc.pvp.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class KitManager {
    private final File KitsYml = new File(main.getInstance().getDataFolder().getAbsolutePath(),"Kits.yml");
    private final FileConfiguration config  = YamlConfiguration.loadConfiguration(KitsYml);

    public final HashMap<String , Kit> kits = new HashMap<>();


    public KitManager(){
        try {
            config.save(KitsYml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadKits();
    }

    public HashMap<String, Kit> getKits() {
        return kits;
    }

   /* public void addKit(String name, Inventory inv){
        HashMap<Integer,ItemStack> tk = new HashMap<>();
        for(int i=0;i<inv.getSize();i++){
            ItemStack is = inv.getItem(i);
            if(is!=null) {
                tk.put(i,is);
                cfg.set("Kits."+name+".Permission","");
                if(is.getType() == Material.AIR)return;
                cfg.set("Kits."+name+"."+i+".Material",is.getType().toString().toLowerCase());
                cfg.set("Kits."+name+"."+i+".Amount",is.getAmount());
                cfg.set("Kits."+name+"."+i+".data",is.getTypeId());
                cfg.set("Kits."+name+"."+i+".durability",is.getDurability());

                if(!is.hasItemMeta())continue;
                if(is.getItemMeta().hasDisplayName()){
                    cfg.set("Kits."+name+"."+i+".DisplayName",is.getItemMeta().getDisplayName());
                }
                if(is.getItemMeta().hasLore()){
                    cfg.set("Kits."+name+"."+i+".Lore",is.getItemMeta().getLore());
                }
                if (is.getItemMeta().hasEnchants()) {
                    Map<Enchantment, Integer> enchants = is.getEnchantments();
                    List<String> enchantList = new ArrayList<>();
                    for (Enchantment e : is.getEnchantments().keySet()) {
                        int level = enchants.get(e);
                        enchantList.add(e.getName().toLowerCase() + ":" + level);
                    }
                    cfg.set("Kits."+name+"."+i+".Enchants", enchantList);
                }else {
                    cfg.set("Kits."+name+"."+i+".Enchants", new ArrayList<>());
                }

            }
        }
        kits.put(name,new Kit(cfg.getString("Kits."+name+".Permission"),tk));
        try {
            cfg.save(KitsYml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Kit getKit(String name){
        return kits.get(name);
    }


    public void LoadKits(){
        if (cfg.getConfigurationSection("Kits") == null) {
            Bukkit.getConsoleSender().sendMessage("§cPlease Check Your Kits.yml file");
            return;
        }
        String slot;
        HashMap<Integer, ItemStack> content = null;
        for (String kitname : cfg.getConfigurationSection("Kits").getKeys(false)) {
            slot = "Kits." + kitname;
            content = new HashMap<>();
            for (String slt : cfg.getConfigurationSection(slot).getKeys(false)) {
                slot = "Kits." + kitname +"."+ slt + ".";
                if (cfg.getString(slot + "Material") == null) break;
                Material mt = Material.getMaterial(cfg.getString(slot + "Material").toUpperCase());
                if(mt == null)break;
                int amount = cfg.getInt(slot + "Amount");
                int data = cfg.getInt(slot + "data");
                int durability =  cfg.getInt(slot + "Durability");
                ItemStack itemStack = new ItemStack(mt, amount, (short) data);
                ItemMeta meta = itemStack.getItemMeta();
                if (durability != -1) {
                    meta.spigot().setUnbreakable(true);
                }
                String DisplayName = cfg.getString(slot + "DisplayName");
                if (DisplayName != null) {
                    meta.setDisplayName(main.color(DisplayName));
                }
                if (mt == Material.FLINT_AND_STEEL) {
                    meta.setDisplayName(main.color("&eCharge: &a1"));
                }
                List<String> enchants = cfg.getStringList(slot + "enchants");
                if (enchants != null) {
                    for (String s1 : enchants) {
                        String[] indiEnchants = s1.split(":");
                        meta.addEnchant(Enchantment.getByName(indiEnchants[0].toUpperCase()), Integer.parseInt(indiEnchants[1]), true);
                    }
                }
                List<String> lore = cfg.getStringList(slot + "lore");
                if (lore != null) {
                    for (int i = 0; i < lore.size(); i++) {
                        Collections.replaceAll(lore, lore.get(i), main.color(lore.get(i)));
                    }
                    meta.setLore(lore);
                }
                meta.getItemFlags().add(ItemFlag.HIDE_UNBREAKABLE);
                itemStack.setItemMeta(meta);


                content.put(Integer.parseInt(slt), itemStack);
            }
           // if(content.size() == 0)return;
            kits.put(kitname,new Kit(cfg.getString("Kits."+kitname+".Permission"),content));
            Bukkit.getConsoleSender().sendMessage(main.Prefix + "§eKit §a" + kitname + " §eHas Been Loaded! ");
        }
    }*/

    public void loadKits() {
        if (config.getConfigurationSection("Kits") == null) {
            Bukkit.getConsoleSender().sendMessage("§cPlease Check Your Kits.yml file");
            return;
        }
        String slot;
        HashMap<Integer, ItemStack> content = null;
        for (String kitname : config.getConfigurationSection("Kits").getKeys(false)) {
            slot = "Kits." + kitname;
            content = new HashMap<>();
            for (String slt : config.getConfigurationSection(slot + ".items").getKeys(false)) {
                slot = "Kits." + kitname + ".items." + slt + ".";
                //if (config.getString(slot + "type") == null) break;
                Material mt = Material.getMaterial(config.getString(slot + "type").toUpperCase());
                int amount = config.getInt(slot + "amount");
                int durability = config.getInt(slot + "durability");
                ItemStack itemStack = new ItemStack(mt, amount, (short) durability);
                ItemMeta meta = itemStack.getItemMeta();
                if (durability != -1) {
                    meta.spigot().setUnbreakable(true);
                }
                String displayname = config.getString(slot + "name");
                if (displayname != null) {
                    meta.setDisplayName(main.color(displayname));
                }
                if (mt == Material.FLINT_AND_STEEL) {
                    meta.setDisplayName(main.color("&6Charge: &e"));
                }
                List<String> enchants = config.getStringList(slot + "enchants");
                if (enchants != null) {
                    for (String s1 : enchants) {
                        String[] indiEnchants = s1.split(":");
                        meta.addEnchant(Enchantment.getByName(indiEnchants[0].toUpperCase()), Integer.parseInt(indiEnchants[1]), true);
                    }
                }
                List<String> lore = config.getStringList(slot + "lore");
                if (lore != null) {
                    for (int i = 0; i < lore.size(); i++) {
                        Collections.replaceAll(lore, lore.get(i), main.color(lore.get(i)));
                    }
                    meta.setLore(lore);
                }
                meta.getItemFlags().add(ItemFlag.HIDE_UNBREAKABLE);
                itemStack.setItemMeta(meta);


                content.put(Integer.parseInt(slt), itemStack);
            }
            if(content.isEmpty())return;
            kits.put(kitname, new Kit(config.getString("Kits." + kitname + ".permission"),content,config.getString("Kits." + kitname + ".displayname")
                    ,config.getInt("Kits."+kitname+".slot"),Material.getMaterial(config.getString("Kits."+kitname+".icon").toUpperCase())
            ,config.getBoolean("Kits."+kitname+".buyable"),config.getInt("Kits."+kitname+".price")));
            Bukkit.getConsoleSender().sendMessage(main.Prefix + "§eKit §a" + kitname + " §eHas Been Loaded with §a" +content.size());
        }

    }
    public void CreateKit(Player p, String Name) {
        ItemStack is;
        String slot ;
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            is = p.getInventory().getItem(i);
            slot = "Kits."+Name +".items."+ String.valueOf(i);
            config.set("Kits." + Name + ".permission","noperm");
            config.set("Kits." + Name + ".displayname",Name);
            config.set("Kits." + Name + ".icon",Material.IRON_CHESTPLATE.toString().toLowerCase());
            config.set("Kits." + Name + ".buyable",false);
            config.set("Kits." + Name + ".price",0);
            config.set("Kits." + Name + ".slot",1);

            if (is == null || is.getType() == Material.AIR)continue;
            config.set(slot + ".type", is.getType().toString().toLowerCase());
            config.set(slot + ".amount", is.getAmount());

            config.set(slot + ".data", is.getDurability());
            if (is.hasItemMeta()) {
                if (is.getItemMeta().hasDisplayName()) {
                    config.set(slot + ".name", is.getItemMeta().getDisplayName());
                }else {
                    config.set(slot + ".name", "");
                }
                if (is.getItemMeta().hasLore()) {
                    config.set(slot + ".lore", is.getItemMeta().getLore());
                }else {
                    List<String> s = new ArrayList<>();
                    config.set(slot+".lore", s);
                }
                if (is.getItemMeta().hasEnchants()) {
                    Map<Enchantment, Integer> enchants = is.getEnchantments();
                    List<String> enchantList = new ArrayList<String>();
                    for (Enchantment e : is.getEnchantments().keySet()) {
                        int level = enchants.get(e);
                        enchantList.add(e.getName().toLowerCase() + ":" + level);
                    }
                    config.set(slot + ".enchants", enchantList);
                }else {
                    List<String> s = new ArrayList<>();
                    config.set(slot + ".enchants", s);

                }
            }
        }
        for (int i = 36; i <40; i++) {
            is = p.getInventory().getItem(i);
            slot = "Kits."+Name +".items."+ String.valueOf(i);
            if (is == null || is.getType() == Material.AIR)
                continue;
            config.set(slot + ".type", is.getType().toString().toLowerCase());
            config.set(slot + ".amount", is.getAmount());

            config.set(slot + ".data", is.getDurability());
            if (is.hasItemMeta()) {
                if (is.getItemMeta().hasDisplayName()) {
                    config.set(slot + ".name", is.getItemMeta().getDisplayName());
                }else {
                    config.set(slot + ".name", "");
                }
                if (is.getItemMeta().hasLore()) {
                    config.set(slot + ".lore", is.getItemMeta().getLore());
                }else {
                    List<String> s = new ArrayList<>();
                    config.set(slot+".lore", s);
                }
                if (is.getItemMeta().hasEnchants()) {
                    Map<Enchantment, Integer> enchants = is.getEnchantments();
                    List<String> enchantList = new ArrayList<String>();
                    for (Enchantment e : is.getEnchantments().keySet()) {
                        int level = enchants.get(e);
                        enchantList.add(e.getName().toLowerCase() + ":" + level);
                    }
                    config.set(slot + ".enchants", enchantList);
                }else {
                    List<String> s = new ArrayList<>();
                    config.set(slot + ".enchants", s);

                }
            }
        }
        try {
            config.save(KitsYml);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}



