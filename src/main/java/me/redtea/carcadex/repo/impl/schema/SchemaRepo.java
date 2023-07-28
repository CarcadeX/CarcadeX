package me.redtea.carcadex.repo.impl.schema;

import me.redtea.carcadex.repo.impl.CacheRepo;
import me.redtea.carcadex.repo.impl.map.MapRepo;
import me.redtea.carcadex.schema.SchemaStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SchemaRepo<K, V> extends MapRepo<K, V> implements CacheRepo<K, V> {
    protected final Set<K> toRemove = new HashSet<>();

    protected final SchemaStrategy<K, V> schemaStrategy;

    public SchemaRepo(SchemaStrategy<K, V> schemaStrategy, Map<K, V> defaults) {
        super(defaults);
        this.schemaStrategy = schemaStrategy;
    }

    public SchemaRepo(SchemaStrategy<K, V> schemaStrategy) {
        this.schemaStrategy = schemaStrategy;
    }

    @Override
    public V set(@NotNull K key, @NotNull V value) {
        return data.put(key, value);
    }

    @Override
    public Collection<V> all() {
        return schemaStrategy.all();
    }

    @Override
    public Optional<V> get(@NotNull K key) {
        if(toRemove.contains(key)) return Optional.ofNullable(null);
        V result = data.get(key);
        if(result == null) try {
            loadToCache(key);
            result = data.get(key);
        } catch (Throwable ignored) {}
        return Optional.ofNullable(result);
    }

    @Override
    public V remove(@NotNull K key) {
        if(!get(key).isPresent()) throw new NoSuchElementException();
        toRemove.add(key);
        return data.remove(key);
    }

    @Override
    public void saveAll() {
        toRemove.forEach(schemaStrategy::remove);
        data.forEach(schemaStrategy::insert);
    }

    @Override
    public void loadToCache(K key) {
        data.put(key, schemaStrategy.get(key));
    }

    @Override
    public void removeFromCache(K key) {
        data.remove(key);
    }

    @Override
    public void init() {
        schemaStrategy.init();
    }

    @Override
    public void close() {
        saveAll();
        data.clear();
        toRemove.clear();
        schemaStrategy.close();
    }

    @Override
    public void reload() {
        saveAll();
        data.clear();
        schemaStrategy.reload();
        init();
    }


}
