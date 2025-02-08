package dev.ev1dent.perchrewards.utilities;

import dev.ev1dent.perchrewards.RewardPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.logging.Level;

public class Utils {

    private RewardPlugin rewardPlugin() {
        return RewardPlugin.getPlugin(RewardPlugin.class);
    }


    public Component formatMM(String s){
        return MiniMessage.miniMessage().deserialize(s).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public void getLogger(String s) {
        if(!rewardPlugin().debugEnabled) return;
        rewardPlugin().getLogger().log(Level.INFO, s);
    }
}
