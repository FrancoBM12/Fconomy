package com.francobm.fconomy.listeners;

import com.francobm.fconomy.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onLeaveListener implements Listener {
    private final Main plugin;

    public onLeaveListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.removePlayerMultiCoins(player);
    }
}
