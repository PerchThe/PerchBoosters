package dev.ev1dent.perchboosters.utilities;

import dev.ev1dent.perchboosters.BoosterPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

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
}
