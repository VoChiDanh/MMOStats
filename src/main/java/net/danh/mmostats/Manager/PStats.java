package net.danh.mmostats.Manager;

import me.clip.placeholderapi.PlaceholderAPI;
import net.danh.dcore.Calculator.Calculator;
import net.danh.mmostats.Resource.Files;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Objects;

import static net.danh.mmostats.Manager.Debug.debug;

public class PStats {

    public static int getStatsFormula(Player p, String stats) {
        String formula = Files.getConfig().getConfig().getString("formula." + stats);
        if (formula != null) {
            String papi = PlaceholderAPI.setPlaceholders(p, formula);
            return (int) BigDecimal.valueOf(Long.parseLong(Calculator.calculator(papi, 0))).doubleValue();
        }
        return 0;
    }

    public static int getStats(Player p, String stats) {
        String stats_formula = Files.getConfig().getConfig().getString("stats." + stats);
        debug("stats formula =" + stats_formula);
        if (stats_formula == null) return 0;
        String papi = PlaceholderAPI.setPlaceholders(p, stats_formula);
        debug("papi =" + papi);
        if (papi.contains("#cf_")) {
            for (String formula : Objects.requireNonNull(Files.getConfig().getConfig().getConfigurationSection("formula")).getKeys(false)) {
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
                BigDecimal bigDecimal = BigDecimal.valueOf(Long.parseLong(papi.replaceAll("#cf_" + formula + "#", String.valueOf(calculator_int))));
                debug(String.valueOf(bigDecimal.intValue()));
                return bigDecimal.intValue();
            }
        }
        return 0;
    }
}
