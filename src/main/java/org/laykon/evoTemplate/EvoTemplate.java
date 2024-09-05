package org.laykon.evoTemplate;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.laykon.evoTemplate.Utils.CommandRegister;
import org.laykon.evoTemplate.Utils.EventsRegister;

public final class EvoTemplate extends JavaPlugin {
    public static EvoTemplate instance;
    public static EvoTemplate getInstance() {return instance;}

    @Override
    public void onEnable() {
        instance = this;
        EventsRegister.registerEvents();
        CommandRegister.registerCommands();

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }
}
