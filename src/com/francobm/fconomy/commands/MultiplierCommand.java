package com.francobm.fconomy.commands;

import com.francobm.fconomy.API.CoinAPI;
import com.francobm.fconomy.Main;
import com.francobm.fconomy.utils.UtilsFC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MultiplierCommand implements CommandExecutor {
    private Main plugin;

    public MultiplierCommand(Main plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = null;
        if(sender instanceof Player){
            player = (Player)sender;
        }
        if(args.length >= 1){
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if(args[0].equalsIgnoreCase("set"))
            {
                if(sender instanceof ConsoleCommandSender){
                    if(args.length >= 3) {
                        String name = args[1];
                        if(Bukkit.getPlayer(name) == null){
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", name));
                            return true;
                        }
                        try{
                            int multiplier = Integer.valueOf(args[2]);
                            if(multiplier < 1)
                            {
                                sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("number_is_0", true));
                                return true;
                            }
                            CoinAPI.setMultiplier(Bukkit.getPlayer(name), multiplier);
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("set_multiplier", true).replace("%multiplier%", multiplier+"").replace("%player%", name));
                        }catch(NumberFormatException e) {
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("invalid_number", true));
                        }
                    }else{
                        sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("set_multiplier_help", true));
                    }
                }
                // /multiplier set <player> <multiplier>
                String soundCorrectList = plugin.getSounds("correct");
                String[] soundCorrectArgs = soundCorrectList.split(";");
                String soundCorrectMsg = soundCorrectArgs[0];
                float soundCorrectF1 = Float.parseFloat(soundCorrectArgs[1]);
                float soundCorrectF2 = Float.parseFloat(soundCorrectArgs[2]);
                String soundErrorList = plugin.getSounds("error");
                String[] soundErrorArgs = soundErrorList.split(";");
                String soundErrorMsg = soundErrorArgs[0];
                float soundErrorF1 = Float.parseFloat(soundErrorArgs[1]);
                float soundErrorF2 = Float.parseFloat(soundErrorArgs[2]);
                if(player == null) return true;
                if(hasPermission(player) || player.hasPermission("fconomy.multiplier.set")){
                    if(args.length >= 3) {
                        String name = args[1];
                        if(Bukkit.getPlayer(name) == null){
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", name));
                            return true;
                        }
                        try{
                            int multiplier = Integer.parseInt(args[2]);
                            if(multiplier < 1)
                            {
                                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("number_is_0", true));
                                player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                                return true;
                            }
                            CoinAPI.setMultiplier(Bukkit.getPlayer(name), multiplier);
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("set_multiplier", true).replace("%multiplier%", multiplier+"").replace("%player%", name));
                            player.playSound(player.getLocation(), Sound.valueOf(soundCorrectMsg), soundCorrectF1, soundCorrectF2);
                        }catch(NumberFormatException e) {
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("invalid_number", true));
                            player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                        }
                    }else{
                        player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("set_multiplier_help", true));
                        player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                    }
                }else{
                    player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("no_perm", true));
                    player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                }
            }
            if(args.length == 1) {
                if (target != null) {
                    if (sender instanceof ConsoleCommandSender) {
                        sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("player_multiplier", true).replace("%player%", target.getName()).replace("%multiplier%", CoinAPI.getMultiplier(target) + ""));
                        return true;
                    }
                    if (player == null) return true;
                    if (player.hasPermission("fconomy.multiplier.see")) {
                        player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("player_multiplier", true).replace("%player%", target.getName()).replace("%multiplier%", CoinAPI.getMultiplier(target) + ""));
                        return true;
                    }
                    player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("no_perm", true));
                } else {
                    if (sender instanceof ConsoleCommandSender) {
                        sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", args[0]));
                        return true;
                    }
                    if (player == null) return true;
                    player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", args[0]));
                    return true;
                }
            }
        }else{
            if(sender instanceof ConsoleCommandSender) {
                helpMessage(sender);
                return true;
            }
            helpMessage(player);
        }
        return true;
    }

    public void helpMessage(CommandSender sender) {
        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage(UtilsFC.ChatColor("&e/multiplier set <player> <coins> - setea el multiplicador de un jugador"));
            sender.sendMessage(UtilsFC.ChatColor("&e/multiplier <player> - observa el multiplicador de un jugador"));
            return;
        }
        Player player = (Player) sender;
        if(hasPermission(player))
        {
            sender.sendMessage(UtilsFC.ChatColor("&e/multiplier set <player> <coins> - setea el multiplicador de un jugador"));
            sender.sendMessage(UtilsFC.ChatColor("&e/multiplier <player> - observa el multiplicador de un jugador"));
        }
    }
    public boolean hasPermission(Player player){
        if(player.isOp() || player.hasPermission("fconomy.admin") || player.hasPermission("fconomy.*"))
        {
            return true;
        }else{
            return false;
        }
    }
}
