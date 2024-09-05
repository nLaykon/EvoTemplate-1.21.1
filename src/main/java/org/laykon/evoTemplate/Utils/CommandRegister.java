package org.laykon.evoTemplate.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.laykon.evoTemplate.EvoTemplate;
import org.reflections.Reflections;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandRegister {
    public static void registerCommands() {
        List<Commands> commands = getCommands();

        if (commands.isEmpty()){
            Console.warn("No commands to register!");
            return;
        }

        for (Commands command : commands) {
            try {
                registerCommand(command);
            } catch (Exception e) {
                Console.error("Failed to register command: " + command.commandName());
                e.printStackTrace();
            }
        }
        Console.success("Commands registered!");
    }

    private static void registerCommand(Commands command) throws Exception {
        String commandName = command.commandName();
        CommandMap commandMap = getCommandMap();

        BukkitCommand bukkitCommand = new BukkitCommand(commandName) {
            @Override
            public boolean execute(CommandSender sender, String label, String[] args) {
                return command.onCommand(sender, this, label, args);
            }

            @Override
            public @Nullable List<String> tabComplete(CommandSender sender, String alias, String[] args) {
                if (command instanceof TabCompleter) {
                    return ((TabCompleter) command).onTabComplete(sender, this, alias, args);
                }
                return super.tabComplete(sender, alias, args);
            }
        };

        commandMap.register(EvoTemplate.getInstance().getDescription().getName(), bukkitCommand);
        Console.log("Registered " + commandName + " Command!");
    }

    private static CommandMap getCommandMap() throws Exception {
        Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMapField.setAccessible(true);
        return (CommandMap) commandMapField.get(Bukkit.getServer());
    }

    private static List<Commands> getCommands() {
        Reflections reflections = new Reflections("org.laykon.evoTemplate");
        Set<Class<? extends Commands>> classes = reflections.getSubTypesOf(Commands.class);

        List<Commands> commands = new ArrayList<>();
        for (Class<? extends Commands> clazz : classes) {
            try {
                commands.add(clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return commands;
    }
}
