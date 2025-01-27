package dev.ev1dent.perchboosters;

import dev.ev1dent.perchboosters.commands.PerchBooster;
import dev.ev1dent.perchboosters.listeners.PlayerJoinListener;
import dev.ev1dent.perchboosters.utilities.ConfigManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class BoosterPlugin extends JavaPlugin {

    public LuckPerms luckPerms;
    ConfigManager configManager = new ConfigManager();
    public String boosterGroup;
    public String keyAllCMD;
    public String keyAllMessage;
    public NamespacedKey key = new NamespacedKey(this, "existingBooster");

    @Override
    public void onEnable() {
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        configManager.loadConfig();
        registerCommands();
        registerEvents();

    }

    private void registerCommands(){
        this.getCommand("boosterplugin").setExecutor(new PerchBooster());
    }

    private void registerEvents(){
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }
}
