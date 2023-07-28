package me.redtea.carcadex.repo.builder.impl;

import me.redtea.carcadex.repo.MutableRepo;
import me.redtea.carcadex.repo.builder.RepoBuilder;
import me.redtea.carcadex.repo.decorator.impl.AutoSaveDecorator;
import me.redtea.carcadex.repo.decorator.impl.SynchronizedDecorator;
import me.redtea.carcadex.repo.decorator.impl.LoggingDecorator;
import me.redtea.carcadex.repo.impl.CacheRepo;
import me.redtea.carcadex.repo.impl.common.CommonRepo;
import me.redtea.carcadex.repo.impl.schema.SchemaRepo;
import me.redtea.carcadex.schema.SchemaStrategy;
import me.redtea.carcadex.serializer.CommonSerializer;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RepoBuilderImpl<K, V> implements RepoBuilder<K, V> {
    private CommonSerializer<V> serializer; //null if binary
    private boolean autoSave;
    private long period;
    private Plugin plugin;
    private Path dir;
    private String filename;
    private SchemaStrategy<K, V> schemaStrategy;
    private Logger logger = null;
    private boolean logging = false;
    private boolean debugLogger = false;
    private boolean sync = false;
    private boolean concurrent = false;

    @Override
    public RepoBuilder<K, V> serializer(CommonSerializer<V> serializer) {
        this.serializer = serializer;
        return this;
    }

    @Override
    public RepoBuilder<K, V> binary() {
        serializer = null;
        return this;
    }

    @Override
    public RepoBuilder<K, V> plugin(Plugin plugin) {
        this.plugin = plugin;
        return this;
    }

    @Override
    public RepoBuilder<K, V> autoSave(long period) {
        autoSave = true;
        this.period = period;
        return this;
    }

    @Override
    public RepoBuilder<K, V> schema(SchemaStrategy<K, V> schemaStrategy) {
        this.schemaStrategy = schemaStrategy;
        return this;
    }

    @Override
    public RepoBuilder<K, V> dir(Path path) {
        this.dir = path;
        return this;
    }

    @Override
    public RepoBuilder<K, V> dir(String filename) {
        this.filename = filename;
        return this;
    }

    @Override
    public RepoBuilder<K, V> logging() {
        logging = true;
        debugLogger = false;
        return this;
    }

    @Override
    public RepoBuilder<K, V> debugLogging() {
        logging = true;
        debugLogger = true;
        return this;
    }

    @Override
    public RepoBuilder<K, V> logging(Logger logger) {
        logging = true;
        debugLogger = false;
        this.logger = logger;
        return this;
    }

    @Override
    public RepoBuilder<K, V> debugLogging(Logger logger) {
        logging = true;
        debugLogger = true;
        this.logger = logger;
        return this;
    }

   @Override
    public RepoBuilder<K, V> sync() {
        sync = true;
        return this;
    }

    @Override
    public RepoBuilder<K, V> concurrent() {
        concurrent = true;
        return this;
    }

    @Override
    public MutableRepo<K, V> build() {
        if(dir == null) {
            checkDir();
        }

        Map<K, V> defaultMap = concurrent ? new ConcurrentHashMap<>() : new HashMap<>();

        CacheRepo<K, V> result = null;

        if(serializer != null) {
            result = new CommonRepo<>(dir, serializer, defaultMap);
        }

        if(schemaStrategy != null) {
            result = new SchemaRepo<>(schemaStrategy, defaultMap);
        }

        if(result == null) {
            result = new CommonRepo<>(dir, defaultMap);
        }

        if(autoSave) {result = new AutoSaveDecorator<>(result, 0, period);
        }

        if(logging) {
            if(logger != null) result = new LoggingDecorator<>(result, logger, debugLogger);
            else result = new LoggingDecorator<>(result, debugLogger);
        }

        if(sync) {
            result = new SynchronizedDecorator<>(result);
        }

        return result;
    }

    private void checkDir() {
        if(plugin == null) {
            if(filename == null) {
                dir = Paths.get("saved-data-carcadex");
            } else dir = Paths.get(filename);
        } else dir = new File(plugin.getDataFolder(), filename).toPath();
    }
}
