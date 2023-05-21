package me.redtea.carcadex.util;

import org.bukkit.scheduler.BukkitRunnable;

@FunctionalInterface
public interface LBRunnable {
    void start();
    default BukkitRunnable get() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                start();
            }
        };
    }
}
