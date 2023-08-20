package me.redtea.carcadex.data.schema;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface SchemaStrategy<K, V> {
    Collection<V> all();

    V get(K key);

    void insert(K key, V value);

    void remove(K key);
    void removeAll();

    Collection<V> find(Predicate<V> predicate);
    Optional<V> findAny(Predicate<V> predicate);
    void close();
}
