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
        processFirstBoost(container, player);
        processReturningBooster(container, player);

    }

    private void processFirstBoost(PersistentDataContainer container, Player player) {

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

        // Already received reward for current month, returning
        Utils.getLogger("Existing Booster: " + container.has(boosterPlugin().existingBooster, PersistentDataType.STRING));
        if(container.has(boosterPlugin().existingBooster, PersistentDataType.STRING)) return;

        // if they didnt, they did now.
        container.set(boosterPlugin().existingBooster, PersistentDataType.STRING, "true");

        for (String command : boosterPlugin().returningBoostCommands){
            Utils.getLogger("Returning Boost Commands: " + command.replace("{PLAYER}", player.getName()));
            boosterPlugin().getServer().dispatchCommand(boosterPlugin().getServer().getConsoleSender(), command.replace("{PLAYER}", player.getName()));
        }
    }
}

