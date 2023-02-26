package me.redtea.carcadex.repo.cache.decorator.impl;

import me.redtea.carcadex.common.CommonSerializer;
import me.redtea.carcadex.repo.cache.CacheMapRepo;
import me.redtea.carcadex.repo.cache.decorator.CacheRepoDecorator;
import me.redtea.carcadex.schema.impl.CommonSchemaStrategy;

import java.nio.file.Path;

public class CommonRepoDecorator<K, V> extends CacheRepoDecorator<K, V> {
    public CommonRepoDecorator(Path dir, CommonSerializer<V> serializer) {
        super(new CacheMapRepo<K, V>(new CommonSchemaStrategy<>(dir, serializer)) {
            @Override
            public void init() {
                schemaStrategy.init();
            }
        });
    }
}
