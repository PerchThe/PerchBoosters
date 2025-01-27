package dev.ev1dent.perchboosters.utilities;

import dev.ev1dent.perchboosters.BoosterPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }

    public void loadConfig() {
        boosterPlugin().reloadConfig();
        FileConfiguration config = boosterPlugin().getConfig();
        boosterPlugin().saveDefaultConfig();
        boosterPlugin().boosterGroup = config.getString("BoosterGroup");
        boosterPlugin().keyAllCMD = (String) config.get("Key_All_Command");
        boosterPlugin().keyAllMessage = config.getString("Key_All_Message");
    }
}