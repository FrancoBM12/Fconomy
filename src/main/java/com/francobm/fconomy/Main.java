package com.francobm.fconomy;

import com.francobm.fconomy.API.CoinAPI;
import com.francobm.fconomy.commands.Command;
import com.francobm.fconomy.commands.MultiplierCommand;
import com.francobm.fconomy.database.MySQL;
import com.francobm.fconomy.files.FileCreator;
import com.francobm.fconomy.listeners.onJoinListener;
import com.francobm.fconomy.listeners.onLeaveListener;
import com.francobm.fconomy.packets.title.TitlePacket;
import com.francobm.fconomy.provider.VaultProvider;
import com.francobm.fconomy.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin {
    public String namePlugin = UtilsFC.ChatColor("&a[FConomy] ");
    private List<String> list_help;
    private FileCreator config;
    public static Main plugin;
    private Map<String, Integer> coins;
    private Map<String, Integer> multiplier;
    private boolean onlineMode;
    private TitlePacket titlePacket;
    private VaultProvider vaultProvider;
    @Override
    public void onEnable() {
        plugin = this;
        if(Bukkit.getPluginManager().getPlugin("Vault") != null){
            vaultProvider = new VaultProvider(this).setup();
        }
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new hookPlaceholderAPI(this).register();
        }
        registerFiles();
        MySQL.connect(this);
        MySQL.createTable(this);
        registerListeners();
        registerCommands();
        coins = new HashMap<>();
        multiplier = new HashMap<>();
        onlineMode = getConfig().getBoolean("online-mode");
        titlePacket = new TitlePacket();
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player != null){
                addPlayerMultiCoins(player);
            }
        }
        getServer().getConsoleSender().sendMessage(UtilsFC.ChatColor(namePlugin+"&aPlugin Activado"));
    }

    @Override
    public void onDisable() {
        if(Bukkit.getPluginManager().getPlugin("Vault") != null){
            vaultProvider.shutdown();
        }
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player != null){
                removePlayerMultiCoins(player);
            }
        }
        MySQL.disconnect(this);
        getServer().getConsoleSender().sendMessage(UtilsFC.ChatColor(namePlugin+"&cPlugin Desactivado"));
    }

    public void registerFiles() {
        config = new FileCreator(this, "config");
        this.list_help = config.getStringList("Messages.help");
    }

    public void registerCommands() {
        getCommand("coins").setExecutor(new Command(this));
        getCommand("multiplier").setExecutor(new MultiplierCommand(this));
    }
    public void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new onJoinListener(this), this);
        pm.registerEvents(new onLeaveListener(this), this);
    }

    public String getSounds(String path){
        if(config.getString("Sounds."+path) == null){
            return "&cERROR Sounds Path";
        }
        return config.getString("Sounds."+path);
    }

    public String getMessages(String path, boolean alive){
        if(alive) {
            if (config.getString("Messages." + path) == null) {
                return "&cERROR Messages Path";
            }
            return config.getString("Messages." + path);
        }
        if (config.getString(path) == null) {
            return "&cERROR Path";
        }
        return config.getString(path);
    }
    public void reload(){
        config.reload();
        list_help = config.getStringList("Messages.help");
        onlineMode = config.getBoolean("online-mode");
    }
    public List<String> getList_help() {
        return list_help;
    }

    @Override
    public FileCreator getConfig() {
        return config;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public Map<String, Integer> getPlayerCoins() {
        return coins;
    }

    public void addPlayerCoins(Player player){
        if(isOnlineMode()){
            int coins = CoinAPI.getCoinsDB(player);
            if(coins == -1) {
                CoinAPI.setCoinsDB(player, 0);
                this.coins.put(player.getUniqueId().toString(), 0);
            }else{
                this.coins.put(player.getUniqueId().toString(), coins);
            }
            return;
        }
        int coins = CoinAPI.getCoinsDB(player);
        if(coins == -1) {
            CoinAPI.setCoinsDB(player, 0);
            this.coins.put(player.getName(), 0);
        }else{
            this.coins.put(player.getName(), coins);
        }
    }

    public void replacePlayerCoins(Player player, int coins){
        if(isOnlineMode()){
            this.coins.replace(player.getUniqueId().toString(), coins);
            return;
        }
        this.coins.replace(player.getName(), coins);
    }

    public void removePlayerCoins(Player player){
        if(isOnlineMode()){
            Iterator<Map.Entry<String, Integer>> iterator = coins.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Integer> coin = iterator.next();
                if(coin.getKey().equalsIgnoreCase(player.getUniqueId().toString())){
                    CoinAPI.setCoinsDB(player, coins.get(player.getUniqueId().toString()));
                    iterator.remove();
                }
            }
            return;
        }
        Iterator<Map.Entry<String, Integer>> iterator = coins.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> coin = iterator.next();
            if(coin.getKey().equalsIgnoreCase(player.getName())){
                CoinAPI.setCoinsDB(player, coins.get(player.getName()));
                iterator.remove();
            }
        }
    }

    public Map<String, Integer> getPlayerMultiplier() {
        return multiplier;
    }

    public boolean containsMultiplier(Player player){
        if(isOnlineMode()){
            return multiplier.containsKey(player.getUniqueId().toString());
        }
        return multiplier.containsKey(player.getName());
    }

    public boolean containsCoins(Player player){
        if(isOnlineMode()){
            return coins.containsKey(player.getUniqueId().toString());
        }
        return coins.containsKey(player.getName());
    }

    public void addPlayerMultiCoins(Player player){
        if(isOnlineMode()){
            if(containsMultiplier(player) && containsCoins(player)){
                int multiplier = CoinAPI.getMultiplierDB(player);
                int coins = CoinAPI.getCoinsDB(player);
                if(coins != -1){
                    replacePlayerCoins(player, coins);
                }
                if(multiplier != -1){
                    replacePlayerMultiplier(player, multiplier);
                }
                return;
            }
            int multiplier = CoinAPI.getMultiplierDB(player);
            int coins = CoinAPI.getCoinsDB(player);
            if(coins == -1 && multiplier == -1){
                CoinAPI.setMultiCoinsDB(player, 0, 1);
                this.coins.put(player.getUniqueId().toString(), 0);
                this.multiplier.put(player.getUniqueId().toString(), 1);
            }else{
                this.coins.put(player.getUniqueId().toString(), coins);
                this.multiplier.put(player.getUniqueId().toString(), multiplier);
            }
            if(multiplier == -1){
                CoinAPI.setMultiplierDB(player, 1);
                replacePlayerMultiplier(player, 1);
            }
            return;
        }
        if(containsMultiplier(player) && containsCoins(player)){
            int multiplier = CoinAPI.getMultiplierDB(player);
            int coins = CoinAPI.getCoinsDB(player);
            if(coins != -1){
                replacePlayerCoins(player, coins);
            }
            if(multiplier != -1){
                replacePlayerMultiplier(player, multiplier);
            }
            return;
        }
        int multiplier = CoinAPI.getMultiplierDB(player);
        int coins = CoinAPI.getCoinsDB(player);
        if(coins == -1 && multiplier == -1){
            CoinAPI.setMultiCoinsDB(player, 0, 1);
            this.coins.put(player.getName(), 0);
            this.multiplier.put(player.getName(), 1);
        }else{
            this.coins.put(player.getName(), coins);
            this.multiplier.put(player.getName(), multiplier);
        }
        if(multiplier == -1){
            CoinAPI.setMultiplierDB(player, 1);
            replacePlayerMultiplier(player, 1);
        }
    }

    public void addPlayerMultiplier(Player player){
        if(isOnlineMode()){
            int multiplier = CoinAPI.getMultiplierDB(player);
            if(multiplier == -1) {
                CoinAPI.setMultiplierDB(player, 1);
                this.multiplier.put(player.getUniqueId().toString(), 1);
            }else {
                this.multiplier.put(player.getUniqueId().toString(), multiplier);
            }
            return;
        }
        int multiplier = CoinAPI.getMultiplierDB(player);
        if(multiplier == -1) {
            CoinAPI.setMultiplierDB(player, 1);
            this.multiplier.put(player.getName(), 1);
        }else {
            this.multiplier.put(player.getName(), multiplier);
        }
    }

    public void replacePlayerMultiplier(Player player, int multiplier){
        if(isOnlineMode()){
            this.multiplier.replace(player.getUniqueId().toString(), multiplier);
            return;
        }
        this.multiplier.replace(player.getName(), multiplier);
    }

    public void removePlayerMultiCoins(Player player){
        if(isOnlineMode()){
            Iterator<Map.Entry<String, Integer>> iterator = multiplier.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Integer> multiplier = iterator.next();
                if(multiplier.getKey().equalsIgnoreCase(player.getUniqueId().toString())){
                    CoinAPI.setMultiCoinsDB(player, this.coins.get(player.getUniqueId().toString()), this.multiplier.get(player.getUniqueId().toString()));
                    iterator.remove();
                }
            }
            return;
        }
        Iterator<Map.Entry<String, Integer>> iterator = multiplier.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> multiplier = iterator.next();
            if(multiplier.getKey().equalsIgnoreCase(player.getName())){
                CoinAPI.setMultiCoinsDB(player, this.coins.get(player.getName()), this.multiplier.get(player.getName()));
                iterator.remove();
            }
        }
    }

    public void removePlayerMultiplier(Player player){
        if(isOnlineMode()){
            Iterator<Map.Entry<String, Integer>> iterator = multiplier.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Integer> multiplier = iterator.next();
                if(multiplier.getKey().equalsIgnoreCase(player.getUniqueId().toString())){
                    CoinAPI.setMultiplierDB(player, this.multiplier.get(player.getUniqueId().toString()));
                    iterator.remove();
                }
            }
            return;
        }
        Iterator<Map.Entry<String, Integer>> iterator = multiplier.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> multiplier = iterator.next();
            if(multiplier.getKey().equalsIgnoreCase(player.getName())){
                CoinAPI.setMultiplierDB(player, this.multiplier.get(player.getName()));
                iterator.remove();
            }
        }
    }

    public String getCoins(Player player){
        if(CoinAPI.getCoins(player) == -1){
            return "Cargando...";
        }
        return String.valueOf(CoinAPI.getCoins(player));
    }

    public String getMultiplier(Player player){
        if(CoinAPI.getMultiplier(player) == -1){
            return "Cargando...";
        }
        return String.valueOf(CoinAPI.getMultiplier(player));
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    public TitlePacket getTitlePacket() {
        return titlePacket;
    }
}
