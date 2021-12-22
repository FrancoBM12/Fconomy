package com.francobm.fconomy.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class UtilsFC {

    public static String ChatColor(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    public static boolean getLastVersion(){
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        if (packageName.contains("1_17_") || packageName.contains("1_16_") || packageName.contains("1_15_") || packageName.contains("1_14_") || packageName.contains("1_13_") || packageName.contains("1_12_") || packageName.contains("1_11_") || packageName.contains("1_10_") || packageName.contains("1_9_")) {
            return true;
        }else if (packageName.contains("1_8_")){
            return false;
        }
        return false;
    }
}
