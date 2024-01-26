package me.voper.extrautilities.core.managers;

import co.aikar.commands.PaperCommandManager;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.voper.extrautilities.ExtraUtilities;
import me.voper.extrautilities.core.Registry;
import me.voper.extrautilities.core.commands.Commands;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.voper.extrautilities.core.managers.SettingsManager.ConfigField.*;

public class CommandManager extends PaperCommandManager {

    private final ExtraUtilities plugin;
    private final SettingsManager sm;

    public CommandManager(ExtraUtilities plugin) {
        super(plugin);
        this.plugin = plugin;
        this.sm = plugin.getSettingsManager();
    }

    public void setup() {
        enableUnstableAPI("help");
        registerDependency(Registry.class, plugin.getRegistry());
        registerCommandReplacements();
        registerCommandCompletions();
        registerCommand(new Commands());
    }

    private void registerCommandReplacements() {
        getCommandReplacements().addReplacements(
                "extrautilities", "extrautilities|eu",
                "restore", String.join("|", sm.getStringList(RESTORE)),
                "restoreall", String.join("|", sm.getStringList(RESTORE_ALL)),
                "setstorageamount", String.join("|", sm.getStringList(SET_STORAGE_AMOUNT)),
                "setcontent", String.join("|", sm.getStringList(SET_CONTENT))
        );
    }

    private void registerCommandCompletions() {
        getCommandCompletions().registerStaticCompletion("itemid", () -> {
            List<String> items = new ArrayList<>(Arrays.stream(Material.values()).map(Enum::name).toList());
            items.addAll(Slimefun.getRegistry().getEnabledSlimefunItems().stream().map(SlimefunItem::getId).toList());
            return items;
        });
    }

}
