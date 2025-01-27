package dev.ev1dent.perchboosters.commands;

import dev.ev1dent.perchboosters.BoosterPlugin;
import dev.ev1dent.perchboosters.utilities.ConfigManager;
import dev.ev1dent.perchboosters.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PerchBooster implements CommandExecutor {

    ConfigManager configManager = new ConfigManager();

    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args[0].toLowerCase()){
            case "reload" -> {
                configManager.loadConfig();
                sender.sendMessage(Utils.formatMM("<green>Reloaded config"));
                return true;
            }
            case "reset" -> {
                if(args.length < 2) return true;
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null) {
                    sender.sendMessage(Utils.formatMM("<red>Player not found"));
                    return true;
                }
                player.getPersistentDataContainer().remove(boosterPlugin().key);
                sender.sendMessage(Utils.formatMM("<green>Player has been reset"));
            }
            default -> {
                sender.sendMessage(Utils.formatMM("<red>Unknown command '" + args[0] + "'"));
            }
        }
        return true;
    }
}
