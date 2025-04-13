package dev.ev1dent.perchboosters.utilities;

import dev.ev1dent.perchboosters.BoosterPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;

public class Utils {

    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }

    public Component formatMM(String s){
        return MiniMessage.miniMessage().deserialize(s).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public void getLogger(String s) {
        if(!boosterPlugin().debugEnabled) return;
        boosterPlugin().getLogger().log(Level.INFO, s);
    }

    public void handleCommands(Player player, List<String> commands) {
        for (String command : commands) {
            if(command.startsWith("[message]")) {
                player.sendMessage(formatMM(command.replace("[message] ", "")));
                continue;
            }
            TaskChain.newChain().add(new TaskChain.GenericTask() {
                @Override
                protected void run() {
                    boosterPlugin().getServer().dispatchCommand(boosterPlugin().getServer().getConsoleSender(), command.replace("{PLAYER}", player.getName()));
                }
            }).execute();


        }
    }
}
