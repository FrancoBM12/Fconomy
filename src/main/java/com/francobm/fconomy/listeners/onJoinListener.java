package com.francobm.fconomy.listeners;

import com.francobm.fconomy.API.CoinAPI;
import com.francobm.fconomy.Main;
import com.francobm.fconomy.utils.UtilsFC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoinListener implements Listener {
    private final Main plugin;

    public onJoinListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

            @Override
            public void run() {
                if(player.isOnline()) {
                    plugin.addPlayerMultiCoins(player);
                }
                if(!player.hasPlayedBefore()){
                    try {
                        int startCoins = Integer.parseInt(plugin.getMessages("starting-balance", false));
                        int startMultiplier = Integer.parseInt(plugin.getMessages("starting-multiplier", false));
                        if(startCoins != 0) {
                            CoinAPI.addCoins(player, startCoins);
                        }
                        if(startMultiplier != 0) {
                            CoinAPI.setMultiplier(player, startMultiplier);
                        }
                    }catch(NumberFormatException exception){
                        plugin.getLogger().severe(UtilsFC.ChatColor("&cstarting-balance: {not_is_number}"));
                    }
                }
            }
        }, 20L);
    }
}
