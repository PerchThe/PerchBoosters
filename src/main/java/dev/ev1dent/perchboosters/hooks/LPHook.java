package dev.ev1dent.perchboosters.hooks;

import dev.ev1dent.perchboosters.BoosterPlugin;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.entity.Player;

import java.util.Collection;

public class LPHook {

    private BoosterPlugin boosterPlugin() {
        return BoosterPlugin.getPlugin(BoosterPlugin.class);
    }

    public boolean isBooster(Player player, String groupName) {
        PlayerAdapter<Player> playerAdapter = boosterPlugin().getLuckPerms().getPlayerAdapter(Player.class);
        User user = playerAdapter.getUser(player);

        Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(player));
        return groups.stream().anyMatch(group -> group.getName().equalsIgnoreCase(groupName));
    }

}
