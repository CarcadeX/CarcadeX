package me.redtea.carcadex.repo.decorator.impl;

import me.redtea.carcadex.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.repo.impl.CacheRepo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CacheAllDecorator<K, V> extends CacheRepoDecorator<K, V> {
    @Nullable
    protected Collection<V> allCache;

    public CacheAllDecorator(CacheRepo<K, V> repo) {
        super(repo);
    }

    @Override
    public Collection<V> all() {
        if(allCache != null) return allCache;
        saveAll();
        allCache = super.all();
        return allCache;
    }

    @Override
    public V set(@NotNull K key, @NotNull V value) {
        allCache = null;
        return super.set(key, value);
    }

    @Override
    public V remove(@NotNull K key) {
        allCache = null;
        return super.remove(key);
    }
}
