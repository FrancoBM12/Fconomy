package com.francobm.fconomy.API;

import org.bukkit.entity.Player;

public class Coin {

    public void getCoins(Player player){
        CoinAPI.getCoins(player);
    }

    public void addCoins(Player player, int coins){
        CoinAPI.addCoins(player, coins);
    }

    public void removeCoins(Player player, int coins){
        CoinAPI.removeCoins(player, coins);
    }

    public void getMultiplier(Player player){
        CoinAPI.getMultiplierDB(player);
    }

    public void setMultiplier(Player player, int multiplier){
        CoinAPI.setMultiplier(player, multiplier);
    }
}