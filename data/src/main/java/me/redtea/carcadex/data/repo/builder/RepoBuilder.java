package me.redtea.carcadex.data.repo.builder;

import me.redtea.carcadex.data.repo.MutableRepo;
import me.redtea.carcadex.data.serializer.CommonSerializer;
import me.redtea.carcadex.data.repo.builder.impl.RepoBuilderImpl;
import me.redtea.carcadex.data.schema.SchemaStrategy;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

public interface RepoBuilder<K, V> {
    RepoBuilder<K, V> folder(File folder);

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
    RepoBuilder<K, V> maxCacheSize(int size);
    RepoBuilder<K, V> maxCacheSize(int size, float cleanFactor);
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
}
