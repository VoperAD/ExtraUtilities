package me.voper.extrautilities.core.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class SupportedPluginsManager {

    public static boolean isSupported(String plugin) {
        return Bukkit.getPluginManager().isPluginEnabled(plugin);
    }

    public static void ifSupported(String plugin, Consumer<Plugin> consumer) {
        if (isSupported(plugin))
            consumer.accept(Bukkit.getPluginManager().getPlugin(plugin));
    }

}
