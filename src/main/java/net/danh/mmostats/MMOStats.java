package net.danh.mmostats;

import net.danh.mmostats.CMD.CMD;
import net.danh.mmostats.Manager.PStats;
import net.danh.mmostats.PlaceholderAPI.Placeholder;
import net.xconfig.bukkit.XConfigBukkit;
import net.xconfig.bukkit.config.BukkitConfigurationModel;
import net.xconfig.bukkit.impls.BukkitConfigurationModelImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class MMOStats extends JavaPlugin {

    public static final HashMap<String, Integer> stats = new HashMap<>();

    private static MMOStats instance;
    private static BukkitConfigurationModelImpl configurationHandler;

    public static MMOStats getInstance() {
        return instance;
    }

    public static BukkitConfigurationModel getConfigurationHandler() {
        return configurationHandler;
    }

    @Override
    public void onEnable() {
        instance = this;
        configurationHandler = XConfigBukkit.manager(instance);
        configurationHandler.build("", "config.yml");
        configurationHandler.build("", "message.yml");
        new CMD();
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholder().register();
        }
        PStats.updateStatsFile();
        PStats.updateFormulaFile();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> getServer().getOnlinePlayers().forEach(PStats::updateStats), 6000L, 6000L);
    }

    @Override
    public void onDisable() {
        configurationHandler.save("", "config.yml");
    }
}
