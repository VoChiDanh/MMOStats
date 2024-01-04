package net.danh.mmostats;

import net.danh.mmostats.Utils.File;
import net.danh.mmostats.CMD.CMD;
import net.danh.mmostats.Manager.PStats;
import net.danh.mmostats.PlaceholderAPI.Placeholder;
import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class MMOStats extends JavaPlugin {

    public static final HashMap<String, Integer> stats = new HashMap<>();

    private static MMOStats instance;

    public static MMOStats getInstance() {
        return instance;
    }

    public static SimpleConfigurationManager getConfigurationHandler() {
        return SimpleConfigurationManager.get();
    }

    @Override
    public void onEnable() {
        instance = this;
        SimpleConfigurationManager.register(this);
        SimpleConfigurationManager.get().build("", false, "config.yml", "message.yml");
        new CMD();
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholder().register();
        }
        PStats.updateStatsFile();
        PStats.updateFormulaFile();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> getServer().getOnlinePlayers().forEach(PStats::updateStats), File.getConfig().getLong("settings.time"), File.getConfig().getLong("settings.time"));
    }

    @Override
    public void onDisable() {
        SimpleConfigurationManager.get().save("config.yml", "message.yml");
    }
}
