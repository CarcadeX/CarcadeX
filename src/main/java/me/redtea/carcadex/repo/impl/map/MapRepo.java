package me.redtea.carcadex.repo.impl.map;

import me.redtea.carcadex.reload.parameterized.ParameterizedReloadable;
import me.redtea.carcadex.reload.parameterized.container.ReloadContainer;
import me.redtea.carcadex.repo.Repo;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Reload container:
 * data - Map.class
 * @param <K> key
 * @param <V> value
 */
public abstract class MapRepo<K, V> extends ParameterizedReloadable implements Repo<K, V> {
    protected Map<K, V> data = new HashMap<>();

    @Override
    public Collection<V> all() {
        return data.values();
    }

    @Override
    public Optional<V> get(@NotNull K key) {
        return Optional.ofNullable(data.get(key));
    }

    @Override
    public void init(ReloadContainer container) {
        if(container != null) data = container.get("data");
    }

    @Override
    public void close() {
        data.clear();
    }
}
