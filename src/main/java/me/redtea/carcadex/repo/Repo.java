package me.redtea.carcadex.repo;

import me.redtea.carcadex.reload.Reloadable;
import me.redtea.carcadex.repo.builder.RepoBuilder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface Repo<K, V> extends Reloadable {
    /**
     * @return all elements of repo
     */
    Collection<V> all();

    /**
     * @param key key of object
     * @return object by key from repo
     */
    Optional<V> get(@NotNull K key);

    /**
     * find objects in repo by predicate
     */
    Collection<V> find(Predicate<V> predicate);

    /**
     * find object in repo by predicate
     */
    Optional<V> findAny(Predicate<V> predicate);

    /**
     *
     * @return Repo builder
     * @param <K> key type
     * @param <V> value type
     */
    static<K, V> RepoBuilder<K, V> builder() {
        return RepoBuilder.get();
    }

    static<K, V> RepoBuilder<K, V> builder(Plugin plugin, String foldername) {
        return RepoBuilder.<K, V>of(plugin).folder(foldername);
    }

    static<K, V> RepoBuilder<K, V> builder(File file) {
        return RepoBuilder.of(file);
    }
}