package org.laykon.evoTemplate.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.laykon.evoTemplate.Utils.Commands;

public class Ping implements Commands {
    @Override
    public @NotNull String commandName() {
        return "ping";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage("pong!");
        return true;
    }
}
