package me.orpheus.orpheuslite;

import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;
import java.util.HashSet;
import java.util.Set;

public class Config {
    // These replace the ForgeConfigSpec values
    public static boolean logDirtBlock = true;
    public static int magicNumber = 42;
    public static String magicNumberIntroduction = "The magic number is... ";
    public static Set<Item> items = new HashSet<>();

    /**
     * In Fabric, we use Identifier.of() to parse strings like "minecraft:iron_ingot"
     */
    public static void load() {
        // Example: adding an item to the set manually
        Item ironIngot = Registries.ITEM.get(Identifier.of("minecraft", "iron_ingot"));
        items.add(ironIngot);

        System.out.println(magicNumberIntroduction + magicNumber);
    }

    /**
     * Helper to check if an item string is valid in the Minecraft registry
     */
    private static boolean validateItemName(String itemName) {
        try {
            return Registries.ITEM.containsId(Identifier.of(itemName));
        } catch (Exception e) {
            return false;
        }
    }
}