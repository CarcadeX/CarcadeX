package me.redtea.carcadex.data.schema.file.impl.serialize;

import me.redtea.carcadex.data.serializer.CommonSerializer;
import me.redtea.carcadex.data.schema.file.AbstractFileSchemaStrategy;

import java.io.*;

public class SerializeSchemaStrategy<K, V> extends AbstractFileSchemaStrategy<K, V> {

    private final CommonSerializer<V> serializer;

    public SerializeSchemaStrategy(File folder, CommonSerializer<V> serializer, String filenameExtension) {
        super(folder, filenameExtension);
        this.serializer = serializer;
    }

    public SerializeSchemaStrategy(File folder, CommonSerializer<V> serializer) {
        super(folder);
        this.serializer = serializer;
    }

    @Override
    protected V fromFile(String string) {
        return serializer.deserialize(string);
    }

    @Override
    protected String toFile(V value) {
        return serializer.serialize(value);
    }

    @Override
    public void close() {

    }
}
