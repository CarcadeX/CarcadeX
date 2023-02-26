package me.redtea.carcadex.repo.decorator.impl;

import me.redtea.carcadex.serializer.CommonSerializer;
import me.redtea.carcadex.repo.impl.map.CacheMapRepo;
import me.redtea.carcadex.repo.decorator.CacheRepoDecorator;
import me.redtea.carcadex.schema.impl.binary.BinarySchemaStrategy;
import me.redtea.carcadex.schema.impl.serialize.SerializeSchemaStrategy;

import java.nio.file.Path;

public class CommonRepoDecorator<K, V> extends CacheRepoDecorator<K, V> {
    public CommonRepoDecorator(Path dir, CommonSerializer<V> serializer) {
        super(new CacheMapRepo<K, V>(new SerializeSchemaStrategy<>(dir, serializer)) {
            @Override
            public void init() {
                schemaStrategy.init();
            }
        });
    }

    public CommonRepoDecorator(Path dir) {
        super(new CacheMapRepo<K, V>(new BinarySchemaStrategy<>(dir)) {
            @Override
            public void init() {
                schemaStrategy.init();
            }
        });
    }
}
