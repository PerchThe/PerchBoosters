package dev.ev1dent.perchboosters.commands.tabcompletion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PerchBoosterTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete( CommandSender sender,  Command command,  String label,  String[] args) {
        final List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1 ->{
                StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList("reload", "reset"), completions);
                Collections.sort(completions);
                return completions;
            }
            case 2 ->{
                if(args[0].equalsIgnoreCase("reload")) return Collections.singletonList("");
                else if(args[0].equalsIgnoreCase("reset")) return null;
            }
            case 3 ->{
                if(args[0].equalsIgnoreCase("reload")) return null;
                StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList("first", "monthly", "all"), completions);
                Collections.sort(completions);
                return completions;

            }
        }
        return null;
    }
}
