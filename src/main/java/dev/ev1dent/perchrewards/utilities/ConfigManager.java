package dev.ev1dent.perchrewards.utilities;

import dev.ev1dent.perchrewards.RewardPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ConfigManager {

    private RewardPlugin rewardPlugin() {
        return RewardPlugin.getPlugin(RewardPlugin.class);
    }

    public void loadConfig() {
        rewardPlugin().reloadConfig();
        FileConfiguration config = rewardPlugin().getConfig();
        copyDefaults();

        rewardPlugin().boosterGroup = config.getString("booster-group");

        rewardPlugin().firstBoostCommands = config.getStringList("first-boost-commands");
        rewardPlugin().returningBoostCommands = config.getStringList("returning-boost-commands");

        rewardPlugin().debugEnabled = config.getBoolean("DEBUG");

        // messages
        rewardPlugin().prefix = config.getString("messages.prefix");
        rewardPlugin().messagesReloadedConfig = rewardPlugin().prefix + config.getString("messages.reloaded-config");
        rewardPlugin().messagesPlayerNotFound = rewardPlugin().prefix + config.getString("messages.player-not-found");
        rewardPlugin().messagesResetMonthly = rewardPlugin().prefix + config.getString("messages.reset-monthly");
        rewardPlugin().messagesResetFirst = rewardPlugin().prefix + config.getString("messages.reset-first");
        rewardPlugin().messagesResetAll = rewardPlugin().prefix + config.getString("messages.reset-all");
        rewardPlugin().messagesCheckMonthly = rewardPlugin().prefix + config.getString("messages.check-monthly");
        rewardPlugin().messagesExistingBooster = rewardPlugin().prefix + config.getString("messages.existing-booster");
        rewardPlugin().messagesDefaultMessage = rewardPlugin().prefix + config.getString("messages.default-message");

    }

    private void copyDefaults(){
        final File configFile = new File(rewardPlugin().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            rewardPlugin().getLogger().info("Config Doesn't exist. Creating default config file.");
            rewardPlugin().saveResource("config.yml", false);
        }
    }
}