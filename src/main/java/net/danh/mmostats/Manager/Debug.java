package net.danh.mmostats.Manager;

import net.danh.mmostats.Utils.File;
import net.danh.mmostats.MMOStats;

import java.util.logging.Level;

public class Debug {

    public static void debug(String msg) {
        if (File.getConfig().getBoolean("debug")) {
            MMOStats.getInstance().getLogger().log(Level.WARNING, msg);
        }
    }
}
