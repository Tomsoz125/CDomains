package xyz.tomsoz.cdomains;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tomsoz.cdomains.Commands.MainCommand;
import xyz.tomsoz.cdomains.Events.PlayerLogin;
import xyz.tomsoz.cdomains.Misc.ConfigManager;
import xyz.tomsoz.cdomains.Misc.Metrics;
import xyz.tomsoz.cdomains.Misc.PAPI;
import xyz.tomsoz.cdomains.Misc.Utils;

import java.util.HashMap;

public final class CDomains extends JavaPlugin {
    public HashMap<Player, String> connections = new HashMap<>();
    public ConfigManager config = new ConfigManager(this);
    @Override
    public void onEnable() {
        setupConfigs();
        registerEvents();
        registerCommands();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PAPI(this).register();
        }
        new Metrics(this, 13385);
        PluginDescriptionFile data = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(Utils.chat("&a"+data.getFullName()+" v"+data.getVersion()+" by "+data.getAuthors().get(0)+" has successfully enabled."));
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile data = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(Utils.chat("&c"+data.getFullName()+" v"+data.getVersion()+" by "+data.getAuthors().get(0)+" has successfully disabled."));
    }

    public ConfigManager getConfigurationManager() {
        return config;
    }

    public void setupConfigs() {
        config.saveDefaultConfig();
    }

    public void registerEvents() {
        registerEvent(new PlayerLogin(this));
    }

    public void registerCommands() {
        registerCommand(new MainCommand(this), "cdomains");
    }

    public void registerCommand(CommandExecutor file, String cmd) {
        getCommand(cmd).setExecutor(file);
    }

    public void registerEvent(Listener file) {
        Bukkit.getPluginManager().registerEvents(file, this);
    }
}
