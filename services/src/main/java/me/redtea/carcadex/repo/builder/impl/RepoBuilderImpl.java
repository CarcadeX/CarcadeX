package me.redtea.carcadex.repo.builder.impl;

import me.redtea.carcadex.repo.impl.schema.SchemaRepo;
import me.redtea.carcadex.schema.SchemaStrategy;
import me.redtea.carcadex.serializer.CommonSerializer;
import me.redtea.carcadex.repo.MutableRepo;
import me.redtea.carcadex.repo.builder.RepoBuilder;
import me.redtea.carcadex.repo.impl.CacheRepo;
import me.redtea.carcadex.repo.decorator.impl.AutoSaveDecorator;
import me.redtea.carcadex.repo.decorator.impl.CommonRepoDecorator;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepoBuilderImpl<K, V> implements RepoBuilder<K, V> {
    private CommonSerializer<V> serializer; //null if binary
    private boolean autoSave;
    private int period;
    private Plugin plugin;
    private Path dir;
    private String filename;
    private SchemaStrategy<K, V> schemaStrategy;

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
    public RepoBuilder<K, V> autoSave(int period) {
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
    public MutableRepo<K, V> build() {
        if(dir == null) {
            checkDir();
        }
        CacheRepo<K, V> result;
        if(serializer == null) {
            if(schemaStrategy != null) result = new SchemaRepo<>(schemaStrategy);
            else result = new CommonRepoDecorator<>(dir);
        }
        else result = new CommonRepoDecorator<>(dir, serializer);
        if(autoSave) {
            if(plugin == null) throw new NullPointerException("Plugin is null! Please set it.");
            result = new AutoSaveDecorator<>(result, plugin, 0, period);
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
