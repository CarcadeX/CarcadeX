package me.redtea.carcadex.repo.factory;

import me.redtea.carcadex.common.CommonSerializer;
import me.redtea.carcadex.repo.MutableRepo;
import me.redtea.carcadex.repo.Repo;
import me.redtea.carcadex.repo.cache.CacheRepo;
import me.redtea.carcadex.repo.cache.decorator.impl.AutoSaveDecorator;
import me.redtea.carcadex.repo.cache.decorator.impl.CommonRepoDecorator;
import me.redtea.carcadex.repo.yaml.YamlRepo;
import me.redtea.carcadex.repo.yaml.strategy.ParseStrategy;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.nio.file.Path;

public interface RepoFactory {
    static <K, V> Repo<K, V> createYaml(Path file, Plugin plugin, ParseStrategy parseStrategy) {
        return new YamlRepo<K, V>(file, plugin, parseStrategy);
    }

    static <K, V> MutableRepo<K, V> createAutoSave(CommonSerializer<V> serializer, Path dir, Plugin plugin, long period) {
        return new AutoSaveDecorator<>((CacheRepo<K, V>) create(serializer, dir), plugin, 0, period);
    }

    static <K, V> MutableRepo<K, V> createAutoSave(CommonSerializer<V> serializer, Plugin plugin, long period) {
        return new AutoSaveDecorator<>((CacheRepo<K, V>) create(serializer, plugin), plugin, 0, period);
    }

    static <K, V> MutableRepo<K, V> create(CommonSerializer<V> serializer, Path dir) {
        return new CommonRepoDecorator<>(dir, serializer);
    }

    static <K, V> MutableRepo<K, V> create(CommonSerializer<V> serializer, Plugin plugin, String name) {
        return create(serializer, new File(plugin.getDataFolder(), name).toPath());
    }

    static <K, V> MutableRepo<K, V> create(CommonSerializer<V> serializer, Plugin plugin) {
        return create(serializer, new File(plugin.getDataFolder(), "saved-data").toPath());
    }

    static <K, V> MutableRepo<K, V> create(CommonSerializer<V> serializer) {
        return create(serializer, new File("saved-data").toPath());
    }
}
