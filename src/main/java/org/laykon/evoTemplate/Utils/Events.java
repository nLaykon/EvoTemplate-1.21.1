package org.laykon.evoTemplate.Utils;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public interface Events extends Listener, Utils {
    @NotNull
    String eventName();
}
