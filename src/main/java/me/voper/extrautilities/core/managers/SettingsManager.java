package me.voper.extrautilities.core.managers;

import lombok.AllArgsConstructor;
import me.voper.extrautilities.ExtraUtilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.NumberConversions;

import javax.annotation.Nonnull;
import java.util.List;

public final class SettingsManager {

    private final ExtraUtilities plugin;
    private final FileConfiguration config;

    public SettingsManager(@Nonnull ExtraUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.config.options().copyDefaults(true);
        this.plugin.saveConfig();
    }

    public String getString(@Nonnull ConfigField field) {
        return config.getString(field.path, String.valueOf(field.defaultValue));
    }

    @Nonnull
    public List<String> getStringList(@Nonnull ConfigField field) {
        List<String> stringList = config.getStringList(field.path);

        if (stringList.isEmpty())
            stringList.add((String) field.defaultValue);

        return stringList;
    }

    public boolean getBoolean(@Nonnull ConfigField field) {
        return config.getBoolean(field.path, (Boolean) field.defaultValue);
    }

    public int getInt(@Nonnull ConfigField field) {
        return config.getInt(field.path, NumberConversions.toInt(field.defaultValue));
    }

    @AllArgsConstructor
    public enum ConfigField {

        AUTO_UPDATE("options.auto-update", true),
        DEBUG("options.debug", false),
        RESTORE("commands.restore", "restore|rs"),
        RESTORE_ALL("commands.restore-all", "restoreall|rsall"),
        SET_STORAGE_AMOUNT("commands.set-storage-amount", "setamount|sa"),
        SET_CONTENT("commands.set-content", "setcontent|sc");

        private final String path;
        private final Object defaultValue;

        ConfigField(String path) {
            this.path = path;
            this.defaultValue = null;
        }

    }

}
