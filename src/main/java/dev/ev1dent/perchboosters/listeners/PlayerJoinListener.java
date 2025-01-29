package dev.ev1dent.perchboosters.listeners;

import dev.ev1dent.perchboosters.BoosterPlugin;
import dev.ev1dent.perchboosters.hooks.LPHook;
import dev.ev1dent.perchboosters.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDate;
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
        processGlobalKeyAll(container, player);
        processMonthlyKey(container, player);
    }

    private void processGlobalKeyAll(PersistentDataContainer container, Player player) {

        if(container.has(boosterPlugin().existingBooster, PersistentDataType.STRING)) return;
        container.set(boosterPlugin().existingBooster, PersistentDataType.STRING, "true");

        String keyAllCommand = boosterPlugin().keyAllCMD.replace("{PLAYER}", player.getName());
        boosterPlugin().getServer().dispatchCommand(boosterPlugin().getServer().getConsoleSender(), keyAllCommand);
        if(!boosterPlugin().keyAllMessage.isEmpty()){
            Bukkit.broadcast(Utils.formatMM(boosterPlugin().keyAllMessage));
        }
    }

    private void processMonthlyKey(PersistentDataContainer container, Player player) {
        Calendar calendar = Calendar.getInstance();
        if(!(calendar.get(Calendar.DAY_OF_MONTH) == boosterPlugin().dayOfMonth)) return;

        if(container.has(boosterPlugin().monthlyKey, PersistentDataType.STRING)) return;
        container.set(boosterPlugin().monthlyKey, PersistentDataType.STRING, "true");

        String keyPlayerCommand = boosterPlugin().keyPlayerCMD.replace("{PLAYER}", player.getName());
        boosterPlugin().getServer().dispatchCommand(boosterPlugin().getServer().getConsoleSender(), keyPlayerCommand);
        if(!boosterPlugin().keyPlayerMessage.isEmpty()){
            Bukkit.broadcast(Utils.formatMM(boosterPlugin().keyPlayerMessage));
        }

    }
}

