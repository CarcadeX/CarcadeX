package me.redtea.carcadex.schema.impl.serialize;

import me.redtea.carcadex.schema.AbstractFileSchemaStrategy;
import me.redtea.carcadex.serializer.CommonSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class SerializeSchemaStrategy<K, V> extends AbstractFileSchemaStrategy<K, V> {

    private final CommonSerializer<V> serializer;

    public SerializeSchemaStrategy(Path dir, CommonSerializer<V> serializer) {
        super(dir);
        this.serializer = serializer;
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
                    String.join("\n", Files.readAllLines(
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

}
