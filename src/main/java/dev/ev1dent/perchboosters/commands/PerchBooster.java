package dev.ev1dent.perchboosters.commands;

import dev.ev1dent.perchboosters.BoosterPlugin;
import dev.ev1dent.perchboosters.utilities.ConfigManager;
import dev.ev1dent.perchboosters.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PerchBooster implements CommandExecutor {

    ConfigManager configManager = new ConfigManager();
    Utils Utils = new Utils();

    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            return true;
        }
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
                switch (args[2].toLowerCase()){
                    case "month" -> {
                        PersistentDataContainer container = player.getPersistentDataContainer();
                        container.remove(boosterPlugin().existingBooster);
                        sender.sendMessage(Utils.formatMM("<green>Monthly Claim has been removed"));
                    }
                    case "first" -> {
                        PersistentDataContainer container = player.getPersistentDataContainer();
                        container.remove(boosterPlugin().monthlyKey);
                        sender.sendMessage(Utils.formatMM("<green>First Claim has been removed"));
                    }
                    case "all" ->{
                        PersistentDataContainer container = player.getPersistentDataContainer();
                        container.remove(boosterPlugin().existingBooster);
                        container.remove(boosterPlugin().monthlyKey);
                        sender.sendMessage(Utils.formatMM("<green>All Reward claims have been reset"));
                    }
                    default -> {
                        sender.sendMessage(Utils.formatMM("<red>Unknown Flag: " + args[2]));
                    }
                }
                
            }
            case "check" -> {
                if(args.length < 3) return false;
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null) {
                    sender.sendMessage(Utils.formatMM("<red>Player not found"));
                    return true;
                }
                PersistentDataContainer container = player.getPersistentDataContainer();
                switch (args[2].toLowerCase()){
                    case "monthly" -> {
                        if(args.length < 5) return false;
                        String month = args[3], year = args[4], key = month + "-" + year;
                        if(container.has(new NamespacedKey(boosterPlugin(), key.toLowerCase()), PersistentDataType.STRING)) {
                            sender.sendMessage(Utils.formatMM("<yellow> " + player.getName() + " <green>has received their rewards for " + month + " " + year));
                            return true;
                        }
                        else {
                            sender.sendMessage(Utils.formatMM("<yellow> " + player.getName() + " <green>has <red>not</red> received their rewards for " + month + " " + year));
                        }

                    }
                    case "first" -> {
                        if(container.has(boosterPlugin().existingBooster, PersistentDataType.STRING)) {
                            sender.sendMessage(Utils.formatMM("<yellow> " + player.getName() + " <green>Is an existing booster"));
                            return true;
                        } else sender.sendMessage(Utils.formatMM("<yellow> " + player.getName() + " <green>Is <red>not</red> an existing booster"));
                    }
                }


            }
        }
        return true;
    }
}
