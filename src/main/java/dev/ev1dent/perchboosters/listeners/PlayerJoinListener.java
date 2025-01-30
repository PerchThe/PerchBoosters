package dev.ev1dent.perchboosters.listeners;

import dev.ev1dent.perchboosters.BoosterPlugin;
import dev.ev1dent.perchboosters.hooks.LPHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Calendar;

public class PlayerJoinListener implements Listener {

    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }
    LPHook LPHook = new LPHook();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        System.out.println(event.getPlayer().getName() + " joined the game");
        Player player = event.getPlayer();
        if(!LPHook.isBooster(player, boosterPlugin().boosterGroup)) return;
        System.out.println(LPHook.isBooster(player, boosterPlugin().boosterGroup));
        PersistentDataContainer container = player.getPersistentDataContainer();
        processBoost(container, player);

    }

    private void processBoost(PersistentDataContainer container, Player player) {
        Calendar calendar = Calendar.getInstance();
        if(!(calendar.get(Calendar.DAY_OF_MONTH) == boosterPlugin().dayOfMonth)) return;

        // Already received reward for current month, returning
        if(container.has(boosterPlugin().monthlyKey, PersistentDataType.STRING)) return;

        // if they didnt, they did now.
        container.set(boosterPlugin().monthlyKey, PersistentDataType.STRING, "true");
        System.out.println("FirstBoost: " + boosterPlugin().firstBoostCommands);

        for (String command : boosterPlugin().firstBoostCommands){
            command.replace("{PLAYER}", player.getName());
            boosterPlugin().getServer().dispatchCommand(boosterPlugin().getServer().getConsoleSender(), command.replace("{PLAYER}", player.getName()));
        }
        processReturningBooster(container, player);
    }

    private void processReturningBooster(PersistentDataContainer container, Player player) {

        if(container.has(boosterPlugin().existingBooster, PersistentDataType.STRING)) return;
        container.set(boosterPlugin().existingBooster, PersistentDataType.STRING, "true");

        System.out.println("ReturningBoost: " + boosterPlugin().returningBoostCommands);
        for (String command : boosterPlugin().returningBoostCommands){
            boosterPlugin().getServer().dispatchCommand(boosterPlugin().getServer().getConsoleSender(), command.replace("{PLAYER}", player.getName()));
        }
    }
}

