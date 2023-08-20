package me.redtea.carcadex.data.repo;

import org.jetbrains.annotations.NotNull;

public interface MutableRepo<K, V> extends Repo<K, V> {

    /**
     * Updates/saves object in repo
     * @param value - object to update
     * @return updated object
     */
    V set(@NotNull K key, @NotNull V value);

    /**
     * Removes object from repo by key
     * @param key - key of object
     * @return removed object
     */
    V remove(@NotNull K key);

    /**
     * Removes all objects in Repo
     */
    void clear();

    /**
     * Saves all cache to storage
     */
    void saveAll();

}
