package dev.ev1dent.perchboosters;

import dev.ev1dent.perchboosters.commands.PerchBooster;
import dev.ev1dent.perchboosters.commands.tabcompletion.PerchBoosterTabCompleter;
import dev.ev1dent.perchboosters.listeners.PlayerJoinListener;
import dev.ev1dent.perchboosters.utilities.ConfigManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDate;
import java.util.List;

public final class BoosterPlugin extends JavaPlugin {

    public LuckPerms luckPerms;
    ConfigManager configManager = new ConfigManager();

    public NamespacedKey existingBooster = new NamespacedKey(this, "existingBooster");
    public NamespacedKey monthlyKey = new NamespacedKey(this, monthlyKeyGen());

    public String boosterGroup;

    public List<String> firstBoostCommands, returningBoostCommands;

    public boolean debugEnabled;

    // messages
    public String messagesReloadedConfig, messagesPlayerNotFound, messagesResetMonthly, messagesResetFirst, prefix, messagesResetAll, messagesDefaultMessage,messagesCheckMonthly, messagesExistingBooster;

    @Override
    public void onEnable() {
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        configManager.loadConfig();
        registerCommands();
        registerEvents();

    }

    private void registerCommands(){
        this.getCommand("perchboosters").setExecutor(new PerchBooster());
        this.getCommand("perchboosters").setTabCompleter(new PerchBoosterTabCompleter());
    }

    private void registerEvents(){
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public String monthlyKeyGen(){
        String month = LocalDate.now().getMonth().toString();
        String year = String.valueOf(LocalDate.now().getYear());
        return month + "-" + year;
    }
}
