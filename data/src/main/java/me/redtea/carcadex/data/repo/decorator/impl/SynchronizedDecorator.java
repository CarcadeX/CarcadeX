package me.redtea.carcadex.data.repo.decorator.impl;

import me.redtea.carcadex.data.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.data.repo.impl.CacheRepo;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;


public class SynchronizedDecorator<K, V> extends CacheRepoDecorator<K, V> {

    private final CacheRepo<K, V> repo;

    public SynchronizedDecorator(CacheRepo<K, V> repo) {
        super(repo);
        this.repo = repo;
    }


    @Override
    public synchronized void loadToCache(K k) {
        super.loadToCache(k);
    }

    @Override
    public synchronized void removeFromCache(K k) {
        super.removeFromCache(k);
    }

    @Override
    public synchronized Collection<V> all() {
        return super.all();
    }

    @Override
    public synchronized Optional<V> get(@NotNull K key) {
        return super.get(key);
    }
    @Override
    public void saveAll() {
        super.saveAll();
    }

    @Override
    public synchronized V set(@NotNull K key, @NotNull V value) {
        V oldValue = repo.get(key).orElse(null);
        if (oldValue != null && !oldValue.equals(value)) {
            repo.set(key, value);
        }
        return value;
    }

    public synchronized V remove(@NotNull K key) {
        V oldValue = repo.get(key).orElse(null);
        if (oldValue != null) {
            repo.remove(key);
        }
        return oldValue;
    }
}
