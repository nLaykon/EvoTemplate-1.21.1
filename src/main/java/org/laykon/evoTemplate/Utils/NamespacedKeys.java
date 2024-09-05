package org.laykon.evoTemplate.Utils;

import org.bukkit.NamespacedKey;
import org.laykon.evoTemplate.EvoTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NamespacedKeys {
    private static final ConcurrentMap<String, NamespacedKey> keys = new ConcurrentHashMap<>();

    public static NamespacedKey getKey(String id) {
        return keys.computeIfAbsent(id, key -> new NamespacedKey(EvoTemplate.getInstance(), key));
    }
}
