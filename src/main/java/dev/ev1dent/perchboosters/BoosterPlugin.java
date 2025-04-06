package dev.ev1dent.perchboosters;

import dev.ev1dent.perchboosters.commands.CommandPerchBoosters;
import dev.ev1dent.perchboosters.listeners.AccountsLinkedListener;
import dev.ev1dent.perchboosters.listeners.PlayerJoinListener;
import dev.ev1dent.perchboosters.utilities.ConfigManager;
import github.scarsz.discordsrv.DiscordSRV;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.luckperms.api.LuckPerms;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDate;
import java.util.List;

public final class BoosterPlugin extends JavaPlugin {

    public LuckPerms luckPerms;
    private final ConfigManager configManager = new ConfigManager();
    private final AccountsLinkedListener accountsLinkedListener = new AccountsLinkedListener();

    public NamespacedKey existingBooster = new NamespacedKey(this, "existingBooster");
    public NamespacedKey monthlyKey = new NamespacedKey(this, monthlyKeyGen());
    public NamespacedKey alreadyLinked = new NamespacedKey(this, "alreadyLinked");

    public String boosterGroup;
    public List<String> firstBoostCommands, returningBoostCommands, firstLinkCommands;

    public boolean debugEnabled;

    // messages
    public String messagesReloadedConfig, messagesPlayerNotFound, messagesResetMonthly, messagesResetFirst, prefix, messagesResetAll, messagesDefaultMessage,messagesCheckMonthly, messagesExistingBooster, messagesCheckLinked, messagesResetLinked;

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onLoad() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register(new CommandPerchBoosters().constructCommand(), "booster command")
        );
    }

    @Override
    public void onEnable() {
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        DiscordSRV.api.subscribe(accountsLinkedListener);
        configManager.loadConfig();
        registerEvents();

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
