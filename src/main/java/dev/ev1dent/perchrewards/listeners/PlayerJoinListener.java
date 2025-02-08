package dev.ev1dent.perchrewards.listeners;

import dev.ev1dent.perchrewards.RewardPlugin;
import dev.ev1dent.perchrewards.hooks.LPHook;
import dev.ev1dent.perchrewards.utilities.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {

    Utils Utils = new Utils();

    private RewardPlugin rewardPlugin() {
        return RewardPlugin.getPlugin(RewardPlugin.class);
    }
    LPHook LPHook = new LPHook();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Utils.getLogger(event.getPlayer().getName() + " joined the game");
        Player player = event.getPlayer();
        Utils.getLogger("Is Booster: " + LPHook.isBooster(player, rewardPlugin().boosterGroup));
        if(!LPHook.isBooster(player, rewardPlugin().boosterGroup)) return;
        PersistentDataContainer container = player.getPersistentDataContainer();
        processFirstBoost(container, player);
        processReturningBooster(container, player);

    }

    private void processFirstBoost(PersistentDataContainer container, Player player) {

        // Already received reward for current month, returning
        Utils.getLogger("Already Claimed for Month: " + container.has(rewardPlugin().monthlyKey, PersistentDataType.STRING));
        if(container.has(rewardPlugin().monthlyKey, PersistentDataType.STRING)) return;

        // if they didnt, they did now.
        container.set(rewardPlugin().monthlyKey, PersistentDataType.STRING, "true");

        for (String command : rewardPlugin().firstBoostCommands){
            Utils.getLogger("First Boost Commands: " + command.replace("{PLAYER}", player.getName()));
            handleCommands(command, player);
        }
    }

    private void processReturningBooster(PersistentDataContainer container, Player player) {

        // Already received reward for current month, returning
        Utils.getLogger("Existing Booster: " + container.has(rewardPlugin().existingBooster, PersistentDataType.STRING));
        if(container.has(rewardPlugin().existingBooster, PersistentDataType.STRING)) return;

        // if they didnt, they did now.
        container.set(rewardPlugin().existingBooster, PersistentDataType.STRING, "true");

        for (String command : rewardPlugin().returningBoostCommands){
            Utils.getLogger("Returning Boost Commands: " + command.replace("{PLAYER}", player.getName()));
            handleCommands(command, player);
        }
    }

    private void handleCommands(String command, Player player) {
        if(command.startsWith("[message]")) {
            player.sendMessage(Utils.formatMM(command.replace("[message] ", "")));
            return;
        }
        rewardPlugin().getServer().dispatchCommand(rewardPlugin().getServer().getConsoleSender(), command.replace("{PLAYER}", player.getName()));
    }
}

