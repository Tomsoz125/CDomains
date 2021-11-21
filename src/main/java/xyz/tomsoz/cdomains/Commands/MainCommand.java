package xyz.tomsoz.cdomains.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.tomsoz.cdomains.CDomains;
import xyz.tomsoz.cdomains.Misc.Utils;

public class MainCommand implements CommandExecutor {
    CDomains plugin;
    public MainCommand(CDomains plugin) {
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("cdomains")) {
            if (args.length == 0 || ((sender instanceof Player) ? !((Player)sender).hasPermission("cdomains.admin") : false)) {
                sender.sendMessage(Utils.chat("&9CDomains v"+plugin.getDescription().getVersion()+" &bby &9"+plugin.getDescription().getAuthors().get(0)));
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.getConfigurationManager().reloadConfig();
                    sender.sendMessage(Utils.chat("&bThe configuration file has successfully been reloaded."));
                }
            }
        }
        return false;
    }
}
