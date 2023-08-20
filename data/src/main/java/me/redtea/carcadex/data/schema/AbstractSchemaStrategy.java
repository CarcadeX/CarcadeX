package me.redtea.carcadex.data.schema;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractSchemaStrategy<K, V> implements SchemaStrategy<K, V>{
    @Override
    public Collection<V> find(Predicate<V> predicate) {
        return all().parallelStream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Optional<V> findAny(Predicate<V> predicate) {
        return all().parallelStream().filter(predicate).findAny();
    }
}
