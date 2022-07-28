package net.danh.mmostats.CMD;

import net.danh.dcore.Commands.CMDBase;
import net.danh.mmostats.MMOStats;
import net.danh.mmostats.Resource.Files;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CMD extends CMDBase {

    public CMD() {
        super(MMOStats.getInstance(), "mmostats");
    }

    @Override
    public void playerexecute(Player p, String[] args) {
        if (p.hasPermission("mmostats.admin")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Files.getConfig().save();
                    Files.getConfig().load();
                }
            }
        }
    }

    @Override
    public void consoleexecute(ConsoleCommandSender c, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                Files.getConfig().save();
                Files.getConfig().load();
            }
        }
    }

    @Override
    public List<String> TabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
