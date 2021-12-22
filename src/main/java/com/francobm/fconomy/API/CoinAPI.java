package com.francobm.fconomy.API;

import com.francobm.fconomy.Main;
import com.francobm.fconomy.database.MySQL;
import com.francobm.fconomy.files.FileCreator;
import com.francobm.fconomy.packets.title.TitlePacket;
import com.francobm.fconomy.utils.UtilsFC;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CoinAPI {

    public static int getCoinsDB(Player player){
        Main plugin = Main.getPlugin();
        FileCreator config = plugin.getConfig();
        boolean enabled = config.getBoolean("MySQL.enabled");
        if(plugin.isOnlineMode()){
            if(enabled) {
                try {
                    String table = config.getString("MySQL.table");
                    PreparedStatement st = MySQL.con.prepareStatement("SELECT * FROM " + table + " WHERE UUID = ?");
                    st.setString(1, player.getUniqueId().toString());
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        return rs.getInt("coins");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else{
                try {
                    PreparedStatement st = MySQL.con.prepareStatement("SELECT * FROM coinTable WHERE UUID = ?");
                    st.setString(1, player.getUniqueId().toString());
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        return rs.getInt("coins");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return -1;
        }
        if(enabled) {
            try {
                String table = config.getString("MySQL.table");
                PreparedStatement st = MySQL.con.prepareStatement("SELECT * FROM " + table + " WHERE PlayerName = ?");
                st.setString(1, player.getName());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    return rs.getInt("coins");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            try {
                PreparedStatement st = MySQL.con.prepareStatement("SELECT * FROM coinTable WHERE PlayerName = ?");
                st.setString(1, player.getName());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    return rs.getInt("coins");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public static int getMultiplierDB(Player player){
        Main plugin = Main.getPlugin();
        FileCreator config = plugin.getConfig();
        boolean enabled = config.getBoolean("MySQL.enabled");
        if(plugin.isOnlineMode()){
            if(enabled) {
                try {
                    String table = config.getString("MySQL.table");
                    PreparedStatement st = MySQL.con.prepareStatement("SELECT * FROM " + table + " WHERE UUID = ?");
                    st.setString(1, player.getUniqueId().toString());
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        return rs.getInt("multiplier");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else{
                try {
                    PreparedStatement st = MySQL.con.prepareStatement("SELECT * FROM coinTable WHERE UUID = ?");
                    st.setString(1, player.getUniqueId().toString());
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        return rs.getInt("multiplier");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return -1;
        }
        if(enabled) {
            try {
                String table = config.getString("MySQL.table");
                PreparedStatement st = MySQL.con.prepareStatement("SELECT * FROM " + table + " WHERE PlayerName = ?");
                st.setString(1, player.getName());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    return rs.getInt("multiplier");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            try {
                PreparedStatement st = MySQL.con.prepareStatement("SELECT * FROM coinTable WHERE PlayerName = ?");
                st.setString(1, player.getName());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    return rs.getInt("multiplier");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public static void setMultiplierDB(Player player, int multiplier){
        Main plugin = Main.getPlugin();
        FileCreator config = plugin.getConfig();
        boolean enabled = config.getBoolean("MySQL.enabled");
        if(plugin.isOnlineMode()){
            if(getMultiplierDB(player) != -1){
                if(enabled) {
                    try {
                        String table = config.getString("MySQL.table");
                        PreparedStatement st = MySQL.con.prepareStatement("UPDATE " + table + " SET multiplier = ? WHERE UUID = ?");
                        st.setInt(1, multiplier);
                        st.setString(2, player.getUniqueId().toString());
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else {
                    try {
                        PreparedStatement st = MySQL.con.prepareStatement("UPDATE coinTable SET multiplier = ? WHERE UUID = ?");
                        st.setInt(1, multiplier);
                        st.setString(2, player.getUniqueId().toString());
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            return;
        }
        if(getMultiplierDB(player) != -1){
            if(enabled) {
                try {
                    String table = config.getString("MySQL.table");
                    PreparedStatement st = MySQL.con.prepareStatement("UPDATE " + table + " SET multiplier = ? WHERE PlayerName = ?");
                    st.setInt(1, multiplier);
                    st.setString(2, player.getName());
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else {
                try {
                    PreparedStatement st = MySQL.con.prepareStatement("UPDATE coinTable SET multiplier = ? WHERE PlayerName = ?");
                    st.setInt(1, multiplier);
                    st.setString(2, player.getName());
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public static void setMultiCoinsDB(Player player, int coins, int multiplier){
        Main plugin = Main.getPlugin();
        FileCreator config = plugin.getConfig();
        boolean enabled = config.getBoolean("MySQL.enabled");
        if(plugin.isOnlineMode()){
            if(getCoinsDB(player) == -1 && getMultiplier(player) == -1){
                if(enabled) {
                    try {
                        String table = config.getString("MySQL.table");
                        PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO " + table + " (UUID,PlayerName,coins,multiplier) VALUES (?,?,?,?)");
                        st.setString(1, player.getUniqueId().toString());
                        st.setString(2, player.getName());
                        st.setInt(3, coins);
                        st.setInt(4, 1);
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else {
                    try {
                        PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO coinTable (UUID,PlayerName,coins,multiplier) VALUES (?,?,?,?)");
                        st.setString(1, player.getUniqueId().toString());
                        st.setString(2, player.getName());
                        st.setInt(3, coins);
                        st.setInt(4, 1);
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }else{
                if(enabled) {
                    try {
                        String table = config.getString("MySQL.table");
                        PreparedStatement st = MySQL.con.prepareStatement("UPDATE " + table + " SET coins = ?, multiplier = ? WHERE UUID = ?");
                        st.setInt(1, coins);
                        st.setInt(2, multiplier);
                        st.setString(3, player.getUniqueId().toString());
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else {
                    try {
                        PreparedStatement st = MySQL.con.prepareStatement("UPDATE coinTable SET coins = ?, multiplier = ? WHERE UUID = ?");
                        st.setInt(1, coins);
                        st.setInt(2, multiplier);
                        st.setString(3, player.getUniqueId().toString());
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            return;
        }
        if(getCoinsDB(player) == -1 && getMultiplierDB(player) == -1){
            if(enabled) {
                try {
                    String table = config.getString("MySQL.table");
                    PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO " + table + " (UUID,PlayerName,coins,multiplier) VALUES (?,?,?,?)");
                    st.setString(1, player.getUniqueId().toString());
                    st.setString(2, player.getName());
                    st.setInt(3, coins);
                    st.setInt(4, 1);
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else {
                try {
                    PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO coinTable (UUID,PlayerName,coins,multiplier) VALUES (?,?,?,?)");
                    st.setString(1, player.getUniqueId().toString());
                    st.setString(2, player.getName());
                    st.setInt(3, coins);
                    st.setInt(4, 1);
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else{
            if(enabled) {
                try {
                    String table = config.getString("MySQL.table");
                    PreparedStatement st = MySQL.con.prepareStatement("UPDATE " + table + " SET coins = ?, multiplier = ? WHERE PlayerName = ?");
                    st.setInt(1, coins);
                    st.setInt(2, multiplier);
                    st.setString(3, player.getName());
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else {
                try {
                    PreparedStatement st = MySQL.con.prepareStatement("UPDATE coinTable SET coins = ?, multiplier = ? WHERE PlayerName = ?");
                    st.setInt(1, coins);
                    st.setInt(2, multiplier);
                    st.setString(3, player.getName());
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public static void setCoinsDB(Player player, int coins){
        Main plugin = Main.getPlugin();
        FileCreator config = plugin.getConfig();
        boolean enabled = config.getBoolean("MySQL.enabled");
        if(plugin.isOnlineMode()){
            if(getCoinsDB(player) == -1){
                if(enabled) {
                    try {
                        String table = config.getString("MySQL.table");
                        PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO " + table + " (UUID,PlayerName,coins,multiplier) VALUES (?,?,?,?)");
                        st.setString(1, player.getUniqueId().toString());
                        st.setString(2, player.getName());
                        st.setInt(3, coins);
                        st.setInt(4, 1);
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else {
                    try {
                        PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO coinTable (UUID,PlayerName,coins,multiplier) VALUES (?,?,?,?)");
                        st.setString(1, player.getUniqueId().toString());
                        st.setString(2, player.getName());
                        st.setInt(3, coins);
                        st.setInt(4, 1);
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }else{
                if(enabled) {
                    try {
                        String table = config.getString("MySQL.table");
                        PreparedStatement st = MySQL.con.prepareStatement("UPDATE " + table + " SET coins = ? WHERE UUID = ?");
                        st.setInt(1, coins);
                        st.setString(2, player.getUniqueId().toString());
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else {
                    try {
                        PreparedStatement st = MySQL.con.prepareStatement("UPDATE coinTable SET coins = ? WHERE UUID = ?");
                        st.setInt(1, coins);
                        st.setString(2, player.getUniqueId().toString());
                        st.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            return;
        }
        if(getCoinsDB(player) == -1){
            if(enabled) {
                try {
                    String table = config.getString("MySQL.table");
                    PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO " + table + " (UUID,PlayerName,coins,multiplier) VALUES (?,?,?,?)");
                    st.setString(1, player.getUniqueId().toString());
                    st.setString(2, player.getName());
                    st.setInt(3, coins);
                    st.setInt(4, 1);
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else {
                try {
                    PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO coinTable (UUID,PlayerName,coins,multiplier) VALUES (?,?,?,?)");
                    st.setString(1, player.getUniqueId().toString());
                    st.setString(2, player.getName());
                    st.setInt(3, coins);
                    st.setInt(4, 1);
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else{
            if(enabled) {
                try {
                    String table = config.getString("MySQL.table");
                    PreparedStatement st = MySQL.con.prepareStatement("UPDATE " + table + " SET coins = ? WHERE PlayerName = ?");
                    st.setInt(1, coins);
                    st.setString(2, player.getName());
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else {
                try {
                    PreparedStatement st = MySQL.con.prepareStatement("UPDATE coinTable SET coins = ? WHERE PlayerName = ?");
                    st.setInt(1, coins);
                    st.setString(2, player.getName());
                    st.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public static void addCoinsDB(Player player, int coins){
        int current = getCoinsDB(player);
        int multiplier = 1;
        if(getMultiplierDB(player) >= 1)
            multiplier = getMultiplierDB(player);
        int newCoins = (coins*multiplier) + current;
        setCoinsDB(player, newCoins);
    }

    public static boolean sendCoinsDB(Player player, Player otherPlayer, int coins){
        int current = getCoinsDB(player);
        if(current >= coins){
            removeCoinsDB(player, coins);
            addCoinsDB(otherPlayer, coins);
            return true;
        }
        return false;
    }

    public static void removeCoinsDB(Player player, int coins){
        int current = getCoinsDB(player);
        int newCoins = current - coins;
        if(newCoins < 0)
        {
            setCoinsDB(player, 0);
        }else{
            setCoinsDB(player, newCoins);
        }
    }

    public static void addCoinsWithoutTitle(Player player, int coins){
        int current = getCoins(player);
        int multiplier = 1;
        if(getMultiplier(player) >= 1)
            multiplier = getMultiplier(player);
        int newCoins = (coins*multiplier) + current;
        setCoins(player, newCoins);
    }
    public static void addCoins(Player player, int coins){
        int current = getCoins(player);
        int multiplier = 1;
        if(getMultiplier(player) != -1)
            multiplier = getMultiplier(player);
        int newCoins = (coins*multiplier) + current;
        Main.getPlugin().getTitlePacket().sendFullTitle(player, "", coinsFormatter(coins * multiplier));
        setCoins(player, newCoins);
    }
    private static void addCoinsPrivate(Player player, int coins){
        int current = getCoins(player);
        int newCoins = coins + current;
        setCoins(player, newCoins);
    }

    public static boolean sendCoins(Player player, Player otherPlayer, int coins){
        int current = getCoins(player);
        if(current >= coins){
            removeCoins(player, coins);
            addCoinsPrivate(otherPlayer, coins);
            return true;
        }
        return false;
    }

    public static void removeCoins(Player player, int coins){
        int current = getCoins(player);
        int newCoins = current - coins;
        if(newCoins < 0){
            setCoins(player, 0);
        }else{
            setCoins(player, newCoins);
        }
    }
    public static void setCoins(Player player, int coins){
        if(getCoins(player) == -1){
            Main.getPlugin().addPlayerCoins(player);
        }else{
            Main.getPlugin().replacePlayerCoins(player, coins);
        }
    }
    public static int getCoins(Player player){
        if(Main.getPlugin().isOnlineMode()){
            String UUID = player.getUniqueId().toString();
            Map<String, Integer> coins = Main.getPlugin().getPlayerCoins();
            for(Map.Entry<String, Integer> coin : coins.entrySet()){
                if(coin.getKey().equalsIgnoreCase(UUID)){
                    return coin.getValue();
                }
            }
            return -1;
        }
        String Name = player.getName();
        Map<String, Integer> coins = Main.getPlugin().getPlayerCoins();
        for(Map.Entry<String, Integer> coin : coins.entrySet()){
            if(coin.getKey().equalsIgnoreCase(Name)){
                return coin.getValue();
            }
        }
        return -1;
    }

    public static void setMultiplier(Player player, int multiplier){
        if(getMultiplier(player) == -1){
            Main.getPlugin().addPlayerMultiplier(player);
        }else{
            Main.getPlugin().replacePlayerMultiplier(player, multiplier);
        }
    }
    public static int getMultiplier(Player player){
        if(Main.getPlugin().isOnlineMode()){
            String UUID = player.getUniqueId().toString();
            Map<String, Integer> multiplier = Main.getPlugin().getPlayerMultiplier();
            for(Map.Entry<String, Integer> mp : multiplier.entrySet()){
                if(mp.getKey().equalsIgnoreCase(UUID)){
                    return mp.getValue();
                }
            }
            return -1;
        }
        String Name = player.getName();
        Map<String, Integer> multiplier = Main.getPlugin().getPlayerMultiplier();
        for(Map.Entry<String, Integer> mp : multiplier.entrySet()){
            if(mp.getKey().equalsIgnoreCase(Name)){
                return mp.getValue();
            }
        }
        return -1;
    }

    public static String coinsFormatter(int coins){
        if(coins == 1){
            return Main.getPlugin().getMessages("add_coin_title", true).replace("%coin%", String.valueOf(coins));
        }
        return Main.getPlugin().getMessages("add_coins_title", true).replace("%coins%", String.valueOf(coins));
    }
}
