package org.laykon.evoTemplate.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Console {
    private static CommandSender sender = Bukkit.getConsoleSender();

    public static void error(String error){
        sender.sendMessage(ChatColor.AQUA + "[Evo] " + ChatColor.RED + error);
    }
    public static void log(String log){
        sender.sendMessage(ChatColor.AQUA + "[Evo] " + ChatColor.GRAY + log);
    }
    public static void warn(String warn){
        sender.sendMessage(ChatColor.AQUA + "[Evo] " + ChatColor.YELLOW + warn);
    }
    public static void success(String success){
        sender.sendMessage(ChatColor.AQUA + "[Evo] " + ChatColor.GREEN + success);
    }
    public static void execute(String command){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
    public static void announceError(String error){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(ChatColor.AQUA + "[Evo] " + ChatColor.RED + error);
        }
    }
    public static void announceLog(String log){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(ChatColor.AQUA + "[Evo] " + ChatColor.GRAY + log);
        }
    }
    public static void announceWarn(String warn){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(ChatColor.AQUA + "[Evo] " + ChatColor.YELLOW + warn);
        }
    }
    public static void announceSuccess(String success){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(ChatColor.AQUA + "[Evo] " + ChatColor.GREEN + success);
        }
    }
    public static void announce(String announcement){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(ChatColor.AQUA + "[Evo] " + announcement);
        }
    }
}
