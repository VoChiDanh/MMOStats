package net.danh.mmostats.CMD;

import net.danh.mmostats.API.CMD.CMDBase;
import net.danh.mmostats.API.Utils.File;
import net.danh.mmostats.Manager.PStats;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CMD extends CMDBase {

    public CMD() {
        super("mmostats");
    }

    @Override
    public void execute(CommandSender c, String[] args) {
        if (c.hasPermission("mmostats.admin")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    File.reloadFiles(c);
                    PStats.updateStatsFile();
                    PStats.updateFormulaFile();
                }
                if (args[0].equalsIgnoreCase("update")) {
                    Bukkit.getOnlinePlayers().forEach(PStats::updateStats);
                }
            }
        }
    }

    @Override
    public List<String> TabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (sender.hasPermission("mmostats.admin")) {
            if (args.length == 1) {
                commands.add("reload");
                commands.add("update");
                StringUtil.copyPartialMatches(args[0], commands, completions);
            }
        }
        Collections.sort(completions);
        return completions;
    }

}