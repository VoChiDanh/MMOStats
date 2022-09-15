package net.danh.mmostats.Manager;

import io.lumine.mythic.lib.api.player.EquipmentSlot;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.player.modifier.ModifierSource;
import io.lumine.mythic.lib.player.modifier.ModifierType;
import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.danh.dcore.Calculator.Calculator;
import net.danh.mmostats.MMOStats;
import net.danh.mmostats.Resource.Files;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import static net.danh.mmostats.Manager.Debug.debug;

public class PStats {

    static Set<String> stats_file = Objects.requireNonNull(Files.getConfig().getConfig().getConfigurationSection("stats")).getKeys(false);
    static Set<String> formula_file = Objects.requireNonNull(Files.getConfig().getConfig().getConfigurationSection("formula")).getKeys(false);

    public static void updateStats(Player p) {
        stats_file.forEach(stats -> {
            if (getStats(p, stats) != MMOStats.stats.getOrDefault(p.getName() + "_" + stats, 0)) {
                new StatModifier(MMOStats.getInstance().getDescription().getName(), stats.toUpperCase(), getStats(p, stats), ModifierType.FLAT, EquipmentSlot.OTHER, ModifierSource.OTHER).register(PlayerData.get(p.getUniqueId()).getMMOPlayerData());
                MMOStats.stats.put(p.getName() + "_" + stats, getStats(p, stats));
            }
        });
    }

    public static int getStats(Player p, String stats) {
        String stats_formula = Files.getConfig().getConfig().getString("stats." + stats);
        debug("stats formula =" + stats_formula);
        if (stats_formula == null) return 0;
        String papi = PlaceholderAPI.setPlaceholders(p, stats_formula);
        debug("papi =" + papi);
        if (papi.contains("#cf_")) {
            for (String formula : formula_file) {
                debug("formula = " + formula);
                String formula_string = Files.getConfig().getConfig().getString("formula." + formula);
                debug("String formula = " + formula_string);
                if (formula_string == null) return 0;
                String formula_papi = PlaceholderAPI.setPlaceholders(p, formula_string);
                debug("papi formula = " + formula_papi);
                String calculator = String.valueOf((int) BigDecimal.valueOf(Double.parseDouble(Calculator.calculator(formula_papi, 0))).doubleValue());
                debug("calculator double = " + calculator);
                int calculator_int = (int) Double.parseDouble(calculator);
                debug("calculator int = " + calculator_int);
                papi = papi.replaceAll("#cf_" + formula + "#", String.valueOf(calculator_int));
                papi = Calculator.calculator(papi, 0);
                if (!papi.contains("#cf_")) {
                    BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(papi));
                    debug(String.valueOf(bigDecimal.intValue()));
                    return bigDecimal.intValue();
                }
            }
        }
        return 0;
    }
}
