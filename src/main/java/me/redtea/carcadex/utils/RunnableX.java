package me.redtea.carcadex.utils;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RunnableX {
    void start();
    default BukkitRunnable get() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                start();
            }
        };
    }

    /**
     * delegate to BukkitRunnable
     */


    @NotNull
    default BukkitTask runTask(@NotNull Plugin plugin) {
        return get().runTask(plugin);
    }

    @NotNull
    default BukkitTask runTaskAsynchronously(@NotNull Plugin plugin) {
        return get().runTaskAsynchronously(plugin);
    }

    @NotNull
    default BukkitTask runTaskLater(@NotNull Plugin plugin, long delay) {
        return get().runTaskLater(plugin, delay);
    }

    @NotNull
    default BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, long delay) {
        return get().runTaskLaterAsynchronously(plugin, delay);
    }

    @NotNull
    default BukkitTask runTaskTimer(@NotNull Plugin plugin, long delay, long period) {
        return get().runTaskTimer(plugin, delay, period);
    }

    @NotNull
    default BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, long delay, long period) {
        return get().runTaskTimerAsynchronously(plugin, delay, period);
    }


}
