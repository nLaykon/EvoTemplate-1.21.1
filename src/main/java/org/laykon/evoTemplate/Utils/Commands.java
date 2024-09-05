package org.laykon.evoTemplate.Utils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Commands extends CommandExecutor, Utils {
    @NotNull
    String commandName();

    default boolean checkOp(CommandSender sender) {
        if (sender.isOp()) {
            return true;
        }
        sender.sendMessage("§cCommand not found.");
        return false;

    }

    default boolean checkPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        sender.sendMessage("§cThis command can only be run by a player.");
        return false;
    }
}
