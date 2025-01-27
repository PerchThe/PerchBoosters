package dev.ev1dent.perchboosters.listeners;

import dev.ev1dent.perchboosters.BoosterPlugin;
import dev.ev1dent.perchboosters.utilities.Utils;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;

public class PlayerJoinListener implements Listener {

    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(!isBooster(player, boosterPlugin().boosterGroup)) return;


        PersistentDataContainer container = player.getPersistentDataContainer();


        if(container.has(boosterPlugin().key, PersistentDataType.STRING)) return;
        container.set(boosterPlugin().key, PersistentDataType.STRING, "true");

        boosterPlugin().getServer().dispatchCommand(boosterPlugin().getServer().getConsoleSender(), boosterPlugin().keyAllCMD);
        if(!boosterPlugin().keyAllMessage.isEmpty()){
            Bukkit.broadcast(Utils.formatMM(boosterPlugin().keyAllMessage));
        }

    }

    private boolean isBooster(Player player, String groupName) {
        PlayerAdapter<Player> playerAdapter = boosterPlugin().getLuckPerms().getPlayerAdapter(Player.class);
        User user = playerAdapter.getUser(player);

        Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(player));
        return groups.stream().anyMatch(group -> group.getName().equalsIgnoreCase(groupName));
    }
}
