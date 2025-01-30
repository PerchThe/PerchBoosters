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

        boosterPlugin().boosterGroup = config.getString("booster-group");
        boosterPlugin().dayOfMonth = config.getInt("day-of-month");

        boosterPlugin().firstBoostCommands = config.getStringList("first-boost-commands");
        boosterPlugin().returningBoostCommands = config.getStringList("returning-boost-commands");
    }
}