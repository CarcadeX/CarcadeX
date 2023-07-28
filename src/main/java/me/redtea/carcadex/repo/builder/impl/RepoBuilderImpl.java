package me.redtea.carcadex.repo.builder.impl;

import me.redtea.carcadex.repo.MutableRepo;
import me.redtea.carcadex.repo.builder.exception.NotConfiguredException;
import me.redtea.carcadex.repo.builder.RepoBuilder;
import me.redtea.carcadex.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.repo.decorator.impl.*;
import me.redtea.carcadex.repo.impl.CacheRepo;
import me.redtea.carcadex.repo.impl.schema.SchemaRepo;
import me.redtea.carcadex.schema.SchemaStrategy;
import me.redtea.carcadex.schema.file.impl.binary.BinarySchemaStrategy;
import me.redtea.carcadex.schema.file.impl.serialize.SerializeSchemaStrategy;
import me.redtea.carcadex.serializer.CommonSerializer;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class RepoBuilderImpl<K, V> implements RepoBuilder<K, V>  {
    private final EmptyMutableDecorator<K, V> rootDecorator = new EmptyMutableDecorator<>();
    private CacheRepoDecorator<K, V> lastDecorator = rootDecorator;

    /*
    FOLDER CONFIGURE
     */
    protected File folder;
    private String filename;

    @Override
    public RepoBuilder<K, V> folder(String filename) {
        if(plugin != null) plugin(plugin);
        this.filename = filename;
        return this;
    }

    private Plugin plugin;
    @Override
    public RepoBuilder<K, V> plugin(Plugin plugin) {
        this.plugin = plugin;
        if(folder != null) return this;
        if(filename == null) filename = "carcadex-data-folder";
        folder(new File(plugin.getDataFolder(), filename));
        return this;
    }

    @Override
    public RepoBuilder<K, V> folder(File folder) {
        this.folder = folder;
        return this;
    }

    /*
    SCHEMA CONFIGURE
     */

    private SchemaStrategy<K, V> schema;

    @Override
    public RepoBuilder<K, V> serializer(CommonSerializer<V> serializer) {
        if(folder == null) throw new NotConfiguredException("Before set serializer you must configure folder!");
        schema = new SerializeSchemaStrategy<>(folder, serializer);
        return this;
    }

    @Override
    public RepoBuilder<K, V> binary() {
        if(folder == null) throw new NotConfiguredException("Before set binary flag you must configure folder!");
        schema = new BinarySchemaStrategy<>(folder);
        return this;
    }

    @Override
    public RepoBuilder<K, V> schema(SchemaStrategy<K, V> schemaStrategy) {
        schema = schemaStrategy;
        return this;
    }

    /*
    DECORATORS
     */

    @Override
    public RepoBuilder<K, V> logging() {
        lastDecorator = new LoggingDecorator<>(lastDecorator);
        return this;
    }

    @Override
    public RepoBuilder<K, V> debugLogging() {
        lastDecorator = new LoggingDecorator<>(lastDecorator, true);
        return this;
    }

    @Override
    public RepoBuilder<K, V> logging(Logger logger) {
        lastDecorator = new LoggingDecorator<>(lastDecorator, logger, false);
        return this;
    }

    @Override
    public RepoBuilder<K, V> debugLogging(Logger logger) {
        lastDecorator = new LoggingDecorator<>(lastDecorator, logger, true);
        return this;
    }

    @Override
    public RepoBuilder<K, V> autoSave(long period) {
        lastDecorator = new AutoSaveDecorator<>(lastDecorator, 0, period);
        return this;
    }

    @Override
    public RepoBuilder<K, V> sync() {
        lastDecorator = new SynchronizedDecorator<>(lastDecorator);
        return this;
    }

    @Override
    public RepoBuilder<K, V> cacheCollectionAll() {
        lastDecorator = new CacheAllDecorator<>(lastDecorator);
        return this;
    }

    /*
    FLAGS
     */
    private boolean concurrent = false;

    @Override
    public RepoBuilder<K, V> concurrent() {
        concurrent = true;
        return this;
    }

    /*
    BUILD
     */

    @Override
    public MutableRepo<K, V> build() {
        if(schema == null) throw new NotConfiguredException("You must set schema!");
        CacheRepo<K, V> result = new SchemaRepo<>(schema, concurrent ? new ConcurrentHashMap<>() : new HashMap<>());
        rootDecorator.setRepo(result);
        return rootDecorator;
    }
}
