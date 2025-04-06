package dev.ev1dent.perchboosters.utilities;

import dev.ev1dent.perchboosters.BoosterPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ConfigManager {

    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }

    public void loadConfig() {
        boosterPlugin().reloadConfig();
        FileConfiguration config = boosterPlugin().getConfig();
        copyDefaults();

        boosterPlugin().boosterGroup = config.getString("booster-group");

        boosterPlugin().firstBoostCommands = config.getStringList("first-boost-commands");
        boosterPlugin().returningBoostCommands = config.getStringList("returning-boost-commands");
        boosterPlugin().firstLinkCommands = config.getStringList("first-link-commands");

        boosterPlugin().debugEnabled = config.getBoolean("DEBUG");

        // messages
        boosterPlugin().prefix = config.getString("messages.prefix");
        boosterPlugin().messagesReloadedConfig = boosterPlugin().prefix + config.getString("messages.reloaded-config");
        boosterPlugin().messagesPlayerNotFound = boosterPlugin().prefix + config.getString("messages.player-not-found");
        boosterPlugin().messagesResetMonthly = boosterPlugin().prefix + config.getString("messages.reset-monthly");
        boosterPlugin().messagesResetFirst = boosterPlugin().prefix + config.getString("messages.reset-first");
        boosterPlugin().messagesResetAll = boosterPlugin().prefix + config.getString("messages.reset-all");
        boosterPlugin().messagesCheckMonthly = boosterPlugin().prefix + config.getString("messages.check-monthly");
        boosterPlugin().messagesExistingBooster = boosterPlugin().prefix + config.getString("messages.existing-booster");
        boosterPlugin().messagesDefaultMessage = boosterPlugin().prefix + config.getString("messages.default-message");

    }

    private void copyDefaults(){
        final File configFile = new File(boosterPlugin().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            boosterPlugin().getLogger().info("Config Doesn't exist. Creating default config file.");
            boosterPlugin().saveResource("config.yml", false);
        }
    }
}