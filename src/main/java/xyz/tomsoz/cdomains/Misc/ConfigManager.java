package xyz.tomsoz.cdomains.Misc;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.tomsoz.cdomains.CDomains;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    public CDomains plugin;
    public ConfigManager(CDomains plugin) {
        this.plugin=plugin;
    }

    File configFile;
    FileConfiguration configCfg;

    public FileConfiguration getConfig() {
        return configCfg;
    }

    public void saveConfig() {
        try {
            configCfg.save(configFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(Utils.chat("&cCouldn't save config.yml!\nIf this persists contact the plugin developer."));
        }
    }

    public void updateConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", true);
        }
        configCfg = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        configCfg = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadConfig() {
        configCfg = YamlConfiguration.loadConfiguration(configFile);
    }
}
