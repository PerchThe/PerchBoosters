package dev.ev1dent.perchboosters.commands;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.ev1dent.perchboosters.BoosterPlugin;
import dev.ev1dent.perchboosters.utilities.ConfigManager;
import dev.ev1dent.perchboosters.utilities.Utils;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@SuppressWarnings({"UnstableApiUsage", "SameReturnValue"})
@NullMarked
public class CommandPerchBoosters {

    private final ConfigManager configManager = new ConfigManager();
    private final Utils Utils = new Utils();
    public static String[] CommandAliases = new String[] {"booster", "bp", "perchboosters", "pr", "perchrewards"};


    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }

    private final List<String> yearSuggestions = List.of("2024", "2025", "2026", "2027", "2028", "2029");
    private final SuggestionProvider<CommandSourceStack> yearSuggestionsProvider = (ctx, builder) -> {
        yearSuggestions.stream()
                .filter(amount -> amount.startsWith(builder.getRemaining()))
                .forEach(builder::suggest);
        return builder.buildFuture();
    };
    private final List<String> monthSuggestions = List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    private final SuggestionProvider<CommandSourceStack> monthSuggestionsProvider = (ctx, builder) -> {
        monthSuggestions.stream()
                .filter(amount -> amount.startsWith(builder.getRemaining()))
                .forEach(builder::suggest);
        return builder.buildFuture();
    };

    public LiteralCommandNode<CommandSourceStack> constructCommand() {
        return Commands.literal("perchboosters")
                .requires(source -> source.getSender().hasPermission("perchboosters.use"))
                .then(Commands.literal("reload")
                        .executes(ctx -> {
                            CommandSender sender = ctx.getSource().getSender();
                            configManager.loadConfig();
                            getSender(ctx).sendMessage(Utils.formatMM("<green>Reloading Config..."));
                            return Command.SINGLE_SUCCESS;
                        })
                )

                .then(Commands.literal("reset")
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .then(Commands.literal("first")
                                        .executes(this::resetFirst)
                                )
                                .then(Commands.literal("monthly")
                                        .then(Commands.argument("month", StringArgumentType.string())
                                                .suggests(monthSuggestionsProvider)
                                                .then(Commands.argument("year", IntegerArgumentType.integer())
                                                        .suggests(yearSuggestionsProvider)
                                                        .executes(this::resetMonthly)
                                                )
                                        )
                                )
                                .then(Commands.literal("linked")
                                        .executes(this::resetLinked)
                                )
                        )
                )
                .then(Commands.literal("check")
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .then(Commands.literal("first")
                                        .executes(this::checkFirst)
                                )
                                .then(Commands.literal("monthly")
                                        .then(Commands.argument("month", StringArgumentType.string())
                                                .suggests(monthSuggestionsProvider)
                                                .then(Commands.argument("year", IntegerArgumentType.integer())
                                                        .suggests(yearSuggestionsProvider)
                                                        .executes(this::checkMonthly)
                                                )
                                        )
                                )
                                .then(Commands.literal("linked")
                                        .executes(this::checkLinked)
                                )
                        )
                )
                .build();
    }

    public int resetFirst(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        if(getContainer(getPlayer(ctx)).has(boosterPlugin().existingBooster)) {
            getSender(ctx).sendMessage(Utils.formatMM(boosterPlugin().messagesResetFirst.replace("{0}", getPlayer(ctx).getName())));
            getContainer(getPlayer(ctx)).remove(boosterPlugin().existingBooster);
            return Command.SINGLE_SUCCESS;
        }
        getSender(ctx).sendMessage(Utils.formatMM("<red>" + getPlayer(ctx).getName() + " has not claimed booster rewards!"));
        return Command.SINGLE_SUCCESS;
    }

    public int resetMonthly(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {

        final String month = ctx.getArgument("month", String.class);
        final Integer year = ctx.getArgument("year", Integer.class);
        NamespacedKey newKey = new NamespacedKey(boosterPlugin(), month + "-" + year);
        if(getContainer(getPlayer(ctx)).has(newKey)) {
            getSender(ctx).sendMessage(Utils.formatMM(boosterPlugin().messagesResetMonthly.replace("{0}", getPlayer(ctx).getName())));
            getContainer(getPlayer(ctx)).remove(newKey);
            return Command.SINGLE_SUCCESS;
        }

        getSender(ctx).sendMessage(Utils.formatMM("<red>" + getPlayer(ctx).getName() + " has not claimed rewards for " + month + " " + year));
        return Command.SINGLE_SUCCESS;
    }

    public int resetLinked(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        if(getContainer(getPlayer(ctx)).has(boosterPlugin().alreadyLinked, PersistentDataType.BOOLEAN)) {
            getSender(ctx).sendMessage(Utils.formatMM(boosterPlugin().messagesResetLinked.replace("{0}", getPlayer(ctx).getName())));
            getContainer(getPlayer(ctx)).remove(boosterPlugin().alreadyLinked);
            return Command.SINGLE_SUCCESS;
        }

        getSender(ctx).sendMessage(Utils.formatMM("<red>" + getPlayer(ctx).getName() + " has not linked!"));
        return Command.SINGLE_SUCCESS;
    }

    public int checkFirst(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        if(getContainer(getPlayer(ctx)).has(boosterPlugin().existingBooster)) {
            getSender(ctx).sendMessage(Utils.formatMM("<green>" + getPlayer(ctx).getName() + " has claimed first rewards!"));
            return Command.SINGLE_SUCCESS;
        }

        getSender(ctx).sendMessage(Utils.formatMM("<red>" + getPlayer(ctx).getName() + " has not claimed first rewards!"));
        return Command.SINGLE_SUCCESS;
    }

    public int checkMonthly(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final String key = ctx.getArgument("month", String.class) + "-" + ctx.getArgument("year", Integer.class);
        if(getContainer(getPlayer(ctx)).has(new NamespacedKey(boosterPlugin(), key))) {
            getSender(ctx).sendMessage(Utils.formatMM("<green>" + getPlayer(ctx).getName() + " has claimed rewards for " + key + "!"));
            return Command.SINGLE_SUCCESS;
        }

        getSender(ctx).sendMessage(Utils.formatMM("<red>" + getPlayer(ctx).getName() + " has not claimed rewards for " + key + "!"));
        return Command.SINGLE_SUCCESS;
    }

    public int checkLinked(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        if(getContainer(getPlayer(ctx)).has(boosterPlugin().alreadyLinked)) {
            getSender(ctx).sendMessage(Utils.formatMM("<green>" + getPlayer(ctx).getName() + " Is linked, and claimed rewards!"));
            return Command.SINGLE_SUCCESS;
        }

        getSender(ctx).sendMessage(Utils.formatMM("<red>" + getPlayer(ctx).getName() + " Is not linked"));
        return Command.SINGLE_SUCCESS;
    }

    private Player getPlayer(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        return ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
    }

    private PersistentDataContainer getContainer(Player player){
        return player.getPersistentDataContainer();
    }

    private CommandSender getSender(CommandContext<CommandSourceStack> ctx){
        return ctx.getSource().getSender();
    }
}