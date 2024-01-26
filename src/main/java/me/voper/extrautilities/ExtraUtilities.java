package me.voper.extrautilities;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import lombok.Getter;
import me.voper.extrautilities.core.Registry;
import me.voper.extrautilities.core.managers.CommandManager;
import me.voper.extrautilities.core.managers.SettingsManager;
import me.voper.extrautilities.core.managers.SupportedPluginsManager;
import me.voper.extrautilities.helper.impl.InfinityStorageHelper;
import me.voper.extrautilities.helper.impl.NetworksStorageHelper;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class ExtraUtilities extends JavaPlugin implements SlimefunAddon {

    private static ExtraUtilities ins;
    private Registry registry;
    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        ins = this;

        this.settingsManager = new SettingsManager(this);
        this.registry = new Registry();

        SupportedPluginsManager.ifSupported("InfinityExpansion", (p) -> registry.registerHelper(new InfinityStorageHelper()));
        SupportedPluginsManager.ifSupported("Networks", (p) -> registry.registerHelper(new NetworksStorageHelper()));

        CommandManager commandManager = new CommandManager(this);
        commandManager.setup();
    }

    @Override
    public void onDisable() {

    }

    @NotNull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return null;
    }

    public static ExtraUtilities getInstance() {
        return ins;
    }

}