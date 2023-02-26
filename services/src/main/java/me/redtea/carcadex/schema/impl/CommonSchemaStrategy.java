package me.redtea.carcadex.schema.impl;

import me.redtea.carcadex.schema.SchemaStrategy;
import me.redtea.carcadex.common.CommonSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class CommonSchemaStrategy<K, V> implements SchemaStrategy<K, V> {
    private final Path dir;

    private final CommonSerializer<V> serializer;

    public CommonSchemaStrategy(Path dir, CommonSerializer<V> serializer) {
        this.dir = dir;
        this.serializer = serializer;
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
    public Collection<V> all() {
        return Arrays.stream(dir.toFile().listFiles()).map(file -> {
            try {
                return serializer.deserialize(String.join("\n", Files.readAllLines(file.toPath())));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public V get(K key) {
        try {
            return serializer.deserialize(
                    String.join("", Files.readAllLines(
                            Arrays.stream(dir.toFile().listFiles()).filter(it -> it.getName().equals(String.valueOf(key)))
                                    .findFirst().get().toPath()
                            )));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(K key, V value) {
        File file = new File(dir.toFile(), String.valueOf(key));

        try {
            if(!file.exists()) file.createNewFile();
            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(serializer.serialize(value));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(K key) {
        File file = new File(dir.toFile(), String.valueOf(key));
        if(file.exists()) file.delete();
    }
}
