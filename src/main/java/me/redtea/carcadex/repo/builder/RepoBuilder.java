package me.redtea.carcadex.repo.builder;

import me.redtea.carcadex.repo.MutableRepo;
import me.redtea.carcadex.repo.builder.impl.RepoBuilderImpl;
import me.redtea.carcadex.schema.SchemaStrategy;
import me.redtea.carcadex.serializer.CommonSerializer;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;

public interface RepoBuilder<K, V> {
    RepoBuilder<K, V> folder(File folder);
    RepoBuilder<K, V> folder(String filename); //requires plugin. didnt need if dir set
    RepoBuilder<K, V> plugin(Plugin plugin);
    RepoBuilder<K, V> serializer(CommonSerializer<V> serializer);
    RepoBuilder<K, V> binary(); //requires that V extends Serializable
    RepoBuilder<K, V> schema(SchemaStrategy<K,V> schemaStrategy);
    RepoBuilder<K, V> logging();
    RepoBuilder<K, V> debugLogging();
    RepoBuilder<K, V> logging(Logger logger);
    RepoBuilder<K, V> debugLogging(Logger logger);
    RepoBuilder<K, V> autoSave(long period); //in milisec
    RepoBuilder<K, V> sync();
    RepoBuilder<K, V> cacheCollectionAll();
    RepoBuilder<K, V> concurrent();

    MutableRepo<K, V> build();

    static<K, V> RepoBuilder<K, V> get() {
        return new RepoBuilderImpl<>();
    }

    static <K, V> RepoBuilder<K, V> of(File folder) {
        return RepoBuilder.<K, V>get().folder(folder);
    }

    static <K, V> RepoBuilder<K, V> of(Path folder) {
        return RepoBuilder.<K, V>get().folder(folder.toFile());
    }

    static <K, V> RepoBuilder<K, V> of(Plugin plugin) {
        return RepoBuilder.<K, V>get().plugin(plugin);
    }
}
