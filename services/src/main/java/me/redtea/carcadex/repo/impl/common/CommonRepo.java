package me.redtea.carcadex.repo.impl.common;

import me.redtea.carcadex.repo.impl.schema.SchemaRepo;
import me.redtea.carcadex.schema.impl.binary.BinarySchemaStrategy;
import me.redtea.carcadex.schema.impl.serialize.SerializeSchemaStrategy;
import me.redtea.carcadex.serializer.CommonSerializer;

import java.nio.file.Path;

public class CommonRepo<K, V> extends SchemaRepo<K, V> {
    public CommonRepo(Path dir, CommonSerializer<V> serializer) {
        super(new SerializeSchemaStrategy<>(dir, serializer));
    }

    public CommonRepo(Path dir) {
        super(new BinarySchemaStrategy<>(dir));
    }
}
