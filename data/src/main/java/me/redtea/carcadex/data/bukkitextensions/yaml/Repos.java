package me.redtea.carcadex.data.bukkitextensions.yaml;

import me.redtea.carcadex.data.repo.builder.RepoBuilder;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface Repos {
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
        return RepoBuilder.of(new File(plugin.getDataFolder(), foldername));
    }

    static<K, V> RepoBuilder<K, V> builder(File file) {
        return RepoBuilder.of(file);
    }
}
