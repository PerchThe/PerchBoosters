package dev.ev1dent.perchboosters.listeners;

import dev.ev1dent.perchboosters.BoosterPlugin;
import dev.ev1dent.perchboosters.utilities.Utils;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.AccountLinkedEvent;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AccountsLinkedListener {

    private BoosterPlugin boosterPlugin(){
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }
    Utils Utils = new Utils();

    @Subscribe
    public void accountsLinked(AccountLinkedEvent event) {
        Player player = (Player) event.getPlayer();
        if (!isFirstLink(player)) return;
        Utils.handleCommands(player, boosterPlugin().firstLinkCommands);
        isNoLongerFirstLink(player);

    }

    private boolean isFirstLink(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        return !container.has(boosterPlugin().alreadyLinked, PersistentDataType.BOOLEAN);
    }

    private void isNoLongerFirstLink(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(boosterPlugin().alreadyLinked, PersistentDataType.BOOLEAN, true);
    }
}
