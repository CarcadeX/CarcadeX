package me.redtea.carcadex.data.repo.impl;

import me.redtea.carcadex.data.repo.MutableRepo;

public interface CacheRepo<K, V> extends MutableRepo<K, V> {
    /**
     * Load from permanent memory to cache by key
     * @param k key of object
     */
    void loadToCache(K k);
    /**
     * Remove object from cache by key
     * (not removes from repo)
     * @param k key of object
     */
    void removeFromCache(K k);

    /**
     * Clear cache of repo
     */
    void clearCache();

    int cacheSize();
}
