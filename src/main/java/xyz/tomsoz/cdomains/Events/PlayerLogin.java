package xyz.tomsoz.cdomains.Events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import xyz.tomsoz.cdomains.CDomains;
import xyz.tomsoz.cdomains.Misc.ConfigManager;
import xyz.tomsoz.cdomains.Misc.Utils;

public class PlayerLogin implements Listener {
    CDomains plugin;
    public PlayerLogin(CDomains plugin) {
        this.plugin=plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        ConfigManager manager = plugin.getConfigurationManager();
        FileConfiguration config = manager.getConfig();
        Player p = e.getPlayer();
        String ip = e.getHostname();
        String domain = ip.split(":")[0];
        if (config.getBoolean("logHostnames")) {
            Bukkit.getConsoleSender().sendMessage(Utils.chat("&a"+p.getDisplayName()+" &ahas joined with "+domain+"&a."));
        }
        plugin.connections.put(p, domain);
        for (String key : config.getConfigurationSection("hostnames").getKeys(false)) {
            if (config.getString("hostnames." + key + ".subdomain").equals(domain)) {
                boolean isCancelled = false;
                if (config.getBoolean("hostnames." + key + ".firstJoin")) {
                    if (p.hasPlayedBefore()) {
                        isCancelled = true;
                    }
                }
                if (!isCancelled) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        for (String s : config.getStringList("hostnames." + key + ".messages"))
                            p.sendMessage(Utils.chat(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(p, Utils.chat(s)) : Utils.chat(s)));
                        for (String s : config.getStringList("hostnames." + key + ".commands"))
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(p, s) : s);
                        for (String s : config.getStringList("hostnames." + key + ".userCommands"))
                            Bukkit.dispatchCommand(p, Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(p, "/"+s) : "/"+s);
                    }, 40L);
                }
            }
        }
    }
}
