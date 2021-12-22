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

public class Command implements CommandExecutor {
    private Main plugin;

    public Command(Main plugin)
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
            if(args[0].equalsIgnoreCase("add"))
            {
                if(sender instanceof ConsoleCommandSender) {
                    if(args.length >= 3) {
                        String name = args[1];
                        if(Bukkit.getPlayer(name) == null){
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", name));
                            return true;
                        }
                        try{
                            int coins = Integer.valueOf(args[2]);
                            if(coins < 1)
                            {
                                sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("number_is_0", true));
                                return true;
                            }
                            CoinAPI.addCoins(Bukkit.getPlayer(name), coins);
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("add_coins", true).replace("%coins%", coins+"").replace("%player%", name));
                        }catch(NumberFormatException e) {
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("invalid_number", true));
                        }
                    }else{
                        sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("add_coins_help", true));
                    }
                    return true;
                }
                // /coins add <player> <coins>
                String soundCorrectList = plugin.getSounds("correct");
                String[] soundCorrectArgs = soundCorrectList.split(";");
                String soundCorrectMsg = soundCorrectArgs[0];
                float soundCorrectF1 = Float.valueOf(soundCorrectArgs[1]);
                float soundCorrectF2 = Float.valueOf(soundCorrectArgs[2]);
                String soundErrorList = plugin.getSounds("error");
                String[] soundErrorArgs = soundErrorList.split(";");
                String soundErrorMsg = soundErrorArgs[0];
                float soundErrorF1 = Float.valueOf(soundErrorArgs[1]);
                float soundErrorF2 = Float.valueOf(soundErrorArgs[2]);
                if(hasPermission(player) || player.hasPermission("fconomy.add")){
                    if(args.length >= 3) {
                        String name = args[1];
                        if(Bukkit.getPlayer(name) == null){
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", name));
                            return true;
                        }
                        try{
                            int coins = Integer.valueOf(args[2]);
                            if(coins < 1)
                            {
                                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("number_is_0", true));
                                player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                                return true;
                            }
                            CoinAPI.addCoins(Bukkit.getPlayer(name), coins);
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("add_coins", true).replace("%coins%", coins+"").replace("%player%", name));
                            player.playSound(player.getLocation(), Sound.valueOf(soundCorrectMsg), soundCorrectF1, soundCorrectF2);
                        }catch(NumberFormatException e) {
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("invalid_number", true));
                            player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                        }
                    }else{
                        player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("add_coins_help", true));
                        player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                    }
                }else{
                    player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("no_perm", true));
                    player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                }
                return true;
            }else if(args[0].equalsIgnoreCase("set")){
                if(sender instanceof ConsoleCommandSender) {
                    if(args.length >= 3) {
                        String name = args[1];
                        if(Bukkit.getPlayer(name) == null){
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", name));
                            return true;
                        }
                        try{
                            int coins = Integer.parseInt(args[2]);
                            if(coins < 0)
                            {
                                sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("number_is_0", true));
                                return true;
                            }
                            CoinAPI.setCoins(Bukkit.getPlayer(name), coins);
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("set_coins", true).replace("%coins%", coins+"").replace("%player%", name));
                        }catch(NumberFormatException e) {
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("invalid_number", true));
                        }
                    }else{
                        sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("set_coins_help", true));
                    }
                    return true;
                }
                // /coins set <player> <coins>
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
                if(hasPermission(player) || player.hasPermission("fconomy.set")){
                    if(args.length >= 3) {
                        Player playerTarget = Bukkit.getPlayer(args[1]);
                        if(playerTarget == null){
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", args[1]));
                            return true;
                        }
                        try{
                            int coins = Integer.parseInt(args[2]);
                            if(coins < 0)
                            {
                                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("number_is_0", true));
                                player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                                return true;
                            }
                            CoinAPI.setCoins(playerTarget, coins);
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("set_coins", true).replace("%coins%", coins+"").replace("%player%", playerTarget.getName()));
                            playerTarget.playSound(playerTarget.getLocation(), Sound.valueOf(soundCorrectMsg), soundCorrectF1, soundCorrectF2);
                        }catch(NumberFormatException e) {
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("invalid_number", true));
                            player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                        }
                    }else{
                        player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("set_coins_help", true));
                        player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                    }
                }else{
                    player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("no_perm", true));
                    player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                }
                return true;
            }else if(args[0].equalsIgnoreCase("remove")){
                if(sender instanceof ConsoleCommandSender) {
                    if(args.length >= 3) {
                        String name = args[1];
                        if(Bukkit.getPlayer(name) == null){
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", name));
                            return true;
                        }
                        try{
                            int coins = Integer.parseInt(args[2]);
                            if(coins < 1)
                            {
                                sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("number_is_0", true));
                                return true;
                            }
                            CoinAPI.removeCoins(Bukkit.getPlayer(name), coins);
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("remove_coins", true).replace("%coins%", coins+"").replace("%player%", name));
                        }catch(NumberFormatException e) {
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("invalid_number", true));
                        }
                    }else{
                        sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("remove_coins_help", true));
                    }
                    return true;
                }
                // /coins remove <player> <coins>
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
                if(hasPermission(player) || player.hasPermission("fconomy.remove")){
                    if(args.length >= 3) {
                        Player playerTarget = Bukkit.getPlayer(args[1]);
                        if(playerTarget == null){
                            sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", args[1]));
                            return true;
                        }
                        try{
                            int coins = Integer.parseInt(args[2]);
                            if(coins < 1)
                            {
                                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("number_is_0", true));
                                player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                                return true;
                            }
                            CoinAPI.removeCoins(playerTarget, coins);
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("remove_coins", true).replace("%coins%", coins+"").replace("%player%", playerTarget.getName()));
                            playerTarget.playSound(playerTarget.getLocation(), Sound.valueOf(soundCorrectMsg), soundCorrectF1, soundCorrectF2);
                        }catch(NumberFormatException e) {
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("invalid_number", true));
                            player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                        }
                    }else{
                        player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("remove_coins_help", true));
                        player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                    }
                }else{
                    player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("no_perm", true));
                    player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                }
                return true;
            }else if(args[0].equalsIgnoreCase("send")){
                if(sender instanceof ConsoleCommandSender) {
                    plugin.getLogger().info("console not have permission for this command");
                    return true;
                }
                // /coins send <player> <coins>
                String soundSuccessSendList = plugin.getSounds("player_success_send");
                String[] soundSuccessSendArgs = soundSuccessSendList.split(";");
                String soundSuccess = soundSuccessSendArgs[0];
                float soundSuccessF1 = Float.parseFloat(soundSuccessSendArgs[1]);
                float soundSuccessF2 = Float.parseFloat(soundSuccessSendArgs[2]);
                String soundSuccessReceiveList = plugin.getSounds("player_receive_send");
                String[] soundSuccessReceiveArgs = soundSuccessReceiveList.split(";");
                String soundSuccessReceive = soundSuccessReceiveArgs[0];
                float soundSuccessReceiveF1 = Float.parseFloat(soundSuccessReceiveArgs[1]);
                float soundSuccessReceiveF2 = Float.parseFloat(soundSuccessReceiveArgs[2]);
                String soundErrorList = plugin.getSounds("error");
                String[] soundErrorArgs = soundErrorList.split(";");
                String soundErrorMsg = soundErrorArgs[0];
                float soundErrorF1 = Float.parseFloat(soundErrorArgs[1]);
                float soundErrorF2 = Float.parseFloat(soundErrorArgs[2]);
                if(player.hasPermission("fconomy.send")){
                    if(args.length >= 3) {
                        String name = args[1];
                        if(Bukkit.getPlayer(name) == null){
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", name));
                            player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                            return true;
                        }
                        if(name.equalsIgnoreCase(player.getName())){
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("send_coins_to_yourself", true));
                            player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                            return true;
                        }
                        try{
                            int coins = Integer.parseInt(args[2]);
                            if(coins < 1)
                            {
                                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("number_is_0", true));
                                player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                                return true;
                            }
                            if(CoinAPI.sendCoins(player, Bukkit.getPlayer(name), coins)){
                                player.playSound(player.getLocation(), Sound.valueOf(soundSuccess), soundSuccessF1, soundSuccessF2);
                                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("player_send_coins_success", true).replace("%coins%", coins+"").replace("%player%", name));
                                Bukkit.getPlayer(name).sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("player_receive_coins_succes", true).replace("%coins%", coins+"").replace("%player%", player.getName()));
                                Bukkit.getPlayer(name).playSound(Bukkit.getPlayer(name).getLocation(), Sound.valueOf(soundSuccessReceive), soundSuccessReceiveF1, soundSuccessReceiveF2);
                            }else{
                                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("not_enough_coins", true));
                                player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                            }
                        }catch(NumberFormatException e) {
                            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("invalid_number", true));
                            player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                        }
                    }else{
                        player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("send_coins_help", true));
                        player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                    }
                }else{
                    player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("no_perm", true));
                    player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                }
                return true;
            }else if(args[0].equalsIgnoreCase("reload")){
                if(sender instanceof ConsoleCommandSender) {
                    plugin.reload();
                    sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("reload", true));
                    return true;
                }
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
                if(hasPermission(player) || player.hasPermission("fconomy.reload")) {
                    plugin.reload();
                    player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("reload", true));
                    player.playSound(player.getLocation(), Sound.valueOf(soundCorrectMsg), soundCorrectF1, soundCorrectF2);
                    return true;
                }
                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("no_perm", true));
                player.playSound(player.getLocation(), Sound.valueOf(soundErrorMsg), soundErrorF1, soundErrorF2);
                return true;
            }else if(args[0].equalsIgnoreCase("help")){
                if(sender instanceof ConsoleCommandSender) {
                    plugin.getLogger().info("console not have permission for this command");
                    return true;
                }
                helpMessage(player);
                return true;
            }

            if (target != null) {
                if(sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("player_coins", true).replace("%player%", target.getName()).replace("%coins%", CoinAPI.getCoins(target) + ""));
                    return true;
                }
                if(player.hasPermission("fconomy.see")) {
                    player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("player_coins", true).replace("%player%", target.getName()).replace("%coins%", CoinAPI.getCoins(target) + ""));
                    return true;
                }
                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("no_perm", true));
            }else {
                if(sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", args[0]));
                    return true;
                }
                player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("target_not_online", true).replace("%player%", args[0]));
            }
        }else{
            if(sender instanceof ConsoleCommandSender) {
                plugin.getLogger().info("console not have permission for this command");
                return true;
            }
            player.sendMessage(plugin.getMessages("plugin_prefix", true) + plugin.getMessages("own_coins", true).replace("%coins%", CoinAPI.getCoins(player)+""));
        }
        return true;
    }

    public void helpMessage(Player player) {
        for(String s : plugin.getList_help()){
            player.sendMessage(UtilsFC.ChatColor(s));
        }
        if(hasPermission(player))
        {
            player.sendMessage(UtilsFC.ChatColor("&e/coins set <player> <coins> - setea coins a un jugador"));
            player.sendMessage(UtilsFC.ChatColor("&e/coins add <player> <coins> - agrega coins a un jugador"));
            player.sendMessage(UtilsFC.ChatColor("&e/coins remove <player> <coins> - quita coins a un jugador"));
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
