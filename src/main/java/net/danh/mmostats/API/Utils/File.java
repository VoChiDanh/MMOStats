package net.danh.mmostats.API.Utils;

import net.danh.mmostats.MMOStats;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class File {
    public static String getPrefix() {
        return Chat.colorize(getMessage().getString("prefix")) + " ";
    }

    public static FileConfiguration getConfig() {
        return MMOStats.getConfigurationHandler().file("", "config.yml");
    }

    public static FileConfiguration getMessage() {
        return MMOStats.getConfigurationHandler().file("", "message.yml");
    }

    public static void reloadFiles(CommandSender c) {
        MMOStats.getConfigurationHandler().reload("", "config.yml");
        MMOStats.getConfigurationHandler().reload("", "message.yml");
        Chat.sendCommandSenderMessage(c, getPrefix() + "&aReloaded");
    }
}
