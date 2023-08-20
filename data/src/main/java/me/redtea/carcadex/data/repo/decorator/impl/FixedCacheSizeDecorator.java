package me.redtea.carcadex.data.repo.decorator.impl;

import me.redtea.carcadex.data.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.data.repo.impl.CacheRepo;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FixedCacheSizeDecorator<K, V> extends CacheRepoDecorator<K, V> {
    protected final int maxSize;
    private final float cleanFactor; //сколько % кеша будет очищено
    private final Map<K, Date> lastUpdateTime = new HashMap<>();

    public FixedCacheSizeDecorator(CacheRepo<K, V> repo, int maxSize) {
        this(repo, maxSize, 0.5f);
    }
    public FixedCacheSizeDecorator(CacheRepo<K, V> repo, int maxSize, float cleanFactor) {
        super(repo);
        this.maxSize = maxSize;
        if(cleanFactor <= 0 || cleanFactor > 1) throw new IllegalArgumentException("Factor must be more than 0.0 and less than 1.0");
        this.cleanFactor = cleanFactor;
    }

    @Override
    public Optional<V> get(@NotNull K key) {
        Optional<V> res = super.get(key);
        if(res.isPresent()) {
            onCacheAdd();
            update(key);
        }
        return res;
    }

    @Override
    public V set(@NotNull K key, @NotNull V value) {
        onCacheAdd();
        update(key);
        return super.set(key, value);
    }

    @Override
    public void loadToCache(K key) {
        onCacheAdd();
        update(key);
        super.loadToCache(key);
    }

    private void onCacheAdd() {
        if(cacheSize() > maxSize) {
            if(cleanFactor == 1.0f) {
                clearCache();
                return;
            }
            lastUpdateTime.entrySet().parallelStream()
                    .sorted(Map.Entry.comparingByValue())
                    .skip((long) Math.max(0, lastUpdateTime.size() * cleanFactor))
                    .map(Map.Entry::getKey)
                    .forEach(this::removeFromCache);
        }
    }


    private void update(K key) {
        lastUpdateTime.put(key, new Date());
    }


    @Override
    public V remove(@NotNull K key) {
        lastUpdateTime.remove(key);
        return super.remove(key);
    }

    @Override
    public void removeFromCache(K key) {
        lastUpdateTime.remove(key);
        super.removeFromCache(key);
    }

    @Override
    public void clearCache() {
        lastUpdateTime.clear();
        super.clearCache();
    }

    @Override
    public void clear() {
        lastUpdateTime.clear();
        super.clear();
    }

    @Override
    public void close() {
        lastUpdateTime.clear();
        super.close();
    }
}
