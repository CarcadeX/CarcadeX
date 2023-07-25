package me.redtea.carcadex.schema;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class  AbstractFileSchemaStrategy<K, V> implements SchemaStrategy<K, V> {
    protected final Path dir;

    public AbstractFileSchemaStrategy(Path dir) {
        this.dir = dir;
        init();
    }

    @Override
    public void init() {
        if(Files.notExists(dir)) {
            try {
                Files.createDirectory(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() {
    }

    @Override
    public void remove(K key) {
        File file = new File(dir.toFile(), String.valueOf(key));
        if(file.exists()) file.delete();
    }
}
