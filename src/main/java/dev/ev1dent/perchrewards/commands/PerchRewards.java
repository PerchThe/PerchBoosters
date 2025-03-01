package dev.ev1dent.perchrewards.commands;

import dev.ev1dent.perchrewards.RewardPlugin;
import dev.ev1dent.perchrewards.utilities.ConfigManager;
import dev.ev1dent.perchrewards.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PerchRewards implements CommandExecutor {

    ConfigManager configManager = new ConfigManager();
    Utils Utils = new Utils();

    private RewardPlugin rewardPlugin() {
        return RewardPlugin.getPlugin(RewardPlugin.class);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            return false;
        }
        switch (args[0].toLowerCase()){
            case "reload" -> {
                configManager.loadConfig();
                sender.sendMessage(Utils.formatMM(rewardPlugin().messagesReloadedConfig));
                return true;
            }
            case "reset" -> {
                if(args.length < 2) return false;
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null) {
                    sender.sendMessage(Utils.formatMM(rewardPlugin().messagesPlayerNotFound));
                    return true;
                }
                switch (args[2].toLowerCase()){
                    case "monthly" -> {
                        PersistentDataContainer container = player.getPersistentDataContainer();
                        container.remove(rewardPlugin().monthlyKey);
                        String s = rewardPlugin().messagesResetMonthly;
                        sender.sendMessage(Utils.formatMM(s.replace("{0}", player.getName())));
                    }
                    case "first" -> {
                        PersistentDataContainer container = player.getPersistentDataContainer();
                        container.remove(rewardPlugin().existingBooster);
                        String s = rewardPlugin().messagesResetFirst;
                        sender.sendMessage(Utils.formatMM(s.replace("{0}", player.getName())));
                    }
                    case "all" ->{
                        PersistentDataContainer container = player.getPersistentDataContainer();
                        container.remove(rewardPlugin().existingBooster);
                        container.remove(rewardPlugin().monthlyKey);
                        String s = rewardPlugin().messagesResetAll;
                        sender.sendMessage(Utils.formatMM(s.replace("{0}", player.getName())));
                    }
                    default -> {
                        String s = rewardPlugin().messagesDefaultMessage;
                        sender.sendMessage(Utils.formatMM(s.replace("{0}", args[0])));
                    }
                }
            }
            case "check" -> {
                if(args.length < 3) return false;
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null) {
                    sender.sendMessage(Utils.formatMM(rewardPlugin().messagesPlayerNotFound));
                    return true;
                }
                PersistentDataContainer container = player.getPersistentDataContainer();
                switch (args[2].toLowerCase()){
                    case "monthly" -> {
                        if(args.length < 5) return false;
                        String month = args[3], year = args[4], key = month + "-" + year;
                        String s = rewardPlugin().messagesCheckMonthly;
                        if(container.has(new NamespacedKey(rewardPlugin(), key.toLowerCase()), PersistentDataType.STRING)) {
                            sender.sendMessage(Utils.formatMM(s.replace("{0}", player.getName()).replace("{1}", "").replace("{2}", month).replace("{3}", year)));
                            return true;
                        }
                        else {
                            sender.sendMessage(Utils.formatMM(s.replace("{0}", player.getName()).replace("{1}", "<red>not</red> ").replace("{2}", month).replace("{3}", year)));
                        }

                    }
                    case "first" -> {
                        String s = rewardPlugin().messagesExistingBooster;
                        if(container.has(rewardPlugin().existingBooster, PersistentDataType.STRING)) {
                            sender.sendMessage(Utils.formatMM(s.replace("{0}", player.getName()).replace("{1}", "")));
                            return true;
                        } else {
                            sender.sendMessage(Utils.formatMM(s.replace("{0}", player.getName()).replace("{1}", "<red>not</red> an ")));
                        }
                    }
                }
            }
            default -> {
                String s = rewardPlugin().messagesDefaultMessage;
                sender.sendMessage(Utils.formatMM(s.replace("{0}", args[0])));
            }
        }
        return true;
    }
}
