package com.francobm.fconomy.utils;

import com.francobm.fconomy.Main;
import org.bukkit.plugin.RegisteredServiceProvider;

public class hookVault {
    private Main plugin;
    private static net.milkbowl.vault.economy.Economy econ;

    public hookVault(Main plugin){
        this.plugin = plugin;
        setupEconomy();
    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> rsp = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static net.milkbowl.vault.economy.Economy getEcon() {
        return econ;
    }
}
