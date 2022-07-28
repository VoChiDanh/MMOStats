package net.danh.mmostats;

import io.lumine.mythic.lib.api.player.EquipmentSlot;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.player.modifier.ModifierSource;
import io.lumine.mythic.lib.player.modifier.ModifierType;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.danh.dcore.DCore;
import net.danh.mmostats.CMD.CMD;
import net.danh.mmostats.Event.JoinQuit;
import net.danh.mmostats.Manager.PStats;
import net.danh.mmostats.Resource.Files;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;

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
        getServer().getPluginManager().registerEvents(new JoinQuit(), this);
        new CMD();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (String stats : Objects.requireNonNull(Files.getConfig().getConfig().getConfigurationSection("stats")).getKeys(false)) {
                        if (PStats.getStats(p, stats) != MMOStats.stats.get(p.getName() + "_" + stats)) {
                            new StatModifier(MMOStats.getInstance().getDescription().getName(), stats.toUpperCase(), MMOStats.stats.get(p.getName() + "_" + stats), ModifierType.FLAT, EquipmentSlot.OTHER, ModifierSource.OTHER).unregister(PlayerData.get(p.getUniqueId()).getMMOPlayerData());
                            new StatModifier(MMOStats.getInstance().getDescription().getName(), stats.toUpperCase(), PStats.getStats(p, stats), ModifierType.FLAT, EquipmentSlot.OTHER, ModifierSource.OTHER).register(PlayerData.get(p.getUniqueId()).getMMOPlayerData());
                            MMOStats.stats.put(p.getName() + "_" + stats, PStats.getStats(p, stats));
                        }
                    }
                }
            }
        }.runTaskTimer(MMOStats.getInstance(), 20L, 20L);
    }

    @Override
    public void onDisable() {
        Files.getConfig().save();
    }
}