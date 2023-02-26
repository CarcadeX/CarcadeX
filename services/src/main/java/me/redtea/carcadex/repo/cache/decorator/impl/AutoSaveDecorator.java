package me.redtea.carcadex.repo.cache.decorator.impl;

import me.redtea.carcadex.repo.cache.CacheRepo;
import me.redtea.carcadex.repo.cache.decorator.CacheRepoDecorator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class AutoSaveDecorator<K, V> extends CacheRepoDecorator<K, V> {
    public AutoSaveDecorator(CacheRepo<K, V> repo, Plugin plugin, long delay /*in ticks*/, long period /*in ticks*/) {
        super(repo);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::saveAll, delay, period);
    }
}
