package me.redtea.carcadex.repo.cache;

import me.redtea.carcadex.repo.MutableRepo;

public interface CacheRepo<K, V> extends MutableRepo<K, V> {
    void loadToCache(K k);

    void removeFromCache(K k);
}
