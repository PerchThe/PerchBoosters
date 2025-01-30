package dev.ev1dent.perchboosters.listeners;

import dev.ev1dent.perchboosters.BoosterPlugin;
import dev.ev1dent.perchboosters.hooks.LPHook;
import dev.ev1dent.perchboosters.utilities.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Calendar;

public class PlayerJoinListener implements Listener {

    Utils Utils = new Utils();


    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }
    LPHook LPHook = new LPHook();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Utils.getLogger(event.getPlayer().getName() + " joined the game");
        Player player = event.getPlayer();
        Utils.getLogger("Is Booster: " + LPHook.isBooster(player, boosterPlugin().boosterGroup));
        if(!LPHook.isBooster(player, boosterPlugin().boosterGroup)) return;
        PersistentDataContainer container = player.getPersistentDataContainer();
        processBoost(container, player);

    }

    private void processBoost(PersistentDataContainer container, Player player) {
        // this is processed any day, so it gets priority so it can't be cancelled.
        processReturningBooster(container, player);

        Calendar calendar = Calendar.getInstance();
        Utils.getLogger("Day of Month: " + calendar.get(Calendar.DAY_OF_MONTH));
        Utils.getLogger("Day of Month Expected: " + boosterPlugin().dayOfMonth);
        if(!(calendar.get(Calendar.DAY_OF_MONTH) == boosterPlugin().dayOfMonth)) return;

        // Already received reward for current month, returning
        Utils.getLogger("Already Claimed for Month: " + container.has(boosterPlugin().monthlyKey, PersistentDataType.STRING));
        if(container.has(boosterPlugin().monthlyKey, PersistentDataType.STRING)) return;

        // if they didnt, they did now.
        container.set(boosterPlugin().monthlyKey, PersistentDataType.STRING, "true");

        for (String command : boosterPlugin().firstBoostCommands){
            Utils.getLogger("First Boost Commands: " + command.replace("{PLAYER}", player.getName()));
            boosterPlugin().getServer().dispatchCommand(boosterPlugin().getServer().getConsoleSender(), command.replace("{PLAYER}", player.getName()));
        }
    }

    private void processReturningBooster(PersistentDataContainer container, Player player) {
        Utils.getLogger("Existing Booster: " + container.has(boosterPlugin().existingBooster, PersistentDataType.STRING));
        if(container.has(boosterPlugin().existingBooster, PersistentDataType.STRING)) return;
        container.set(boosterPlugin().existingBooster, PersistentDataType.STRING, "true");

        for (String command : boosterPlugin().returningBoostCommands){
            Utils.getLogger("Returning Boost Commands: " + command.replace("{PLAYER}", player.getName()));
            boosterPlugin().getServer().dispatchCommand(boosterPlugin().getServer().getConsoleSender(), command.replace("{PLAYER}", player.getName()));
        }
    }
}

