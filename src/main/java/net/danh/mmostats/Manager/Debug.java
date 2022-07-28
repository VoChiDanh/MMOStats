package net.danh.mmostats.Manager;

import net.danh.dcore.DCore;
import net.danh.mmostats.Resource.Files;

public class Debug {

    public static void debug(String msg) {
        if (Files.getConfig().getConfig().getBoolean("debug")) {
            DCore.dCoreLog(msg);
        }
    }
}
