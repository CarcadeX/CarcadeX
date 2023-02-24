package me.redtea.carcadex.repo;

import me.redtea.carcadex.reload.Reloadable;

import java.util.Collection;
import java.util.Optional;

public interface Repo<K, V> extends Reloadable {
    Collection<V> all();

    Optional<V> get(K key);
}