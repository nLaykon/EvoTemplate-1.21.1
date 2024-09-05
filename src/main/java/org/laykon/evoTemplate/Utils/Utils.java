package org.laykon.evoTemplate.Utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.laykon.evoTemplate.EvoTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Utils {

    JavaPlugin plugin = EvoTemplate.getInstance();

    default String Colour(String s) {
        s = ChatColor.translateAlternateColorCodes('&', s);
        return applyHexColor(s);
    }

    default String[] Colour(String[] s) {
        for (String s1 : s) {
            s = new String[]{ChatColor.translateAlternateColorCodes('&', s1)};
        }
        return s;
    }

    default List<String> Colour(List<String> s) {
        for (String s1 : s) {
            s = Collections.singletonList(ChatColor.translateAlternateColorCodes('&', s1));
        }
        return s;
    }

    default String getPrefix() {
        return Colour("");
    }

    default void sendMessage(Player player, String message) {
        player.sendMessage(getPrefix() + Colour(message));
    }

    default void sendMessage(CommandSender player, String message) {
        player.sendMessage(getPrefix() + Colour(message));
    }

    default void sendMessage(String message, CommandSender... players) {
        for (CommandSender player : players) {
            player.sendMessage(getPrefix() + Colour(message));
        }
    }

    default void sendMessage(String message, Player... players) {
        for (Player player : players) {
            player.sendMessage(getPrefix() + Colour(message));
        }
    }

    default Location serverSpawn() {
        return Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
    }


    default String applyHexColor(String text) {
        Pattern hexColorPattern = Pattern.compile("#[0-9a-fA-F]{6}");
        Matcher matcher = hexColorPattern.matcher(text);

        if (matcher.find()) {
            String hexColor = matcher.group();
            text = text.replace(hexColor, net.md_5.bungee.api.ChatColor.of(hexColor) + "");
        }
        return text;
    }

    default ItemStack getItem(ItemStack item, String Name, String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Colour(Name));
        meta.setLore(setLore(lore));
        item.setItemMeta(meta);
        return item;
    }

    default List<String> setLore(String... s) {
        List<String> lore = new ArrayList<String>();
        for (String s1 : s) {
            lore.add(s1);
        }
        return Colour(lore);
    }

    default String getNiceName(Material material) {
        String name = material.name().toLowerCase().replace("_", " ");
        return capitalizeWords(name);
    }

    default String capitalizeWords(String str) {
        StringBuilder result = new StringBuilder();
        String[] words = str.split("\\s");
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        return result.toString().trim();
    }

    default ItemStack nbtItem(String name, Material item, String key, String value) {
        JavaPlugin plugin = EvoTemplate.getInstance();

        ItemStack itemStack = new ItemStack(item);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(name);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    default boolean isItem(Player player, String tag) {
        if (player.getInventory().getItemInMainHand().isEmpty()) return false;
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKeys.getKey(tag))) {
            return true;
        }
        return false;
    }

    default String hexColour(String s) {
        final char altColorChar = '&';
        final StringBuilder b = new StringBuilder();
        final char[] mess = s.toCharArray();
        boolean color = false, hashtag = false, doubleTag = false;
        char tmp;

        for (int i = 0; i < mess.length; ) {
            final char c = mess[i];
            if (doubleTag) {
                doubleTag = false;
                final int max = i + 6;
                if (max <= mess.length) {
                    boolean match = true;
                    for (int n = i; n < max; n++) {
                        tmp = mess[n];
                        if (!((tmp >= '0' && tmp <= '9') || (tmp >= 'a' && tmp <= 'f') || (tmp >= 'A' && tmp <= 'F'))) {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        b.append(ChatColor.COLOR_CHAR);
                        b.append('x');
                        for (; i < max; i++) {
                            tmp = mess[i];
                            b.append(ChatColor.COLOR_CHAR);
                            b.append(tmp);
                        }
                        continue;
                    }
                }
                b.append(altColorChar);
                b.append("##");
            }
            if (hashtag) {
                hashtag = false;
                if (c == '#') {
                    doubleTag = true;
                    i++;
                    continue;
                }
                final int max = i + 6;
                if (max <= mess.length) {
                    boolean match = true;
                    for (int n = i; n < max; n++) {
                        tmp = mess[n];
                        if (!((tmp >= '0' && tmp <= '9') || (tmp >= 'a' && tmp <= 'f') || (tmp >= 'A' && tmp <= 'F'))) {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        b.append(ChatColor.COLOR_CHAR);
                        b.append('x');
                        for (; i < max; i++) {
                            b.append(ChatColor.COLOR_CHAR);
                            b.append(mess[i]);
                        }
                        continue;
                    }
                }
                b.append(altColorChar);
                b.append('#');
            }
            if (color) {
                color = false;
                if (c == '#') {
                    hashtag = true;
                    i++;
                    continue;
                }
                if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || c == 'r' || (c >= 'k' && c <= 'o') || (c >= 'A' && c <= 'F') || c == 'R' || (c >= 'K' && c <= 'O')) {
                    b.append(ChatColor.COLOR_CHAR);
                    b.append(c);
                    i++;
                    continue;
                }
                b.append(altColorChar);
            }
            if (c == altColorChar) {
                color = true;
                i++;
                continue;
            }
            b.append(c);
            i++;
        }
        if (color) b.append(altColorChar);
        else if (hashtag) {
            b.append(altColorChar);
            b.append('#');
        } else if (doubleTag) {
            b.append(altColorChar);
            b.append("##");
        }
        return b.toString();
    }

    default boolean isCrop(final Material material) {
        return material == Material.WHEAT ||
                material == Material.CARROTS ||
                material == Material.POTATOES ||
                material == Material.BEETROOTS;
    }

    default boolean isLog(Material material) {
        return material == Material.OAK_LOG ||
                material == Material.DARK_OAK_LOG ||
                material == Material.SPRUCE_LOG ||
                material == Material.JUNGLE_LOG ||
                material == Material.BIRCH_LOG ||
                material == Material.ACACIA_LOG ||
                material == Material.CHERRY_LOG ||
                material == Material.MANGROVE_LOG;
    }

    default boolean isStone(Material material) {
        return material == Material.STONE ||
                material == Material.COBBLESTONE ||
                material == Material.ANDESITE ||
                material == Material.GRANITE ||
                material == Material.DEEPSLATE ||
                material == Material.COBBLED_DEEPSLATE ||
                material == Material.TUFF;
    }

    default boolean isValidAutoSmelt(Material material) {
        return material == Material.STONE ||
                material == Material.COBBLESTONE ||
                material == Material.DEEPSLATE ||
                material == Material.COBBLED_DEEPSLATE ||
                material == Material.DEEPSLATE_IRON_ORE ||
                material == Material.IRON_ORE ||
                material == Material.RAW_IRON_BLOCK ||
                material == Material.COPPER_ORE ||
                material == Material.DEEPSLATE_COPPER_ORE ||
                material == Material.RAW_COPPER_BLOCK ||
                material == Material.GOLD_ORE ||
                material == Material.DEEPSLATE_GOLD_ORE ||
                material == Material.RAW_GOLD_BLOCK;
    }

    default int getMaxAge(final Material material) {

        if (material == Material.WHEAT ||
                material == Material.CARROTS ||
                material == Material.POTATOES) {
            return 7;
        }
        if (material == Material.BEETROOTS ||
                material == Material.NETHER_WART) {
            return 3;
        }
        if (material == Material.COCOA) {
            return 2;
        }
        return 0;
    }

    default void placeBlockAtLocation(Location location, Material material) {
        World world = location.getWorld();
        Block block = world.getBlockAt(location);
        block.setType(material);
    }

    default boolean isInWater(Player player) {
        Location loc = player.getLocation();
        Block block = loc.getBlock();
        return block.getType() == Material.WATER || block.getType() == Material.WATER;
    }

    default List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> nearbyBlocks = new ArrayList<>();
        World world = null;
        try {
            world = location.getWorld();
        } catch (Exception e) {
            Bukkit.getLogger().info("World not found for 'getNearbyBlocks'");
        }


        if (world != null) {
            int minX = location.getBlockX() - radius;
            int minY = location.getBlockY() - radius;
            int minZ = location.getBlockZ() - radius;
            int maxX = location.getBlockX() + radius;
            int maxY = location.getBlockY() + radius;
            int maxZ = location.getBlockZ() + radius;
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        Block block = world.getBlockAt(x, y, z);
                        nearbyBlocks.add(block);
                    }
                }
            }
        }
        return nearbyBlocks;
    }

    default List<Block> getSameLevelBlocks(Location location, int radius) {
        List<Block> nearbyBlocks = new ArrayList<>();
        World world = null;
        try {
            world = location.getWorld();
        } catch (Exception e) {
            Bukkit.getLogger().info("World not found for 'getNearbyBlocks'");
        }


        if (world != null) {
            int minX = location.getBlockX() - radius;
            int minZ = location.getBlockZ() - radius;
            int maxX = location.getBlockX() + radius;
            int maxZ = location.getBlockZ() + radius;
            int y = location.getBlockY();
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    nearbyBlocks.add(block);
                }

            }
        }
        return nearbyBlocks;
    }

    default Entity getNearestEntity(Player player) {
        double lowestDistance = 32;
        Entity closestEntity = null;

        for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
            if (entity == (Entity) player) continue;
            double distance = entity.getLocation().distance(player.getLocation());
            if (distance < lowestDistance) {
                lowestDistance = distance;
                closestEntity = entity;
            }
        }

        return closestEntity;
    }

    default boolean hasClearLineOfSight(Location start, Location end) {
        World world = start.getWorld();
        if (world == null) return false;

        Vector direction = end.toVector().subtract(start.toVector()).normalize();
        double distance = start.distance(end);
        Location loc = start.clone();

        for (int i = 0; i < distance; i++) {
            loc.add(direction);
            Block block = loc.getBlock();
            if (!(block.isPassable())) {
                return false;
            }
        }

        return true;
    }

    default Collection<ItemStack> smelt(Collection<ItemStack> collection) {
        Collection<ItemStack> smeltedItems = new ArrayList<>();

        for (ItemStack x : collection) {
            Material smeltedType = null;

            switch (x.getType()) {
                case RAW_IRON -> smeltedType = Material.IRON_INGOT;
                case RAW_IRON_BLOCK -> smeltedType = Material.IRON_BLOCK;
                case RAW_COPPER -> smeltedType = Material.COPPER_INGOT;
                case RAW_COPPER_BLOCK -> smeltedType = Material.COPPER_BLOCK;
                case RAW_GOLD -> smeltedType = Material.GOLD_INGOT;
                case RAW_GOLD_BLOCK -> smeltedType = Material.GOLD_BLOCK;
                case COBBLESTONE -> smeltedType = Material.STONE;
                case COBBLED_DEEPSLATE -> smeltedType = Material.DEEPSLATE;
                default -> smeltedType = x.getType();
            }

            ItemStack smeltedItem = new ItemStack(smeltedType, x.getAmount());
            smeltedItems.add(smeltedItem);
        }

        return smeltedItems;
    }

    default boolean isArmor(Material material) {
        String materialName = material.name();
        return materialName.endsWith("_HELMET") ||
                materialName.endsWith("_CHESTPLATE") ||
                materialName.endsWith("_LEGGINGS") ||
                materialName.endsWith("_BOOTS");
    }

    default void treeCap(Block block) {
        if (!isLog(block.getType())) {
            return;
        }
        breakAdjacentLogs(block);
    }

    default void breakAdjacentLogs(Block block) {
        if (!isLog(block.getType())) {
            return;
        }

        block.breakNaturally();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }
                    Block relative = block.getRelative(x, y, z);
                    if (isLog(relative.getType())) {
                        breakAdjacentLogs(relative);
                    }
                }
            }
        }
    }

    default List<String> generateGradient(String startColor, String endColor, int steps) {
        List<String> gradientColors = new ArrayList<>();

        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);

        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);

        double redStep = (double) (endRed - startRed) / (steps - 1);
        double greenStep = (double) (endGreen - startGreen) / (steps - 1);
        double blueStep = (double) (endBlue - startBlue) / (steps - 1);

        for (int i = 0; i < steps; i++) {
            int red = (int) (startRed + i * redStep);
            int green = (int) (startGreen + i * greenStep);
            int blue = (int) (startBlue + i * blueStep);

            String hexColor = String.format("%02X%02X%02X", red, green, blue);
            gradientColors.add("§x§" + hexColor);
        }

        return gradientColors;
    }

    default String gradString(String str, String startColor, String endColor) {
        String finalString = "";
        List<String> colours = generateGradient(startColor, endColor, str.length());
        String[] chars = str.split("(?!^)");
        for (int i = 0; i < str.length(); i++) {
            String finalColor = colours.get(i);
            StringBuilder colorBuilder = new StringBuilder();
            for (char c : finalColor.toCharArray()) {
                colorBuilder.append("§").append(c);
            }
            finalString += colorBuilder.toString() + chars[i];
        }
        return finalString;
    }


    default void sendGradString(CommandSender sender, String str, String startColor, String endColor) {
        List<String> colours = generateGradient(startColor, endColor, str.length());
        String[] chars = str.split("");
        for (int i = 0; i < str.length(); i++) {
            ChatColor color = ChatColor.getByChar(colours.get(i));
            sender.sendMessage(color + chars[i]);
        }
    }

    default boolean hasPermission(Player player, String perm) {
        if (player.getPersistentDataContainer().get(NamespacedKeys.getKey(perm), PersistentDataType.STRING) == null)
            return false;
        String detectPerm = player.getPersistentDataContainer().get(NamespacedKeys.getKey(perm), PersistentDataType.STRING);
        if (detectPerm.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    default String scramble(String hi) {
        List<Character> chars = new ArrayList<>();

        for (char c : hi.toCharArray()) {
            chars.add(c);
        }
        Collections.shuffle(chars);

        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            result.append(c);
        }
        return result.toString();


    }

    default void addOrDrop(Player player, ItemStack item) {
        boolean x = false;
        for (int i = 0; i < player.getInventory().getSize() - 5; i++) {
            if (player.getInventory().getItem(i) == null) {
                player.getInventory().setItem(i, item);
                x = true;
                break;
            } else {
                continue;
            }
        }
        if (x == false) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    }

    default String invalidArg(int index, String[] argument) {
        return ("§6" + argument[index] + "§r §c<-- Invalid argument at position §6" + (index + 1));
    }

    default World getSpawnWorld() {
        return Bukkit.getWorlds().get(0);
    }

    default long getWorldTime() {
        return getSpawnWorld().getTime();
    }

    default boolean isDiggable(Material material) {
        switch (material) {
            case CLAY, DIRT, COARSE_DIRT, PODZOL, FARMLAND, GRASS_BLOCK, GRAVEL, MYCELIUM, SAND, RED_SAND, SNOW_BLOCK,
                 SOUL_SAND, SOUL_SOIL, DIRT_PATH -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

}

