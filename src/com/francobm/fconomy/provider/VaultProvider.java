package com.francobm.fconomy.provider;

import com.francobm.fconomy.API.CoinAPI;

import com.francobm.fconomy.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;

import java.util.List;

public class VaultProvider implements Economy {
    private final Main plugin;
    public VaultProvider(Main plugin){
        this.plugin = plugin;
    }

    public VaultProvider setup() {
        Bukkit.getServicesManager().register(Economy.class, this, this.plugin, ServicePriority.High);
        return this;
    }

    public void shutdown() {
        Bukkit.getServicesManager().unregister(Economy.class, this);
    }

    @Override
    public boolean isEnabled() {
        return plugin.isEnabled();
    }

    @Override
    public String getName() {
        return plugin.getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return "Coins";
    }

    @Override
    public String currencyNameSingular() {
        return "Coin";
    }

    @Override
    public boolean hasAccount(String s) {
        Player player = Bukkit.getPlayer(s);
        return CoinAPI.getCoins(player) >= 0;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        Player player = offlinePlayer.getPlayer();
        return CoinAPI.getCoins(player) >= 0;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    @Override
    public double getBalance(String s) {
        Player player = Bukkit.getPlayer(s);
        double coins = CoinAPI.getCoins(player);
        return coins;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        Player player = offlinePlayer.getPlayer();
        double coins = CoinAPI.getCoins(player);
        return coins;
    }

    @Override
    public double getBalance(String s, String s1) {
        Player player = Bukkit.getPlayer(s);
        double coins = CoinAPI.getCoins(player);
        return coins;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        Player player = offlinePlayer.getPlayer();
        double coins = CoinAPI.getCoins(player);
        return coins;
    }

    @Override
    public boolean has(String s, double v) {
        Player player = Bukkit.getPlayer(s);
        double coins = CoinAPI.getCoins(player);
        return coins >= (int)v;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        Player player = offlinePlayer.getPlayer();
        double coins = CoinAPI.getCoins(player);
        return coins >= (int)v;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        Player player = Bukkit.getPlayer(s);
        double coins = CoinAPI.getCoins(player);
        return coins >= (int)v;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        Player player = offlinePlayer.getPlayer();
        double coins = CoinAPI.getCoins(player);
        return coins >= (int)v;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        if(has(s,v)){
            CoinAPI.removeCoins(Bukkit.getPlayer(s), (int)v);
            return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE, "No Coins");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        if(has(offlinePlayer,v)){
            CoinAPI.removeCoins(offlinePlayer.getPlayer(), (int)v);
            return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.FAILURE, "No Coins");
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        if(has(s,v)){
            CoinAPI.removeCoins(Bukkit.getPlayer(s), (int)v);
            return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE, "No Coins");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        if(has(offlinePlayer,v)){
            CoinAPI.removeCoins(offlinePlayer.getPlayer(), (int)v);
            return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.FAILURE, "No Coins");
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        Player player = Bukkit.getPlayer(s);
        CoinAPI.addCoinsWithoutTitle(player, (int)v);
        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        Player player = offlinePlayer.getPlayer();
        CoinAPI.addCoinsWithoutTitle(player, (int)v);
        return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        Player player = Bukkit.getPlayer(s);
        CoinAPI.addCoinsWithoutTitle(player, (int)v);
        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        Player player = offlinePlayer.getPlayer();
        CoinAPI.addCoinsWithoutTitle(player, (int)v);
        return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
