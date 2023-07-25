package me.redtea.carcadex.schema;

import me.redtea.carcadex.reload.Reloadable;

import java.util.Collection;

public interface SchemaStrategy<K, V> extends Reloadable {
    Collection<V> all();

    V get(K key);

    void insert(K key, V value);

    void remove(K key);
}
