package me.redtea.carcadex.repo;

import me.redtea.carcadex.reload.Reloadable;
import me.redtea.carcadex.repo.builder.RepoBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface Repo<K, V> extends Reloadable {
    Collection<V> all();

    Optional<V> get(@NotNull K key);

    static<K, V> RepoBuilder<K, V> builder() {
        return RepoBuilder.get();
    }
}