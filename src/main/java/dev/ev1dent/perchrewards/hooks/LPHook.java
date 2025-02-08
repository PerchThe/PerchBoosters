package dev.ev1dent.perchrewards.hooks;

import dev.ev1dent.perchrewards.RewardPlugin;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.entity.Player;

import java.util.Collection;

public class LPHook {

    private RewardPlugin rewardPlugin() {
        return RewardPlugin.getPlugin(RewardPlugin.class);
    }

    public boolean isBooster(Player player, String groupName) {
        PlayerAdapter<Player> playerAdapter = rewardPlugin().getLuckPerms().getPlayerAdapter(Player.class);
        User user = playerAdapter.getUser(player);

        Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(player));
        return groups.stream().anyMatch(group -> group.getName().equalsIgnoreCase(groupName));
    }

}
