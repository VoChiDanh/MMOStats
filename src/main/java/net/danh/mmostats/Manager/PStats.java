package net.danh.mmostats.Manager;

import io.lumine.mythic.lib.api.player.EquipmentSlot;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.player.modifier.ModifierSource;
import io.lumine.mythic.lib.player.modifier.ModifierType;
import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.danh.mmostats.API.Calculator.Calculator;
import net.danh.mmostats.API.Utils.Chat;
import net.danh.mmostats.API.Utils.File;
import net.danh.mmostats.MMOStats;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static net.danh.mmostats.Manager.Debug.debug;

public class PStats {

    public static Set<String> stats_file = new HashSet<>();
    public static Set<String> formula_file = new HashSet<>();

    public static void updateStats(Player p) {
        stats_file.forEach(stats -> {
            if (getStats(p, stats) != MMOStats.stats.getOrDefault(p.getName() + "_" + stats, 0)) {
                StatModifier statModifier = new StatModifier(MMOStats.getInstance().getDescription().getName(), stats.toUpperCase(), getStats(p, stats), ModifierType.FLAT, EquipmentSlot.OTHER, ModifierSource.OTHER);
                statModifier.register(PlayerData.get(p.getUniqueId()).getMMOPlayerData());
                MMOStats.stats.put(p.getName() + "_" + stats, getStats(p, stats));
                Chat.sendPlayerMessage(p, File.getPrefix() + Objects.requireNonNull(File.getMessage().getString("message.updated")).replace("<name>", getStatsString(stats.toUpperCase())).replace("<amount>", String.valueOf(MMOStats.stats.get(p.getName() + "_" + stats))));
            }
        });
    }

    private static String getStatsString(String stats) {
        return File.getConfig().getString("name." + stats);
    }

    public static void updateStatsFile() {
        if (!stats_file.isEmpty()) {
            stats_file.clear();
        }
        stats_file.addAll(Objects.requireNonNull(File.getConfig().getConfigurationSection("stats")).getKeys(false));
    }

    public static void updateFormulaFile() {
        if (!formula_file.isEmpty()) {
            formula_file.clear();
        }
        formula_file.addAll(Objects.requireNonNull(File.getConfig().getConfigurationSection("formula")).getKeys(false));
    }

    public static int getStats(Player p, String stats) {
        String stats_formula = File.getConfig().getString("stats." + stats);
        debug("stats formula =" + stats_formula);
        if (stats_formula == null) return 0;
        String papi = PlaceholderAPI.setPlaceholders(p, stats_formula);
        debug("papi =" + papi);
        if (papi.contains("#cf_")) {
            for (String formula : formula_file) {
                debug("formula = " + formula);
                String formula_string = File.getConfig().getString("formula." + formula);
                debug("String formula = " + formula_string);
                if (formula_string == null) return 0;
                String formula_papi = PlaceholderAPI.setPlaceholders(p, formula_string);
                debug("papi formula = " + formula_papi);
                String calculator = String.valueOf((int) BigDecimal.valueOf(Double.parseDouble(Calculator.calculator(formula_papi, 0))).doubleValue());
                debug("calculator double = " + calculator);
                int calculator_int = (int) Double.parseDouble(calculator);
                debug("calculator int = " + calculator_int);
                papi = papi.replaceAll("#cf_" + formula + "#", String.valueOf(calculator_int));
                debug("papi = " + papi);
                if (!papi.contains("#cf_")) {
                    papi = Calculator.calculator(papi, 0);
                    debug("papi = " + papi);
                    BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(papi));
                    debug("Decimal = " + bigDecimal);
                    debug(String.valueOf(bigDecimal.intValue()));
                    return bigDecimal.intValue();
                }
            }
        }
        return 0;
    }
}
