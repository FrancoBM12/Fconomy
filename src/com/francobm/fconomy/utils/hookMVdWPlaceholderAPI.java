package com.francobm.fconomy.utils;

import com.francobm.fconomy.API.CoinAPI;
import com.francobm.fconomy.Main;
import org.bukkit.Bukkit;

public class hookMVdWPlaceholderAPI {
    private Main plugin;

    public hookMVdWPlaceholderAPI(Main plugin){
        this.plugin = plugin;
        MVdWPlaceHolderApi();
    }

    private void MVdWPlaceHolderApi() {
        if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") != null) {
            plugin.getLogger().info("MVdWPlaceholderAPI Detectado");
            be.maximvdw.placeholderapi.PlaceholderAPI.registerPlaceholder(plugin, "fconomy_coins", event -> plugin.getCoins(event.getPlayer()));
        }
    }
}
