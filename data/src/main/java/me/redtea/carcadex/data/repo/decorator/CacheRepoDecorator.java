package me.redtea.carcadex.data.repo.decorator;

import me.redtea.carcadex.data.repo.impl.CacheRepo;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class CacheRepoDecorator<K, V> implements CacheRepo<K, V> {
    protected CacheRepo<K, V> repo;

    protected CacheRepoDecorator(CacheRepo<K, V> repo) {
        this.repo = repo;
    }

    @Override
    public V set(@NotNull K key, @NotNull V value) {
        return repo.set(key, value);
    }

    @Override
    public V remove(@NotNull K key) {
        return repo.remove(key);
    }

    @Override
    public void saveAll() {
        repo.saveAll();
    }

    @Override
    public Collection<V> all() {
        return repo.all();
    }

    @Override
    public Optional<V> get(@NotNull K key) {
        return repo.get(key);
    }

    @Override
    public void loadToCache(K k) {
        repo.loadToCache(k);
    }

    @Override
    public void removeFromCache(K k) {
        repo.remove(k);
    }

    @Override
    public Collection<V> find(Predicate<V> predicate) {
        return repo.find(predicate);
    }

    @Override
    public Optional<V> findAny(Predicate<V> predicate) {
        return repo.findAny(predicate);
    }

    @Override
    public void clearCache() {
        repo.clearCache();
    }

    @Override
    public void clear() {
        repo.clear();
    }

    @Override
    public int cacheSize() {
        return repo.cacheSize();
    }

    @Override
    public void close() {
        repo.close();
    }
}
