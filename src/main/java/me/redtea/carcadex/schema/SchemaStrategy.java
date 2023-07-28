package me.redtea.carcadex.schema;

import me.redtea.carcadex.reload.Reloadable;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface SchemaStrategy<K, V> extends Reloadable {
    Collection<V> all();

    V get(K key);

    void insert(K key, V value);

    void remove(K key);

    Collection<V> find(Predicate<V> predicate);
    Optional<V> findAny(Predicate<V> predicate);
}
