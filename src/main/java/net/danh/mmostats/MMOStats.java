package net.danh.mmostats;

import net.danh.mmostats.CMD.CMD;
import net.danh.mmostats.Manager.PStats;
import net.danh.mmostats.PlaceholderAPI.Placeholder;
import net.danh.mmostats.Resource.Files;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class MMOStats extends JavaPlugin {

    public static final HashMap<String, Integer> stats = new HashMap<>();

    private static MMOStats instance;

    public static MMOStats getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Files.getConfig().load();
        new CMD();
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholder().register();
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> getServer().getOnlinePlayers().forEach(PStats::updateStats), 20L, 20L);
    }

    @Override
    public void onDisable() {
        Files.getConfig().save();
    }
}
