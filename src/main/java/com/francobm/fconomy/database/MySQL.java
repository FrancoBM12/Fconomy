package com.francobm.fconomy.database;

import com.francobm.fconomy.Main;
import com.francobm.fconomy.files.FileCreator;
import com.francobm.fconomy.utils.UtilsFC;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    public static Connection con;
    public static void connect(Main plugin) {
        FileCreator config = plugin.getConfig();
        boolean enabled = config.getBoolean("MySQL.enabled");
        if(enabled) {
            if (!isConnected()) {
                try {
                    String host = config.getString("MySQL.host");
                    int port = config.getInt("MySQL.port");
                    String user = config.getString("MySQL.user");
                    String pass = config.getString("MySQL.password");
                    String database = config.getString("MySQL.database");
                    con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, pass);
                    Bukkit.getServer().getConsoleSender().sendMessage(UtilsFC.ChatColor(plugin.namePlugin+"&aMySQL conectada"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else{
            try {
                Bukkit.getServer().getConsoleSender().sendMessage(UtilsFC.ChatColor(plugin.namePlugin+"&eMySQL Desactivada, Conectando la base de datos con SQLite."));
                File FileSQL = new File(plugin.getDataFolder(), "fconomy.db");
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:" + FileSQL);
                Bukkit.getServer().getConsoleSender().sendMessage(UtilsFC.ChatColor(plugin.namePlugin+"&aSQLite conectada"));
            } catch (Exception e) {
                e.printStackTrace();
                Main.getPlugin().getPluginLoader().disablePlugin(Main.getPlugin());
            }
        }
    }

    public static void disconnect(Main plugin) {
        FileCreator config = plugin.getConfig();
        boolean enabled = config.getBoolean("MySQL.enabled");
        if(isConnected()){
            if(enabled) {
                try {
                    con.close();
                    Bukkit.getServer().getConsoleSender().sendMessage(UtilsFC.ChatColor("&cConexión MySQL Cerrada"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else{
                try {
                    con.close();
                    Bukkit.getServer().getConsoleSender().sendMessage(UtilsFC.ChatColor("&cConexión SQLite Cerrada"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public static boolean isConnected() {
        return (con != null);
    }

    public static void createTable(Main plugin) {
        FileCreator config = plugin.getConfig();
        boolean enabled = config.getBoolean("MySQL.enabled");
        if(enabled) {
            try {
                String table = config.getString("MySQL.table");
                con.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (UUID VARCHAR(100), PlayerName VARCHAR(100), coins INT(16), multiplier INT(16))").executeUpdate();
                Bukkit.getServer().getConsoleSender().sendMessage(UtilsFC.ChatColor("&eTabla MySQL creada con exito!"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            try {
                con.prepareStatement("CREATE TABLE IF NOT EXISTS coinTable (UUID VARCHAR(100), PlayerName VARCHAR(100), coins INT(16), multiplier INT(16))").executeUpdate();
                Bukkit.getServer().getConsoleSender().sendMessage(UtilsFC.ChatColor("&eTabla SQLite creada con exito!"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
