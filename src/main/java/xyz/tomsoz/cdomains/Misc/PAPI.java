package xyz.tomsoz.cdomains.Misc;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import xyz.tomsoz.cdomains.CDomains;

import java.util.concurrent.CountDownLatch;

public class PAPI extends PlaceholderExpansion {
    CDomains plugin;
    public PAPI(CDomains plugin) {
        this.plugin=plugin;
    }

    @Override
    public String getAuthor() {
        return "Tomsoz";
    }

    @Override
    public String getIdentifier() {
        return "CDomains";
    }

    @Override
    public String getVersion() {
        return "${project.version}";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        if (params.equalsIgnoreCase("domain")){
            return plugin.connections.get(p);
        }
        return null;
    }
}
