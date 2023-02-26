package me.redtea.carcadex.repo.impl.map;

import me.redtea.carcadex.repo.Repo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class MapRepo<K, V> implements Repo<K, V> {
    protected final Map<K, V> data = new HashMap<>();

    @Override
    public Collection<V> all() {
        return data.values();
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(data.get(key));
    }


    @Override
    public void close() {
        data.clear();
    }
}
