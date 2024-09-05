package org.laykon.evoTemplate.Utils;

import org.bukkit.Bukkit;
import org.laykon.evoTemplate.EvoTemplate;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EventsRegister {
    public static void registerEvents() {
        List<Events> eventsList = getEvents();

        if (eventsList.isEmpty()) {
            Console.warn("No events to register!");
            return;
        }

        try {
            for (Events events : eventsList) {
                Bukkit.getServer().getPluginManager().registerEvents(events, EvoTemplate.getInstance());
                Console.log("Registered " + events.eventName() + " event!");
            }
            Console.success("Registered all events!");
        } catch (Exception e) {
            Console.error("Not all events registered!");
            e.printStackTrace();
        }
    }

    private static List<Events> getEvents() {
        Reflections reflections = new Reflections("org.laykon.Evo");
        Set<Class<? extends Events>> classes = reflections.getSubTypesOf(Events.class);

        List<Events> events = new ArrayList<>();
        for (Class<? extends Events> clazz : classes) {
            try {
                events.add(clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return events;
    }
}
