package me.redtea.carcadex.repo.decorator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import me.redtea.carcadex.repo.impl.CacheRepo;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CacheRepoDecorator<K, V> implements CacheRepo<K, V> {
    protected final CacheRepo<K, V> repo;

    @Override
    public void init() {
        repo.init();
    }

    @Override
    public void close() {
        repo.close();
    }

    @Override
    public V update(@NotNull K key, @NotNull V value) {
        return repo.update(key, value);
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
}
