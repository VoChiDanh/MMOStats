package net.danh.mmostats.Event;

import io.lumine.mythic.lib.api.player.EquipmentSlot;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.player.modifier.ModifierSource;
import io.lumine.mythic.lib.player.modifier.ModifierType;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.danh.mmostats.MMOStats;
import net.danh.mmostats.Manager.PStats;
import net.danh.mmostats.Resource.Files;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class JoinQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (String stats : Objects.requireNonNull(Files.getConfig().getConfig().getConfigurationSection("stats")).getKeys(false)) {
            new StatModifier(MMOStats.getInstance().getDescription().getName(), stats.toUpperCase(), PStats.getStats(p, stats), ModifierType.FLAT, EquipmentSlot.OTHER, ModifierSource.OTHER).register(PlayerData.get(p.getUniqueId()).getMMOPlayerData());
            MMOStats.stats.put(p.getName() + "_" + stats, PStats.getStats(p, stats));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        for (String stats : Objects.requireNonNull(Files.getConfig().getConfig().getConfigurationSection("stats")).getKeys(false)) {
            new StatModifier(MMOStats.getInstance().getDescription().getName(), stats.toUpperCase(), MMOStats.stats.get(p.getName() + "_" + stats), ModifierType.FLAT, EquipmentSlot.OTHER, ModifierSource.OTHER).unregister(PlayerData.get(p.getUniqueId()).getMMOPlayerData());
            MMOStats.stats.remove(p.getName() + "_" + stats);
        }
    }
}
