package me.redtea.carcadex.data.repo.impl.map;

import me.redtea.carcadex.data.repo.Repo;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Reload container:
 * data - Map.class
 * @param <K> key
 * @param <V> value
 */
public abstract class MapRepo<K, V> implements Repo<K, V> {
    protected Map<K, V> data;

    public MapRepo() {
        this(new HashMap<>());
    }

    public MapRepo(Map<K, V> defaults) {
        data = defaults;
    }


    @Override
    public Collection<V> all() {
        return data.values();
    }

    @Override
    public Optional<V> get(@NotNull K key) {
        return Optional.ofNullable(data.get(key));
    }


    @Override
    public void close() {
        data.clear();
    }

    @Override
    public Collection<V> find(Predicate<V> predicate) {
        return all().parallelStream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Optional<V> findAny(Predicate<V> predicate) {
        return all().parallelStream().filter(predicate).findAny();
    }


}
