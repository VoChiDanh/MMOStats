package net.danh.mmostats.Resource;


import net.danh.mmostats.MMOStats;

public class Files {

    public static net.danh.dcore.Resource.Files getConfig() {
        return new net.danh.dcore.Resource.Files(MMOStats.getInstance(), "config");
    }
}
