package dev.ev1dent.perchrewards.commands.tabcompletion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PerchRewardsTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete( CommandSender sender,  Command command,  String label,  String[] args) {
        final List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1 ->{
                StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList("reload", "reset", "check"), completions);
                Collections.sort(completions);
                return completions;
            }
            case 2 ->{
                if(args[0].equalsIgnoreCase("reload")) return  Collections.emptyList();
                else return null;
            }
            case 3 ->{
                switch (args[0].toLowerCase()) {
                    case "reload" -> {
                        return null;
                    }
                    case "check" -> {
                        StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList("first", "monthly"), completions);
                        Collections.sort(completions);
                        return completions;
                    }
                    case "reset" -> {
                        StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList("first", "monthly", "all"), completions);
                        Collections.sort(completions);
                        return completions;
                    }

                }
            }
            case 4 -> {
                if(args[0].equalsIgnoreCase("check") && args[2].equalsIgnoreCase("monthly")){
                    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                    StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList(months), completions);
                    Collections.sort(completions);
                    return completions;
                }
                return Collections.emptyList();
            }
            case 5 -> {
                if(args[0].equalsIgnoreCase("check") && args[2].equalsIgnoreCase("monthly")){
                    String[] months = {"2024", "2025", "2026", "2027", "2028", "2029" };
                    StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList(months), completions);
                    Collections.sort(completions);
                    return completions;
                }
                return Collections.emptyList();
            }
        }
        return null;
    }
}
