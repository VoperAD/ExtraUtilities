package me.voper.extrautilities.utils;

import me.voper.extrautilities.ExtraUtilities;

import java.util.logging.Level;
import java.util.logging.Logger;

import static me.voper.extrautilities.core.managers.SettingsManager.ConfigField.DEBUG;

public class Debug {

    private static final boolean isEnabled = ExtraUtilities.getInstance().getSettingsManager().getBoolean(DEBUG);
    private static final Logger logger = ExtraUtilities.getInstance().getLogger();

    public static void log(String message, Object... args) {
        if (isEnabled)
            logger.log(Level.INFO, "[DEBUG] " + message, args);
    }

}
