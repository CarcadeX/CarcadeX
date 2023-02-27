package me.redtea.carcadex.repo.decorator.impl;

import me.redtea.carcadex.serializer.CommonSerializer;
import me.redtea.carcadex.repo.impl.schema.SchemaRepo;
import me.redtea.carcadex.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.schema.impl.binary.BinarySchemaStrategy;
import me.redtea.carcadex.schema.impl.serialize.SerializeSchemaStrategy;

import java.nio.file.Path;

public class CommonRepoDecorator<K, V> extends CacheRepoDecorator<K, V> {
    public CommonRepoDecorator(Path dir, CommonSerializer<V> serializer) {
        super(new SchemaRepo<K, V>(new SerializeSchemaStrategy<>(dir, serializer)));
    }

    public CommonRepoDecorator(Path dir) {
        super(new SchemaRepo<>(new BinarySchemaStrategy<>(dir)));
    }
}
